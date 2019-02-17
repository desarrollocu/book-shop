import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AuthorManagementComponent} from '../author-management/author-management.component';
import {AlertService} from '../../../shared/alert/alert.service';
import {AuthorService} from '../author.service';

import {Author} from '../model/author';
import {Country} from "../../user/model/country";

@Component({
  selector: 'app-author-list',
  templateUrl: './author-list.component.html',
  styleUrls: ['./author-list.component.scss']
})
export class AuthorListComponent implements OnInit {
  page: number;
  author = new Author();
  itemsPerPage: number;
  totalItems: any;
  authorList: Author[];
  predicate: any;
  reverse: any;
  remAuthor: Author;
  countryList: Country[];
  currentLang: string;

  constructor(private authorService: AuthorService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private modalService: NgbModal) {
    this.itemsPerPage = 10;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getAuthors(null);
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  private countryLanguage() {
    let temp = this.countryList;
    this.countryList = [];
    if (temp) {
      for (let i in temp) {
        this.countryList = [...this.countryList, temp[i]];
      }
    }
  }

  getAuthors(param) {
    if (param === 'btn')
      this.page = 0;

    this.authorList = [];
    this.authorService.getAuthors({
      authorDTO: this.author,
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: HttpResponse<Author[]>) => this.onSuccess(response),
        (response: HttpResponse<any>) => this.onError(response)
      );
  }

  addAuthor() {
    const modalRef = this.modalService.open(AuthorManagementComponent, {size: 'lg'});
    modalRef.componentInstance.author = new Author();
    modalRef.result.then(value => {
      this.getAuthors('btn');
    }, (reason => {
    }))
  }

  editAuthor(author) {
    const modalRef = this.modalService.open(AuthorManagementComponent, {size: 'lg'});
    modalRef.componentInstance.author = author;
    modalRef.result.then(value => {
      this.getAuthors('btn');
    }, (reason => {
    }))
  }

  removeAuthor() {
    this.authorService.deleteAuthor(this.remAuthor.id)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancelMy();
  }

  openDialog(deleteAuthor, author) {
    this.remAuthor = author;
    this.modalService.open(deleteAuthor);
  }

  getCountries() {
    this.authorService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  private onSuccess(res) {
    this.authorList = res.body.elements;
    this.totalItems = res.body.total;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  onSuccessDelete() {
    this.alertService.success('success.remove', null, null);
    this.getAuthors('btn');
  }

  onErrorDelete(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  trackIdentity(index, item: Author) {
    return item.id;
  }

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }
}

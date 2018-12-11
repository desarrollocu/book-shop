import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

import {AuthorManagementComponent} from "../author-management/author-management.component";
import {AlertService} from "../../../shared/alert/alert.service";
import {AuthorService} from "../author.service";

import {Author} from '../model/author';

@Component({
  selector: 'app-author-list',
  templateUrl: './author-list.component.html',
  styleUrls: ['./author-list.component.css']
})
export class AuthorListComponent implements OnInit {
  page: number;
  author = new Author();
  itemsPerPage: number;
  totalItems: any;
  authorList: Author[];
  predicate: any;
  reverse: any;
  deleteAuthorId: string;

  constructor(private authorService: AuthorService,
              private alertService: AlertService,
              private modalService: NgbModal) {
    this.itemsPerPage = 5;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
  }

  ngOnInit() {
    this.getAuthors(null);
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
    }, (reason => {
      this.getAuthors('btn');
    }))
  }

  editAuthor(author) {
    const modalRef = this.modalService.open(AuthorManagementComponent, {size: 'lg'});
    modalRef.componentInstance.author = author;
    modalRef.result.then(value => {
    }, (reason => {
      this.getAuthors('btn');
    }))
  }

  removeAuthor() {
    this.authorService.deleteAuthor(this.deleteAuthorId)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancel();
  }

  open(deleteAuthor, authorId) {
    this.deleteAuthorId = authorId;
    this.modalService.open(deleteAuthor);
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

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

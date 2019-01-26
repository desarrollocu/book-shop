import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {AuthorService} from '../author.service';

import {Author} from '../model/author';
import {Country} from '../../user/model/country';

@Component({
  selector: 'app-author-management',
  templateUrl: './author-management.component.html',
  styleUrls: ['./author-management.component.scss']
})
export class AuthorManagementComponent implements OnInit {
  @Input() author;
  countryList: Country[];
  currentLang: string;

  constructor(private alertService: AlertDialogService,
              private authorService: AuthorService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.findAuthor();
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  findAuthor() {
    this.authorService.getAuthor(this.author)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveAuthor() {
    this.authorService.saveAuthor(this.author)
      .subscribe(response => this.onSuccess(response, this.author),
        response => this.onError(response));
  }

  getCountries() {
    this.authorService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
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

  private onSuccess(response, author) {
    let msg = 'success.add';
    if (author.id) {
      msg = 'success.edited';
    }
    this.author = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.close();
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.author = result.body;
    else {
      this.author = new Author();
    }
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  cancel() {
    this.activeModal.dismiss();
  }
}

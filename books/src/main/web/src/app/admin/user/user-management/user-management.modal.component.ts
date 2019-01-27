import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AlertDialogService} from "../../../shared/alert/alert.dialog.service";
import {AuthorService} from '../../author/author.service';
import {UserService} from '../user.service';

import {User} from '../../../core/user/user.model';
import {Country} from "../model/country";
import {Value} from '../model/value';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.modal.component.html',
  styleUrls: ['./user-management.modal.component.scss']
})
export class UserManagementModalComponent implements OnInit {
  @Input() user;
  pass: string;
  languages: string[];
  values: Value[];
  countryList: Country[];
  confirmPassword: string;
  currentLang: string;

  constructor(private userService: UserService,
              private alertService: AlertDialogService,
              private authorService: AuthorService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.values = [];
    this.pass = null;
    this.confirmPassword = "";
    this.languages = ["en", "es"];
    this.currentLang = this.translateService.currentLang;
    this.values.push(new Value("true", "user.yes"));
    this.values.push(new Value("false", "user.no"));
  }

  ngOnInit() {
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  twoPass() {
    if (this.user.password !== undefined && this.user.password !== null && this.user.password !== "") {
      if (this.user.password === this.confirmPassword)
        return false;
      else
        return true;
    }
    return false;
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

  saveUser() {
    if (this.user.password === "")
      this.user.password = null;

    this.userService.saveUser(this.user)
      .subscribe(response => this.onSuccess(response, this.user),
        response => this.onError(response));
  }

  findUser() {
    this.userService.getUser(this.user)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  private onSuccess(response, user) {
    let msg = 'success.add';
    if (user.id) {
      msg = 'success.edited';
    }
    this.user = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.close();
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.user = result.body;
    else {
      this.user = new User();
      this.user.langKey = "en";
      this.user.isAdmin = "true";
    }
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
    this.findUser();
  }

  cancel() {
    this.activeModal.dismiss();
  }
}

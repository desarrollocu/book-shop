import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';

import {AlertService} from '../alert/alert.service';
import {UserService} from '../../admin/user/user.service';
import {AuthorService} from '../../admin/author/author.service';
import {Principal} from '../../core/auth/principal.service';

import {User} from '../../core/user/user.model';
import {Country} from '../../admin/user/model/country';

@Component({
  selector: 'app-account',
  templateUrl: './account.modal.component.html',
  styleUrls: ['./account.modal.component.scss']
})
export class AccountModalComponent implements OnInit {
  user: User;
  languages: string[];
  confirmPassword: string;
  countryList: Country[];
  currentLang: string;
  isAdmin: boolean;

  constructor(private alertService: AlertService,
              private userService: UserService,
              private authorService: AuthorService,
              private translateService: TranslateService,
              private principal: Principal,
              public activeModal: NgbActiveModal) {
    this.isAdmin = false;
    this.user = new User();
    this.user.langKey = "en";
    this.confirmPassword = "";
    this.languages = ["en", "es"];
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getAccountInfo();
    this.getCountries();
  }

  getAccountInfo() {
    this.principal.identity(true).then(account => {
      this.user = account;
      this.isAdmin = this.user.isAdmin === "true" ? true : false;
    })
  }

  save() {
    this.userService.updateAccount(this.user)
      .subscribe(response => this.onSuccess(response),
        response => this.onError(response));
  }

  getCountries() {
    this.authorService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
  }

  private onSuccess(response) {
    this.user = response.body;
    this.principal.getFullNameSubject().next(this.user.fullName);
    this.alertService.success('success.edited', null, null);
    this.activeModal.dismiss('cancel');
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  cancel() {
    this.activeModal.dismiss('cancel');
  }
}

import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AlertService} from '../alert/alert.service';
import {UserService} from '../../admin/user/user.service';
import {AuthorService} from '../../admin/author/author.service';

import {User} from '../../core/user/user.model';
import {Country} from '../../admin/user/model/country';

@Component({
  selector: 'app-register',
  templateUrl: './register.modal.component.html',
  styleUrls: ['./register.modal.component.scss']
})
export class RegisterModalComponent implements OnInit {
  user: User;
  languages: string[];
  confirmPassword: string;
  countryList: Country[];
  currentLang: string;

  constructor(private alertService: AlertService,
              private userService: UserService,
              private authorService: AuthorService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.user = new User();
    this.user.langKey = "en";
    this.confirmPassword = "";
    this.languages = ["en", "es"];
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  twoPass() {
    if (this.user.password && this.confirmPassword) {
      if (this.user.password === this.confirmPassword)
        return false;
      else
        return true;
    }
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

  save() {
    this.userService.registerUser(this.user)
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
    this.alertService.success('success.register', null, null);
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

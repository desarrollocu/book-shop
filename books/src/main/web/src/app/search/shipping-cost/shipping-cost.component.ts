import {Component, OnInit} from '@angular/core';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AlertService} from '../../shared/alert/alert.service';
import {AuthorService} from '../../admin/author/author.service';

import {Country} from '../../admin/user/model/country';

@Component({
  selector: 'app-shipping-cost',
  templateUrl: './shipping-cost.component.html',
  styleUrls: ['./shipping-cost.component.scss']
})
export class ShippingCostComponent implements OnInit {
  countryList: Country[];
  country: Country;
  currentLang: string;

  constructor(private translateService: TranslateService,
              private alertService: AlertService,
              private authorService: AuthorService) {
    this.country = new Country();
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
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

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null) {
      this.countryList = result;
      for (let i = 0; i < this.countryList.length; i++) {
        if (this.countryList[i].spanishName === 'Argentina') {
          this.country = this.countryList[i];
          break;
        }
      }
    }
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }
}

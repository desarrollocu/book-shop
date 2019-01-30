import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {Country} from '../../admin/user/model/country';
import {SearchSale} from './model/searchSale';
import {AlertService} from '../../shared/alert/alert.service';

import {BuyService} from '../buy.service';
import {Author} from "../../admin/author/model/author";
import {AuthorService} from '../../admin/author/author.service';

@Component({
  selector: 'app-search-sale',
  templateUrl: './search-sale.component.html',
  styleUrls: ['./search-sale.component.scss']
})
export class SearchSaleComponent implements OnInit {
  page: number;
  sale = new SearchSale();
  itemsPerPage: number;
  totalItems: any;
  saleList: SearchSale[];
  predicate: any;
  reverse: any;
  countryList: Country[];
  currentLang: string;

  constructor(private alertService: AlertService,
              private translateService: TranslateService,
              private authorService: AuthorService,
              private buyService: BuyService,
              private modalService: NgbModal) {
    this.itemsPerPage = 5;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getSales(null);
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

  getSales(param) {
    if (param === 'btn')
      this.page = 0;

    this.saleList = [];
    this.buyService.getSales({
      saleDTO: this.sale,
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
    this.saleList = res.body.elements;
    this.totalItems = res.body.total;
  }

  private onError(response) {
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

  trackIdentity(index, item: SearchSale) {
    return item.id;
  }
}

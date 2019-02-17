import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {AlertService} from '../../../shared/alert/alert.service';
import {AuthorService} from '../../author/author.service';
import {SaleService} from './sale.service';

import {SearchSale} from '../../../search/search-sale/model/searchSale';
import {Country} from '../../user/model/country';
import {Author} from '../../author/model/author';
import {SaleManagementComponent} from "../sale-management/sale-management.component";

@Component({
  selector: 'app-sale-list',
  templateUrl: './sale-list.component.html',
  styleUrls: ['./sale-list.component.scss']
})
export class SaleListComponent implements OnInit {
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
              private saleService: SaleService,
              private modalService: NgbModal) {
    this.itemsPerPage = 10;
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
    this.saleService.getSales({
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

  editSale(sale) {
    const modalRef = this.modalService.open(SaleManagementComponent, {size: 'lg'});
    modalRef.componentInstance.sale = sale;
    modalRef.result.then(value => {
      this.getSales('btn');
    }, (reason => {
    }))
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

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }
}

import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbModal, NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

import {AlertService} from "../../shared/alert/alert.service";
import {SearchService} from "../search.service";

import {Book} from "../../admin/book/model/book";
import {Search} from "../model/search";
import {TranslateService} from "@ngx-translate/core";
import {Magazine} from "../../admin/magazine/model/magazine";
import {CartService} from "../cart.service";


@Component({
  selector: 'app-search-general',
  templateUrl: './search-general.component.html',
  styleUrls: ['./search-general.component.scss'],
  providers: [NgbRatingConfig],
  encapsulation: ViewEncapsulation.None,
})
export class SearchGeneralComponent implements OnInit {
  page: number;
  search = new Search();
  itemsPerPage: number;
  totalItems: any;
  bookList: Book[];
  predicate: any;
  reverse: any;
  selectedBook: Book;
  currentLang: string;
  searchMore: boolean;
  load: boolean;

  pageMagazine: number;
  itemsPerPageMagazine: number;
  totalItemsMagazine: any;
  selectedMagazine: Magazine;
  magazineList: Magazine[];
  predicateMagazine: any;
  reverseMagazine: any;

  constructor(private searchService: SearchService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private shoppingService: CartService,
              private modalService: NgbModal) {
    this.itemsPerPage = 4;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.selectedBook = new Book();

    this.itemsPerPageMagazine = 4;
    this.predicateMagazine = 'id';
    this.reverseMagazine = true;
    this.pageMagazine = 0;
    this.selectedMagazine = new Magazine();
  }

  ngOnInit() {
    this.getBooks(null);
    this.getMagazines(null);
  }

  getMagazines(param) {
    if (param === 'btn')
      this.pageMagazine = 0;

    this.magazineList = [];
    this.searchService.searchMagazines({
      pageable: {
        page: this.pageMagazine - 1,
        size: this.itemsPerPageMagazine,
        sort: this.sortMagazine()
      }
    })
      .subscribe(
        (response: Magazine[]) => this.onSuccessMagazine(response),
        (response: any) => this.onError(response)
      );
  }

  getBooks(param) {
    if (param === 'btn')
      this.page = 0;

    this.bookList = [];
    this.searchService.searchBooks({
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: Book[]) => this.onSuccess(response),
        (response: any) => this.onError(response)
      );
  }

  private onSuccess(res) {
    this.bookList = res.elements;
    this.totalItems = res.total;
  }

  private onSuccessMagazine(res) {
    this.magazineList = res.elements;
    this.totalItemsMagazine = res.total;
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

  sortMagazine() {
    const result = [this.predicateMagazine + ',' + (this.reverseMagazine ? 'asc' : 'desc')];
    if (this.predicateMagazine !== 'id') {
      result.push('id');
    }
    return result;
  }

  trackIdentity(index, item: Book) {
    return item.id;
  }

  selectBook(book) {
    this.selectedBook = new Book();
    this.selectedBook = book;
  }

  selectMagazine(magazine) {
    this.selectedMagazine = new Magazine();
    this.selectedMagazine = magazine;
  }

  addToCar() {
    this.shoppingService.addToCar(this.selectedBook, true);
    this.alertService.info('shopping.success', null, null);
    this.cancel();
  }

  addToCarMagazine() {
    this.shoppingService.addToCar(this.selectedMagazine, false);
    this.alertService.info('shopping.success', null, null);
    this.cancel();
  }

  magazineDetails(details) {
    this.modalService.open(details);
  }

  bookDetails(details) {
    this.modalService.open(details);
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

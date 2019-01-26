import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbModal, NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AlertService} from '../../shared/alert/alert.service';
import {SearchService} from '../search.service';

import {Book} from '../../admin/book/model/book';
import {Search} from '../model/search';
import {Magazine} from '../../admin/magazine/model/magazine';
import {CartService} from '../cart.service';
import {UiData} from "../model/uiData";
import {Principal} from "../../core/auth/principal.service";


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

  imageUrlList: any[];
  imageList: string[];

  uiData: UiData;

  constructor(private searchService: SearchService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private shoppingService: CartService,
              private principal: Principal,
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
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getBookCarousel();
    this.getUIData();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
    });
  }

  getUIData() {
    this.principal.getUIData({})
      .subscribe(response => this.onUIDataSuccess(response),
        response => this.onUIDataError(response));
  }

  private onUIDataError(response) {
    let error = response.error;
  }

  private onUIDataSuccess(res) {
    this.uiData = res.body;
  }

  // createArray(array) {
  //   this.imageUrlList = [];
  //   while (array.length > 0) {
  //     let toPush = [];
  //     let temp = array.splice(0, 4);
  //
  //     for (let i = 0; i < temp.length; i++) {
  //       toPush.push({
  //         url: temp[i],
  //         style: i > 0 ? 'position: absolute;left:' + 167 * i + 'px' : null
  //       })
  //     }
  //
  //     let val = 4 - toPush.length;
  //     for (let i = 0; i < val; i++) {
  //       toPush.push({
  //         url: 'assets/images/image.gif',
  //         style: i > 0 ? 'position: absolute;left:' + 167 * i + 'px' : null
  //       })
  //     }
  //     this.imageUrlList.push(toPush);
  //   }
  // }

  getBookCarousel() {
    this.imageUrlList = [];
    this.searchService.searchCarouselBooks()
      .subscribe(
        (response: Book[]) => this.onCarouselSuccess(response),
        (response: any) => this.onError(response)
      );
  }

  private onCarouselSuccess(res) {
    this.imageUrlList = res;
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
    this.shoppingService.toCar(this.selectedBook, true);
    this.alertService.info('shopping.success', null, null);
    this.cancel();
  }

  addToCarMagazine() {
    this.shoppingService.toCar(this.selectedMagazine, false);
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

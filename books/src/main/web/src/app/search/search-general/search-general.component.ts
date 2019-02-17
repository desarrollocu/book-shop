import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbModal, NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {AlertService} from '../../shared/alert/alert.service';
import {Principal} from "../../core/auth/principal.service";
import {SearchService} from '../search.service';

import {Book} from '../../admin/book/model/book';
import {Search} from '../model/search';
import {Magazine} from '../../admin/magazine/model/magazine';
import {CartService} from '../cart.service';
import {UiData} from '../model/uiData';
import {BookService} from "../../admin/book/book.service";
import {MagazineService} from "../../admin/magazine/magazine.service";


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
  predicate: any;
  reverse: any;
  selectedBook: Book;
  currentLang: string;
  load: boolean;
  pageMagazine: number;
  itemsPerPageMagazine: number;
  selectedMagazine: Magazine;
  predicateMagazine: any;
  reverseMagazine: any;
  imageUrlList: any[];
  uiData: UiData;

  constructor(private searchService: SearchService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private shoppingService: CartService,
              private bookService: BookService,
              private magazineService: MagazineService,
              private modalService: NgbModal,
              private cartService: CartService,
              private principal: Principal) {
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

  getBookCarousel() {
    this.imageUrlList = [];
    this.searchService.searchCarouselBooks()
      .subscribe(
        (response: Book[]) => this.onCarouselSuccess(response),
        (response: any) => this.onError(response)
      );
  }

  showDetails(details, detailMagazine, document) {
    if (document.book === true) {
      this.bookService.getBook({
        id: document.documentId
      }).subscribe(response => this.onSearchSuccess(response, details, true),
        response => this.onError(response));
    }
    else {
      this.magazineService.getMagazine({
        id: document.documentId
      }).subscribe(response => this.onSearchSuccess(response, detailMagazine, false),
        response => this.onError(response));
    }
  }

  addToCarMagazine(magazine) {
    if (magazine !== undefined) {
      this.cartService.addToCart({
        id: magazine !== undefined ? magazine.id : this.selectedMagazine.id,
        cant: 1,
        book: false
      }).subscribe(response => this.onAddCarSuccess(response.body),
        response => this.onError(response));
    }
  }

  addToCarBook(book) {
    if (book !== undefined) {
      this.cartService.addToCart({
        id: book !== undefined ? book.id : this.selectedBook.id,
        cant: 1,
        book: true
      }).subscribe(response => this.onAddCarSuccess(response.body),
        response => this.onError(response));
    }
  }

  onAddCarSuccess(resp) {
    if (resp.exist)
      this.alertService.info('info.inCart', null, null);
    else
      this.alertService.info('shopping.success', null, null);

    this.cartService.getCarSubject().next(resp.cant);
    this.cancel();
  }

  private onSearchSuccess(result, details, isBook) {
    if (isBook)
      this.selectedBook = result.body;
    else
      this.selectedMagazine = result.body;
    this.modalService.open(details, {size: 'lg'});
  }

  private onCarouselSuccess(res) {
    this.imageUrlList = res;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  trackIdentity(index, item: Book) {
    return item.id;
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

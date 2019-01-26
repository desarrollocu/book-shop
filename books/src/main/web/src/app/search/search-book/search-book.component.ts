import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {SearchService} from '../search.service';
import {AlertService} from '../../shared/alert/alert.service';
import {TopicService} from '../../admin/topic/topic.service';
import {ClassificationService} from '../../admin/classification/classification.service';
import {CartService} from '../cart.service';

import {Search} from '../model/search';
import {Classification} from '../../admin/classification/model/classification';
import {Book} from '../../admin/book/model/book';
import {Topic} from '../../admin/topic/model/topic';
import {Country} from "../../admin/user/model/country";

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.scss']
})
export class SearchBookComponent implements OnInit {
  page: number;
  search = new Search();
  itemsPerPage: number;
  totalItems: any;
  bookList: Book[];
  predicate: any;
  reverse: any;
  currentRate = 2;
  years: string[];
  classifications: Classification[];
  classificationsTemp: Classification[];
  selectedBook: Book;
  images: string[];
  topics: Topic[];
  currentLang: string;
  searchMore: boolean;
  load: boolean;
  countryList: Country[];

  constructor(private searchService: SearchService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private topicService: TopicService,
              private classificationService: ClassificationService,
              private shoppingService: CartService,
              private modalService: NgbModal) {
    this.searchMore = false;
    this.load = false;
    this.itemsPerPage = 12;
    this.predicate = 'title';
    this.reverse = true;
    this.page = 0;
    this.selectedBook = new Book();
    this.currentLang = this.translateService.currentLang;

    this.years = [];
    for (let i = 1800; i < 2100; i++) {
      this.years.push(String(i));
    }
  }

  ngOnInit() {
    this.getBooks(null);
    this.findClassifications();
    this.findTopics();
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
      this.classificationsLanguage();
    });
  }

  searchMoreChange() {
    this.searchMore = !this.searchMore;
  }

  getBooks(param) {
    if (param === 'btn')
      this.page = 0;
    else
      this.predicate = param;

    this.bookList = [];
    this.searchService.searchBook({
      searchDTO: this.search,
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: HttpResponse<Book[]>) => this.onSuccess(response),
        (response: HttpResponse<any>) => this.onError(response)
      );
  }

  private onSuccess(res) {
    this.bookList = res.body.elements;
    this.totalItems = res.body.total;
    this.load = true;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  sort() {
    this.predicate = this.predicate !== null ? this.predicate : 'id';
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    this.reverse = !this.reverse;
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    return result;
  }

  trackIdentity(index, item: Book) {
    return item.id;
  }

  getCountries() {
    this.searchService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  findClassifications() {
    this.classificationService.getAllClassifications()
      .subscribe(response => this.onClassificationsSuccess(response),
        response => this.onError(response));
  }

  findTopics() {
    this.topicService.getAllTopics()
      .subscribe(response => this.onTopicsSuccess(response),
        response => this.onError(response));
  }

  private onTopicsSuccess(response) {
    this.topics = [];
    if (response) {
      this.topics = response;
      this.topicLanguage();
    }
  }

  private topicLanguage() {
    let temp = this.topics;
    this.topics = [];
    if (temp) {
      for (let i in temp) {
        this.topics = [...this.topics, temp[i]];
      }
    }
  }

  private classificationsLanguage() {
    let temp = this.classificationsTemp;
    this.classifications = [];
    if (temp) {
      for (let i in temp) {
        this.classifications = [...this.classifications, {
          id: temp[i].id,
          name: this.translateService.instant(temp[i].name)
        }];
      }
    }
  }

  private onClassificationsSuccess(response) {
    this.classifications = [];
    this.classificationsTemp = [];
    if (response) {
      this.classificationsTemp = response;
      for (let i in response) {
        this.classifications.push({
          id: response[i].id,
          name: this.translateService.instant(response[i].name)
        });
      }
    }
  }

  selectBook(book) {
    this.selectedBook = new Book();
    this.selectedBook = book;
  }

  addToCar(book) {
    if (book !== undefined)
      this.shoppingService.toCar(book, true, true);
    else
      this.shoppingService.toCar(this.selectedBook, true, true);
    this.cancel();
  }

  bookDetails(details, book) {
    this.selectedBook = new Book();
    this.selectedBook = book;
    this.selectBook(book);
    this.modalService.open(details);
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {NgbModal, NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

import {AlertService} from "../../shared/alert/alert.service";
import {TopicService} from "../../admin/topic/topic.service";
import {EditorService} from "../../admin/editor/editor.service";
import {AuthorService} from "../../admin/author/author.service";
import {SearchService} from "../search.service";

import {Book} from "../../admin/book/model/book";
import {Topic} from "../../admin/topic/model/topic";
import {Editor} from "../../admin/editor/model/editor";
import {Author} from "../../admin/author/model/author";
import {Search} from "../model/search";
import {Classification} from "../../admin/classification/model/classification";
import {ClassificationService} from "../../admin/classification/classification.service";
import {TranslateService} from "@ngx-translate/core";


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
  currentRate = 2;
  years: string[];
  classifications: Classification[];
  selectedBook: Book;

  topics: Topic[];
  editors: Editor[];
  authorList: Author[];
  images: string[];

  constructor(config: NgbRatingConfig,
              private searchService: SearchService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private topicService: TopicService,
              private classificationService: ClassificationService,
              private modalService: NgbModal,
              private editorService: EditorService,
              private authorService: AuthorService) {
    config.max = 5;
    this.itemsPerPage = 12;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.selectedBook = new Book();
    this.images = ["assets/images/img.jpg", "assets/images/login.png", "assets/images/img.jpg"];

    this.years = [];
    for (let i = 1800; i < 2100; i++) {
      this.years.push(String(i));
    }
  }

  ngOnInit() {
    this.getBooks(null);
    this.findClassifications();
  }

  getBooks(param) {
    if (param === 'btn')
      this.page = 0;

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

  trackIdentity(index, item: Book) {
    return item.id;
  }

  findClassifications() {
    this.classificationService.getAllClassifications()
      .subscribe(response => this.onClassificationsSuccess(response),
        response => this.onError(response));
  }

  private onClassificationsSuccess(response) {
    this.classifications = [];
    if (response) {
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

  addToCar() {
    console.log(this.selectedBook.id);
    this.cancel();
  }

  bookDetails(details) {
    this.modalService.open(details);
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

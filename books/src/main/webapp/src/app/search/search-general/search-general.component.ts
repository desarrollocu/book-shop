import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

import {AlertService} from "../../shared/alert/alert.service";
import {TopicService} from "../../admin/topic/topic.service";
import {EditorService} from "../../admin/editor/editor.service";
import {AuthorService} from "../../admin/author/author.service";
import {SearchService} from "../search.service";

import {Book} from "../../admin/book/model/book";
import {Topic} from "../../admin/topic/model/topic";
import {Editor} from "../../admin/editor/model/editor";
import {Author} from "../../admin/author/model/author";


@Component({
  selector: 'app-search-general',
  templateUrl: './search-general.component.html',
  styleUrls: ['./search-general.component.css'],
  providers: [NgbRatingConfig],
  encapsulation: ViewEncapsulation.None,
})
export class SearchGeneralComponent implements OnInit {
  page: number;
  book = new Book();
  itemsPerPage: number;
  totalItems: any;
  bookList: Book[];
  predicate: any;
  reverse: any;
  currentRate = 2;

  topics: Topic[];
  editors: Editor[];
  authorList: Author[];

  constructor(config: NgbRatingConfig,
              private searchService: SearchService,
              private alertService: AlertService,
              private topicService: TopicService,
              private editorService: EditorService,
              private authorService: AuthorService) {
    config.max = 5;
    this.itemsPerPage = 12;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
  }

  ngOnInit() {
    this.getBooks(null);
  }

  getBooks(param) {
    if (param === 'btn')
      this.page = 0;

    this.bookList = [];
    this.searchService.generalSearch({
      bookDTO: this.book,
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

  imageClick() {
    alert('OK');
  }
}

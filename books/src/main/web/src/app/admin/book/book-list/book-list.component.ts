import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {BookManagementComponent} from '../book-management/book-management.component';
import {AlertService} from '../../../shared/alert/alert.service';
import {BookService} from '../book.service';
import {TopicService} from '../../topic/topic.service';
import {EditorService} from "../../editor/editor.service";
import {AuthorService} from "../../author/author.service";

import {Book} from '../model/book';
import {Topic} from '../../topic/model/topic';
import {Editor} from "../../editor/model/editor";
import {Author} from "../../author/model/author";
import {BookTraceComponent} from "../book-trace/book-trace.component";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  page: number;
  book = new Book();
  itemsPerPage: number;
  totalItems: any;
  bookList: Book[];
  predicate: any;
  reverse: any;
  remBook: Book;
  topics: Topic[];
  editors: Editor[];
  authorList: Author[];
  currentLang: string;

  constructor(private bookService: BookService,
              private alertService: AlertService,
              private modalService: NgbModal,
              private topicService: TopicService,
              private translateService: TranslateService,
              private editorService: EditorService,
              private authorService: AuthorService) {
    this.itemsPerPage = 10;
    this.predicate = 'title';
    this.reverse = true;
    this.page = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getBooks(null);
    this.findTopics();
    this.findEditors();
    this.findAuthors();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
    });
  }

  getBooks(param) {
    if (param === 'btn')
      this.page = 0;
    else
      this.predicate = param;

    this.bookList = [];
    this.bookService.getBooks({
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

  addBook() {
    const modalRef = this.modalService.open(BookManagementComponent, {size: 'lg'});
    modalRef.componentInstance.book = new Book();
    modalRef.result.then(value => {
      this.getBooks('btn');
    }, (reason => {
    }))
  }

  editBook(book) {
    const modalRef = this.modalService.open(BookManagementComponent, {size: 'lg'});
    modalRef.componentInstance.book = book;
    modalRef.result.then(value => {
      this.getBooks('btn');
    }, (reason => {
    }))
  }

  bookTrace(book) {
    const modalRef = this.modalService.open(BookTraceComponent, {size: 'lg'});
    modalRef.componentInstance.book = book;
  }

  removeBook() {
    this.bookService.deleteBook(this.remBook.id)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancelMy();
  }

  findTopics() {
    this.topicService.getAllTopics()
      .subscribe(response => this.onTopicsSuccess(response),
        response => this.onError(response));
  }

  findEditors() {
    this.editorService.getAllEditors()
      .subscribe(response => this.onEditorsSuccess(response),
        response => this.onError(response));
  }

  findAuthors() {
    this.authorService.getAllAuthors()
      .subscribe(response => this.onAuthorsSuccess(response),
        response => this.onError(response));
  }

  openDialog(deleteBook, book) {
    this.remBook = book;
    this.modalService.open(deleteBook);
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

  onSuccessDelete() {
    this.alertService.success('success.remove', null, null);
    this.getBooks('btn');
  }

  onErrorDelete(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
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

  private onEditorsSuccess(response) {
    this.editors = [];
    if (response)
      this.editors = response;
  }

  private onAuthorsSuccess(response) {
    this.authorList = [];
    if (response)
      this.authorList = response;
  }

  sort() {
    this.predicate = this.predicate !== null ? this.predicate : 'id';
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    this.reverse = !this.reverse;
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    return result;
    // const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    // return result;
  }

  trackIdentity(index, item: Book) {
    return item.id;
  }

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }
}

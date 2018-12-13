import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

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

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  page: number;
  book = new Book();
  itemsPerPage: number;
  totalItems: any;
  bookList: Book[];
  predicate: any;
  reverse: any;
  deleteBookId: string;
  topics: Topic[];
  editors: Editor[];
  authorList: Author[];

  constructor(private bookService: BookService,
              private alertService: AlertService,
              private modalService: NgbModal,
              private topicService: TopicService,
              private editorService: EditorService,
              private authorService: AuthorService) {
    this.itemsPerPage = 5;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
  }

  ngOnInit() {
    this.getBooks(null);
    this.findTopics();
    this.findEditors();
    this.findAuthors();
  }

  getBooks(param) {
    if (param === 'btn')
      this.page = 0;

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
    }, (reason => {
      this.getBooks('btn');
    }))
  }

  editBook(book) {
    const modalRef = this.modalService.open(BookManagementComponent, {size: 'lg'});
    modalRef.componentInstance.book = book;
    modalRef.result.then(value => {
    }, (reason => {
      this.getBooks('btn');
    }))
  }

  removeBook() {
    this.bookService.deleteBook(this.deleteBookId)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancel();
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

  open(deleteBook, bookId) {
    this.deleteBookId = bookId;
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
    if (response)
      this.topics = response;
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
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  trackIdentity(index, item: Book) {
    return item.id;
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

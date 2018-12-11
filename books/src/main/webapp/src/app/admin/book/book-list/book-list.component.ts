import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {BookManagementComponent} from '../book-management/book-management.component';
import {AlertService} from '../../../shared/alert/alert.service';
import {BookService} from '../book.service';

import {Book} from '../model/book';

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

  constructor(private bookService: BookService,
              private alertService: AlertService,
              private modalService: NgbModal) {
    this.itemsPerPage = 5;
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

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

import {AlertService} from "../../../shared/alert/alert.service";
import {BookService} from "../book.service";

import {Book} from "../model/book";

@Component({
  selector: 'app-book-management',
  templateUrl: './book-management.component.html',
  styleUrls: ['./book-management.component.css']
})
export class BookManagementComponent implements OnInit {
  @Input() book;

  constructor(private alertService: AlertService,
              private bookService: BookService,
              public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
    this.findBook();
  }

  findBook() {
    this.bookService.getBook(this.book)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveBook() {
    this.bookService.saveBook(this.book)
      .subscribe(response => this.onSuccess(response, this.book),
        response => this.onError(response));
  }

  private onSuccess(response, book) {
    let msg = 'success.add';
    if (book.id) {
      msg = 'success.edited';
    }
    this.book = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.dismiss('cancel');
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.book = result.body;
    else {
      this.book = new Book();
    }
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  cancel() {
    this.activeModal.dismiss('cancel');
  }
}

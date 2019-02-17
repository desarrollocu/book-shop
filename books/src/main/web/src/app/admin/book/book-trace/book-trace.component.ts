import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {BookService} from '../book.service';
import {BookTrace} from "../model/book-trace";

@Component({
  selector: 'app-book-trace',
  templateUrl: './book-trace.component.html',
  styleUrls: ['./book-trace.component.scss']
})
export class BookTraceComponent implements OnInit {
  @Input() book;
  traceList: BookTrace[];

  constructor(public activeModal: NgbActiveModal,
              private bookService: BookService,
              private alertService: AlertDialogService) {
  }

  ngOnInit() {
    this.findTrace();
  }

  findTrace() {
    this.bookService.findTrace(this.book)
      .subscribe(response => this.onSuccess(response),
        response => this.onError(response));
  }

  private onSuccess(res) {
    this.traceList = res.body;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  close() {
    this.activeModal.dismiss();
  }
}

import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {TranslateService} from "@ngx-translate/core";

import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {BookService} from '../book.service';
import {TopicService} from '../../topic/topic.service';
import {EditorService} from '../../editor/editor.service';
import {AuthorService} from '../../author/author.service';
import {ClassificationService} from '../../classification/classification.service';

import {Book} from '../model/book';
import {Topic} from '../../topic/model/topic';
import {Editor} from '../../editor/model/editor';
import {Author} from '../../author/model/author';
import {Classification} from '../../classification/model/classification';

@Component({
  selector: 'app-book-management',
  templateUrl: './book-management.component.html',
  styleUrls: ['./book-management.component.css']
})
export class BookManagementComponent implements OnInit {
  @Input() book;
  topics: Topic[];
  editors: Editor[];
  authorList: Author[];
  classifications: Classification[];
  coinList: string[];
  years: string[];

  constructor(private alertService: AlertDialogService,
              private bookService: BookService,
              private topicService: TopicService,
              private editorService: EditorService,
              private authorService: AuthorService,
              private classificationService: ClassificationService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.coinList = ['$', 'U$S'];
    this.years = [];
    for (let i = 1800; i < 2100; i++) {
      this.years.push(String(i));
    }
  }

  ngOnInit() {
    this.findBook();
    this.findTopics();
    this.findEditors();
    this.findAuthors();
    this.findClassifications();
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
    if (result.body !== null) {
      if (result.body.classification) {
        result.body.classification.name = this.translateService.instant(result.body.classification.name);
      }
      this.book = result.body;
    }
    else {
      this.book = new Book();
      this.book.editionYear = '2018';
      this.book.coin = '$'
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

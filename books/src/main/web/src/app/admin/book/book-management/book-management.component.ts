import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

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
  styleUrls: ['./book-management.component.scss']
})
export class BookManagementComponent implements OnInit {
  @Input() book;
  topics: Topic[];
  editors: Editor[];
  authorList: Author[];
  classifications: Classification[];
  coinList: string[];
  years: string[];
  currentLang: string;
  toShowList: any[];
  imagePath: any;
  message: string;
  imageSize: any;
  file: File;
  load:boolean = false;

  constructor(private alertService: AlertDialogService,
              private bookService: BookService,
              private topicService: TopicService,
              private editorService: EditorService,
              private authorService: AuthorService,
              private classificationService: ClassificationService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.coinList = ['USD'];
    this.toShowList = [{elem: this.translateService.instant('user.yes'), val: true},
      {elem: this.translateService.instant('user.no'), val: false}];
    this.currentLang = this.translateService.currentLang;
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

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
    });
  }

  findBook() {
    this.bookService.getBook(this.book)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveBook() {
    this.load = true;
    this.bookService.saveBook(this.book, this.file)
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
    this.load = false;
    this.alertService.success(msg, null, null);
    this.activeModal.close();
  }

  private onSearchSuccess(result) {
    if (result.body !== null) {
      if (result.body.toShow)
        result.body.toShow.elem = this.translateService.instant(result.body.toShow.elem);
      if (result.body.classification) {
        result.body.classification.name = this.translateService.instant(result.body.classification.name);
        let lang = this.translateService.currentLang;
        if (result.body.topic) {
          if (lang === 'en')
            result.body.topic.name = result.body.topic.englishName;
          else
            result.body.topic.name = result.body.topic.spanishName;
        }
      }
      this.book = result.body;
      this.imagePath = this.book.image;
    }
    else {
      this.book = new Book();
      this.book.editionYear = '2018';
      this.book.coin = 'USD'
    }
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.load = false;
    this.alertService.error(error.error, fields, null);
  }

  cancel() {
    this.activeModal.dismiss();
  }

  preview(event) {
    let files = event.target.files;
    if (files.length === 0)
      return;

    let mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = this.translateService.instant('general.image.error');
      return;
    }
    else {
      this.message = null;
    }

    this.imageSize = this.returnFileSize(files[0].size);

    if ((this.imageSize.number > 500 && this.imageSize.type === 'KB') || this.imageSize.type === 'MB') {
      this.message = this.translateService.instant('general.image.errorSize');
      return;
    }
    else {
      let reader = new FileReader();
      reader.readAsDataURL(files[0]);
      reader.onload = (_event) => {
        this.imagePath = reader.result;
        this.file = files[0];
      };
    }
  }

  resetImage() {
    this.imageSize = '';
    this.imagePath = '';
    this.book.image = null;
    this.message = null;
  }

  returnFileSize(number) {
    if (number < 1024) {
      return {number: number, type: 'bytes'};
    } else if (number >= 1024 && number < 1048576) {
      return {number: (number / 1024).toFixed(1), type: 'KB'};
    } else if (number >= 1048576) {
      return {number: (number / 1048576).toFixed(1), type: 'MB'};
    }
  }
}

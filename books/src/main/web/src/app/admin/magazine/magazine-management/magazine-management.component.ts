import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {MagazineService} from '../magazine.service';
import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {TopicService} from '../../topic/topic.service';
import {EditorService} from '../../editor/editor.service';

import {Topic} from '../../topic/model/topic';
import {Editor} from '../../editor/model/editor';
import {Magazine} from '../model/magazine';


@Component({
  selector: 'app-magazine-management',
  templateUrl: './magazine-management.component.html',
  styleUrls: ['./magazine-management.component.scss']
})
export class MagazineManagementComponent implements OnInit {
  @Input() magazine;
  topics: Topic[];
  editors: Editor[];
  coinList: string[];
  years: string[];
  currentLang: string;
  toShowList: any[];
  imagePath: any;
  message: string;
  imageSize: any;
  file: File;

  constructor(private alertService: AlertDialogService,
              private magazineService: MagazineService,
              private topicService: TopicService,
              private editorService: EditorService,
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
    this.findMagazine();
    this.findTopics();
    this.findEditors();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
    });
  }

  findMagazine() {
    this.magazineService.getMagazine(this.magazine)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveMagazine() {
    this.magazineService.saveMagazine(this.magazine, this.file)
      .subscribe(response => this.onSuccess(response, this.magazine),
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

  private onSuccess(response, magazine) {
    let msg = 'success.add';
    if (magazine.id) {
      msg = 'success.edited';
    }
    this.magazine = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.dismiss('cancel');
  }

  private onSearchSuccess(result) {
    if (result.body !== null) {
      if (result.body.toShow)
        result.body.toShow.elem = this.translateService.instant(result.body.toShow.elem);
      this.magazine = result.body;
      this.imagePath = this.magazine.image;
    }
    else {
      this.magazine = new Magazine();
      this.magazine.publishYear = '2018';
      this.magazine.coin = 'USD'
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
      this.imagePath = files;
      reader.readAsDataURL(files[0]);
      reader.onload = (_event) => {
        this.imagePath = reader.result;
        this.file = files[0];
      }
    }
  }

  resetImage() {
    this.imageSize = '';
    this.imagePath = '';
    this.magazine.image = null;
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

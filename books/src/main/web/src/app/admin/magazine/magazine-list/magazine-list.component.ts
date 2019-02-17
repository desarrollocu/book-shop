import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {MagazineManagementComponent} from "../magazine-management/magazine-management.component";

import {TopicService} from "../../topic/topic.service";
import {EditorService} from "../../editor/editor.service";
import {MagazineService} from "../magazine.service";
import {AlertService} from "../../../shared/alert/alert.service";

import {Topic} from "../../topic/model/topic";
import {Editor} from "../../editor/model/editor";
import {Magazine} from "../model/magazine";
import {Book} from "../../book/model/book";


@Component({
  selector: 'app-magazine-list',
  templateUrl: './magazine-list.component.html',
  styleUrls: ['./magazine-list.component.scss']
})
export class MagazineListComponent implements OnInit {
  page: number;
  magazine = new Magazine();
  itemsPerPage: number;
  totalItems: any;
  magazineList: Magazine[];
  predicate: any;
  reverse: any;
  remMagazine: Magazine;
  topics: Topic[];
  editors: Editor[];
  currentLang: string;

  constructor(private magazineService: MagazineService,
              private alertService: AlertService,
              private modalService: NgbModal,
              private topicService: TopicService,
              private translateService: TranslateService,
              private editorService: EditorService) {
    this.itemsPerPage = 10;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getMagazines(null);
    this.findTopics();
    this.findEditors();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
    });
  }

  getMagazines(param) {
    if (param === 'btn')
      this.page = 0;

    this.magazineList = [];
    this.magazineService.getMagazines({
      magazineDTO: this.magazine,
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

  addMagazine() {
    const modalRef = this.modalService.open(MagazineManagementComponent, {size: 'lg'});
    modalRef.componentInstance.magazine = new Magazine();
    modalRef.result.then(value => {
      this.getMagazines('btn');
    }, (reason => {
    }))
  }

  editMagazine(magazine) {
    const modalRef = this.modalService.open(MagazineManagementComponent, {size: 'lg'});
    modalRef.componentInstance.magazine = magazine;
    modalRef.result.then(value => {
      this.getMagazines('btn');
    }, (reason => {
    }))
  }

  removeMagazine() {
    this.magazineService.deleteMagazine(this.remMagazine.id)
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

  openDialog(deleteMagazine, magazine) {
    this.remMagazine = magazine;
    this.modalService.open(deleteMagazine);
  }

  private onSuccess(res) {
    this.magazineList = res.body.elements;
    this.totalItems = res.body.total;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  onSuccessDelete() {
    this.alertService.success('success.remove', null, null);
    this.getMagazines('btn');
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

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  trackIdentity(index, item: Magazine) {
    return item.id;
  }

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }
}

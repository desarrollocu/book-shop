import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

import {TopicService} from "../../topic/topic.service";
import {EditorService} from "../../editor/editor.service";
import {MagazineService} from "../magazine.service";
import {AlertService} from "../../../shared/alert/alert.service";

import {Topic} from "../../topic/model/topic";
import {Editor} from "../../editor/model/editor";
import {Magazine} from "../model/magazine";
import {HttpResponse} from "@angular/common/http";
import {Book} from "../../book/model/book";
import {MagazineManagementComponent} from "../magazine-management/magazine-management.component";


@Component({
  selector: 'app-magazine-list',
  templateUrl: './magazine-list.component.html',
  styleUrls: ['./magazine-list.component.css']
})
export class MagazineListComponent implements OnInit {
  page: number;
  magazine = new Magazine();
  itemsPerPage: number;
  totalItems: any;
  magazineList: Magazine[];
  predicate: any;
  reverse: any;
  deleteMagazineId: string;
  topics: Topic[];
  editors: Editor[];

  constructor(private magazineService: MagazineService,
              private alertService: AlertService,
              private modalService: NgbModal,
              private topicService: TopicService,
              private editorService: EditorService) {
    this.itemsPerPage = 5;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
  }

  ngOnInit() {
    this.getMagazines(null);
    this.findTopics();
    this.findEditors();
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
    }, (reason => {
      this.getMagazines('btn');
    }))
  }

  editMagazine(magazine) {
    const modalRef = this.modalService.open(MagazineManagementComponent, {size: 'lg'});
    modalRef.componentInstance.magazine = magazine;
    modalRef.result.then(value => {
    }, (reason => {
      this.getMagazines('btn');
    }))
  }

  removeMagazine() {
    this.magazineService.deleteMagazine(this.deleteMagazineId)
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

  open(deleteMagazine, magazineId) {
    this.deleteMagazineId = magazineId;
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
    if (response)
      this.topics = response;
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

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';


import {TopicManagementComponent} from "../topic-management/topic-management.component";
import {AlertService} from "../../../shared/alert/alert.service";
import {TopicService} from "../topic.service";

import {Topic} from "../model/topic";

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent implements OnInit {
  page: number;
  topic = new Topic();
  itemsPerPage: number;
  totalItems: any;
  topicList: Topic[];
  predicate: any;
  reverse: any;
  remTopic: Topic;
  currentLang: string;

  constructor(private topicService: TopicService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private modalService: NgbModal) {
    this.itemsPerPage = 10;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getTopics(null);

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
    });
  }

  getTopics(param) {
    if (param === 'btn')
      this.page = 0;

    this.topicList = [];
    this.topicService.getTopics({
      topicDTO: this.topic,
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: HttpResponse<Topic[]>) => this.onSuccess(response),
        (response: HttpResponse<any>) => this.onError(response)
      );
  }

  addTopic() {
    const modalRef = this.modalService.open(TopicManagementComponent, {size: 'lg'});
    modalRef.componentInstance.topic = new Topic();
    modalRef.result.then(value => {
      this.getTopics('btn');
    }, (reason => {
    }))
  }

  editTopic(topic) {
    const modalRef = this.modalService.open(TopicManagementComponent, {size: 'lg'});
    modalRef.componentInstance.topic = topic;
    modalRef.result.then(value => {
      this.getTopics('btn');
    }, (reason => {
    }));
  }

  removeTopic() {
    this.topicService.deleteTopic(this.remTopic.id)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancelMy();
  }

  openDialog(deleteTopic, topic) {
    this.remTopic = topic;
    this.modalService.open(deleteTopic);
  }

  private onSuccess(res) {
    this.topicList = res.body.elements;
    this.totalItems = res.body.total;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  onSuccessDelete() {
    this.alertService.success('success.remove', null, null);
    this.getTopics('btn');
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

  trackIdentity(index, item: Topic) {
    return item.id;
  }

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }
}

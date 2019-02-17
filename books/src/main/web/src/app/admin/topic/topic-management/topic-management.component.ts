import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

import {AlertDialogService} from "../../../shared/alert/alert.dialog.service";
import {TopicService} from "../topic.service";

import {Topic} from "../model/topic";

@Component({
  selector: 'app-topic-management',
  templateUrl: './topic-management.component.html',
  styleUrls: ['./topic-management.component.scss']
})
export class TopicManagementComponent implements OnInit {
  @Input() topic;
  load: boolean = false;

  constructor(private alertService: AlertDialogService,
              private topicService: TopicService,
              public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
    this.findTopic();
  }

  findTopic() {
    this.topicService.getElement(this.topic)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveTopic() {
    this.load = false;
    this.topicService.saveTopic(this.topic)
      .subscribe(response => this.onSuccess(response, this.topic),
        response => this.onError(response));
  }

  private onSuccess(response, editor) {
    let msg = 'success.add';
    if (editor.id) {
      msg = 'success.edited';
    }
    this.topic = response.body;
    this.load = false;
    this.alertService.success(msg, null, null);
    this.activeModal.close();
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.topic = result.body;
    else {
      this.topic = new Topic();
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
}

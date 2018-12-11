import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

import {AlertService} from "../../../shared/alert/alert.service";
import {AuthorService} from "../author.service";
import {Author} from '../model/author';

@Component({
  selector: 'app-author-management',
  templateUrl: './author-management.component.html',
  styleUrls: ['./author-management.component.css']
})
export class AuthorManagementComponent implements OnInit {
  @Input() author;

  constructor(private alertService: AlertService,
              private authorService: AuthorService,
              public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
    this.findAuthor();
  }

  findAuthor() {
    this.authorService.getAuthor(this.author)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveAuthor() {
    this.authorService.saveAuthor(this.author)
      .subscribe(response => this.onSuccess(response, this.author),
        response => this.onError(response));
  }

  private onSuccess(response, author) {
    let msg = 'success.add';
    if (author.id) {
      msg = 'success.edited';
    }
    this.author = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.dismiss('cancel');
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.author = result.body;
    else {
      this.author = new Author();
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

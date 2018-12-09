import {Component, Input, OnInit} from '@angular/core';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {User} from '../../../core/user/user.model';
import {UserService} from '../user.service';
import {AlertService} from "../../../shared/alert/alert.service";
import {Value} from '../model/value';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.modal.component.html',
  styleUrls: ['./user-management.modal.component.css']
})
export class UserManagementModalComponent implements OnInit {
  @Input() user;
  pass: string;
  languages: string[];
  values: Value[];

  constructor(private userService: UserService,
              private alertService: AlertService,
              public activeModal: NgbActiveModal) {
    this.values = [];
    this.pass = null;
    this.languages = ["en", "es"];
    this.values.push(new Value("true", "user.yes"));
    this.values.push(new Value("false", "user.no"));
  }

  ngOnInit() {
    this.findUser();
  }

  saveUser() {
    this.userService.saveUser(this.user)
      .subscribe(response => this.onSuccess(response),
        response => this.onError(response));
  }

  findUser() {
    this.userService.getUser(this.user)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.user = result.body;
    else {
      this.user = new User();
      this.user.langKey = "en";
      this.user.isAdmin = "true";
    }
  }

  cancel() {
    this.activeModal.dismiss('cancel');
  }

  private onSuccess(response) {
    this.user = response.body;
    this.alertService.success('success.add', null, null);
    this.activeModal.dismiss('cancel');
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }
}

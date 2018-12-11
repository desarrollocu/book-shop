import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from '../user.service';
import {AlertService} from '../../../shared/alert/alert.service';
import {User} from '../../../core/user/user.model';
import {UserManagementModalComponent} from "../user-management/user-management.modal.component";


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  page: number;
  user = new User;
  itemsPerPage: number;
  totalItems: any;
  userList: User[];
  predicate: any;
  reverse: any;
  deleteUserId: string;

  constructor(private userService: UserService,
              private alertService: AlertService,
              private modalService: NgbModal) {
    this.itemsPerPage = 5;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
  }

  ngOnInit() {
    this.getUsers(null);
  }

  getUsers(param) {
    if (param === 'btn')
      this.page = 0;

    this.userList = [];
    this.userService.getUsers({
      userDTO: this.user,
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: HttpResponse<User[]>) => this.onSuccess(response),
        (response: HttpResponse<any>) => this.onError(response)
      );
  }

  addUser() {
    const modalRef = this.modalService.open(UserManagementModalComponent, {size: 'lg'});
    modalRef.componentInstance.user = new User();
    modalRef.result.then(value => {
    }, (reason => {
      this.getUsers('btn');
    }))
  }

  editUser(user) {
    const modalRef = this.modalService.open(UserManagementModalComponent, {size: 'lg'});
    modalRef.componentInstance.user = user;
    modalRef.result.then(value => {
    }, (reason => {
      this.getUsers('btn');
    }))
  }

  removeUser() {
    this.userService.deleteUser(this.deleteUserId)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancel();
  }

  open(deleteUser, userId) {
    this.deleteUserId = userId;
    this.modalService.open(deleteUser);
  }

  private onSuccess(res) {
    this.userList = res.body.elements;
    this.totalItems = res.body.total;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  onSuccessDelete() {
    this.alertService.success('success.remove', null, null);
    this.getUsers('btn');
  }

  onErrorDelete(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }
}

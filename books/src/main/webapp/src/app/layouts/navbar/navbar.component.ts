import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {LoginModalService} from '../../core/login/login-modal.service';
import {LoginService} from '../../core/login/login.service';
import {Principal} from '../../core/auth/principal.service';
import {StateStorageService} from '../../core/auth/state-storage.service';
import {UserService} from '../../admin/user/user.service';
import {AlertService} from '../../shared/alert/alert.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  navbarOpen: boolean;
  enActive: boolean;
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
  modalRef: NgbModalRef;

  constructor(private loginService: LoginService,
              private principal: Principal,
              private alertService: AlertService,
              private modalService: NgbModal,
              private userService: UserService,
              private loginModalService: LoginModalService,
              private router: Router,
              private stateStorageService: StateStorageService,
              private translate: TranslateService) {

    this.enActive = true;
    this.navbarOpen = false;
    let language = this.stateStorageService.getLanguage();
    if (language) {
      language === 'en' ? this.enActive = true : this.enActive = false;
      this.translate.use(language);
    }
  }

  ngOnInit() {
  }

  useLanguage(language: string) {
    this.stateStorageService.storeLanguage(language);
    language === 'es' ? this.enActive = false : this.enActive = true;
    this.translate.use(language);

    if (this.principal.isAuthenticated()) {
      this.userService.changeLang(language)
        .subscribe(response => this.onSuccess(response, 'lang'),
          response => this.onError(response));
    }
  }

  getUserData() {
    return this.principal.fullName();
  }

  isAuthenticated() {
    return this.principal.isAuthenticated();
  }

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

  collapseNavbar() {
    this.navbarOpen = false;
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  private onSuccess(response, key) {
    if (key === 'lang')
      this.alertService.success('success.language', null, null);
    else {
      this.alertService.success('success.changePassword', null, null);
      this.modalService.dismissAll('cancel');
    }
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  changePassword(changeUserPass) {
    this.newPassword = "";
    this.currentPassword = "";
    this.confirmPassword = "";
    this.modalService.open(changeUserPass);
  }

  save() {
    if (this.newPassword === this.confirmPassword)
      this.userService.changePassword(this.currentPassword, this.newPassword)
        .subscribe(response => this.onSuccess(response, 'pass'),
          response => this.onError(response));
    else {
      this.alertService.error('error.password.equal', null, null);
    }
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

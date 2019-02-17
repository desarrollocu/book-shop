import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {AlertDialogService} from '../../shared/alert/alert.dialog.service';
import {LoginModalService} from '../../core/login/login-modal.service';
import {LoginService} from '../../core/login/login.service';
import {Principal} from '../../core/auth/principal.service';
import {CartService} from '../../search/cart.service';
import {StateStorageService} from '../../core/auth/state-storage.service';
import {UserService} from '../../admin/user/user.service';
import {RegisterModalComponent} from '../../shared/register/register.modal.component';
import {AccountModalComponent} from '../../shared/account/account.modal.component';

import {UiData} from '../../search/model/uiData';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  navbarOpen: boolean;
  enActive: boolean;
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
  modalRef: NgbModalRef;
  userData: string;
  uiData: UiData;
  lat: number = -34.898702;
  lng: number = -56.178551;

  constructor(private loginService: LoginService,
              private principal: Principal,
              private alertService: AlertDialogService,
              private modalService: NgbModal,
              private userService: UserService,
              private cartService: CartService,
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
    this.getUIData();
    this.translate.onLangChange.subscribe((event: LangChangeEvent) => {
      this.enActive = event.lang === 'en' ? true : false;
    });

    this.principal.fullName().subscribe((value => {
      this.userData = value;
    }))
  }

  getUIData() {
    this.principal.getUIData({})
      .subscribe(response => this.onUIDataSuccess(response),
        response => this.onUIDataError(response));
  }

  private onUIDataError(response) {
    let error = response.error;
  }

  private onUIDataSuccess(res) {
    this.uiData = res.body;
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
    else {
      this.alertService.success('success.language', null, null);
    }
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.cartService.setCartCant(0);
    this.cartService.getCarSubject().next(0);
    this.loginService.logout();
    this.router.navigate(['']);
  }

  changePassword(changeUserPass) {
    this.newPassword = "";
    this.currentPassword = "";
    this.confirmPassword = "";
    this.modalService.open(changeUserPass);
  }

  showContacts(contacts) {
    this.modalService.open(contacts, {size: 'lg'});
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

  register() {
    this.modalService.open(RegisterModalComponent, {size: 'lg'});
  }

  accountInfo() {
    this.modalService.open(AccountModalComponent, {size: 'lg'});
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

  twoPass() {
    if (this.newPassword && this.confirmPassword) {
      if (this.newPassword === this.confirmPassword)
        return false;
      else
        return true;
    }
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

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

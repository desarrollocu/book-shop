import {AfterViewInit, Component} from '@angular/core';
import {Router} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {LoginService} from '../../core/login/login.service';
import {StateStorageService} from '../../core/auth/state-storage.service';
import {CartService} from "../../search/cart.service";

@Component({
  selector: 'app-login',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent implements AfterViewInit {
  authenticationError: boolean;
  password: string;
  rememberMe: boolean;
  username: string;
  credentials: any;

  constructor(private loginService: LoginService,
              private router: Router,
              private cartService: CartService,
              private stateStorageService: StateStorageService,
              public activeModal: NgbActiveModal) {
    this.credentials = {};
  }

  ngAfterViewInit(): void {
  }

  login() {
    this.loginService
      .login({
        username: this.username,
        password: this.password,
        rememberMe: this.rememberMe
      })
      .then(() => {
        this.authenticationError = false;
        this.activeModal.dismiss('login success');
        // this.cartService.setCartCant(0);
        // this.cartService.getCarSubject().next(0);
        if (this.router.url === 'register') {
          this.router.navigate(['']);
        }

        const redirect = this.stateStorageService.getUrl();
        if (redirect) {
          this.stateStorageService.storeUrl(null);
          this.router.navigate([redirect]);
        }
      })
      .catch(() => {
        this.authenticationError = true;
      });
  }

  cancel() {
    this.credentials = {
      username: null,
      password: null,
      rememberMe: true
    };
    this.authenticationError = false;
    this.activeModal.dismiss('cancel');
  }
}

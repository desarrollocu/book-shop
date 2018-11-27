import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../core/login/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  authenticationError: boolean;
  password: string;
  rememberMe: boolean;
  username: string;
  credentials: any;

  constructor(private loginService: LoginService, private router: Router) {
    this.credentials = {};
  }

  ngOnInit() {
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
        this.router.navigateByUrl('/user-list');
      })
      .catch(() => {
        this.authenticationError = true;
      });
  }
}

import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../core/login/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['product-list']);
  }
}

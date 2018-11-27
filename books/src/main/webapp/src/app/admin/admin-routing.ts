import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserManagementComponent} from './user-management/user-management.component';
import {RouteAccessService} from '../core/auth/route-access-service';
import {LoginComponent} from './login/login.component';
import {UserListComponent} from "./user-list/user-list.component";

const routes: Routes = [
  {path: "login", component: LoginComponent}
  , {
    path: "user-management", component: UserManagementComponent,
    data: {
      pageTitle: 'User-Management'
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "user-list", component: UserListComponent,
    data: {
      pageTitle: 'Users',
      defaultSort: 'id,asc'
    },
    canActivate: [RouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}

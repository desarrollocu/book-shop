import {NgModule} from '@angular/core';

import {UserManagementComponent} from './user-management/user-management.component';
import {SharedModule} from '../shared/shared.module';
import {AdminRoutingModule} from './admin-routing';
import {LoginComponent} from './login/login.component';
import { UserListComponent } from './user-list/user-list.component';

@NgModule({
  declarations: [LoginComponent, UserManagementComponent, UserListComponent],
  imports: [
    SharedModule,
    AdminRoutingModule
  ]
})
export class AdminModule {
}

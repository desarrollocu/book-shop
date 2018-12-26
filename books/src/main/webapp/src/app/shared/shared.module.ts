import {NgModule} from '@angular/core';
import {SharedLibsModule} from './shared-libs.module';
import {SharedCommonModule} from './shared-common.module';
import {LoginModalComponent} from "./login/login-modal.component";
import {HasAnyAuthorityDirective} from './auth/has-any-authority.directive';
import {RegisterModalComponent} from './register/register.modal.component';
import {PasswordStrengthBarComponent} from './auth/password-strength-bar.component';
import {AccountModalComponent} from './account/account.modal.component';

@NgModule({
  imports: [
    SharedLibsModule, SharedCommonModule
  ],
  declarations: [LoginModalComponent, RegisterModalComponent, AccountModalComponent,
    PasswordStrengthBarComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent, RegisterModalComponent, AccountModalComponent],
  exports: [SharedCommonModule, LoginModalComponent, RegisterModalComponent, AccountModalComponent,
    PasswordStrengthBarComponent, HasAnyAuthorityDirective]
})
export class SharedModule {
  static forRoot() {
    return {
      ngModule: SharedModule
    };
  }
}

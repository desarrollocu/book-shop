import {NgModule} from '@angular/core';
import {SharedLibsModule} from './shared-libs.module';
import {SharedCommonModule} from './shared-common.module';
import {LoginModalComponent} from "./login/login-modal.component";
import {HasAnyAuthorityDirective} from './auth/has-any-authority.directive';
import {RegisterModalComponent} from './register/register.modal.component';

@NgModule({
  imports: [
    SharedLibsModule, SharedCommonModule
  ],
  declarations: [LoginModalComponent, RegisterModalComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent, RegisterModalComponent],
  exports: [SharedCommonModule, LoginModalComponent, RegisterModalComponent, HasAnyAuthorityDirective]
})
export class SharedModule {
  static forRoot() {
    return {
      ngModule: SharedModule
    };
  }
}

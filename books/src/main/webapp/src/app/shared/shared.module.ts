import {NgModule} from '@angular/core';
import {SharedLibsModule} from './shared-libs.module';
import {SharedCommonModule} from './shared-common.module';
import {LoginModalComponent} from "./login/login-modal.component";
import {HasAnyAuthorityDirective} from './auth/has-any-authority.directive';

@NgModule({
  imports: [
    SharedLibsModule, SharedCommonModule
  ],
  declarations: [LoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent],
  exports: [SharedCommonModule, LoginModalComponent, HasAnyAuthorityDirective]
})
export class SharedModule {
  static forRoot() {
    return {
      ngModule: SharedModule
    };
  }
}

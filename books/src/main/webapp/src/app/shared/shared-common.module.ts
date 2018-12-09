import {NgModule} from '@angular/core';

import {SharedLibsModule} from './shared-libs.module';
import {TranslateModule} from '@ngx-translate/core';
import {AlertComponent} from "./alert/alert.component";

@NgModule({
  imports: [SharedLibsModule, TranslateModule],
  declarations: [AlertComponent],
  exports: [SharedLibsModule, AlertComponent, TranslateModule]
})
export class SharedCommonModule {
}

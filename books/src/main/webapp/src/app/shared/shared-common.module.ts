import {NgModule} from '@angular/core';

import {SharedLibsModule} from './shared-libs.module';
import {TranslateModule} from '@ngx-translate/core';
import {AlertComponent} from "./alert/alert.component";
import {AlertDialogComponent} from "./alert/alert.dialog.component";

@NgModule({
  imports: [SharedLibsModule, TranslateModule],
  declarations: [AlertComponent, AlertDialogComponent],
  exports: [SharedLibsModule, AlertComponent, AlertDialogComponent, TranslateModule]
})
export class SharedCommonModule {
}

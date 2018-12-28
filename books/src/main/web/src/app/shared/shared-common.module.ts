import {NgModule} from '@angular/core';

import {SharedLibsModule} from './shared-libs.module';
import {TranslateModule} from '@ngx-translate/core';


@NgModule({
  imports: [SharedLibsModule, TranslateModule],
  declarations: [],
  exports: [SharedLibsModule, TranslateModule]
})
export class SharedCommonModule {
}

import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [NgbModule],
  exports: [FormsModule, CommonModule, NgbModule]
})
export class SharedLibsModule {
  static forRoot() {
    return {
      ngModule: SharedLibsModule
    };
  }
}

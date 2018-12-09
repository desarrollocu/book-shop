import {NgModule} from '@angular/core';
import {SearchGeneralComponent} from './search-general/search-general.component';
import {SearchBookComponent} from './search-book/search-book.component';
import {SearchMagazineComponent} from './search-magazine/search-magazine.component';
import {SharedModule} from '../shared/shared.module';
import {SearchRoutingModule} from './search-routing';

@NgModule({
  declarations: [SearchGeneralComponent, SearchBookComponent, SearchMagazineComponent],
  imports: [
    SharedModule,
    SearchRoutingModule
  ]
})
export class SearchModule {
}

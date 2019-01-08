import {NgModule} from '@angular/core';

import {SearchGeneralComponent} from './search-general/search-general.component';
import {SearchBookComponent} from './search-book/search-book.component';
import {SearchMagazineComponent} from './search-magazine/search-magazine.component';
import {SharedModule} from '../shared/shared.module';
import {SearchRoutingModule} from './search-routing';
import {CartComponent} from './cart/cart.component';
import {PaypalComponent} from './paypal/paypal.component';


@NgModule({
  declarations: [SearchGeneralComponent, SearchBookComponent,
    SearchMagazineComponent, CartComponent, PaypalComponent],
  imports: [
    SharedModule,
    SearchRoutingModule
  ]
})
export class SearchModule {
}

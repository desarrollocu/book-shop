import {NgModule} from '@angular/core';

import {SearchGeneralComponent} from './search-general/search-general.component';
import {SearchBookComponent} from './search-book/search-book.component';
import {SearchMagazineComponent} from './search-magazine/search-magazine.component';
import {SharedModule} from '../shared/shared.module';
import {SearchRoutingModule} from './search-routing';
import {CartComponent} from './cart/cart.component';
import {PaypalComponent} from './paypal/paypal.component';
import { ShippingCostComponent } from './shipping-cost/shipping-cost.component';
import { SearchSaleComponent } from './search-sale/search-sale.component';


@NgModule({
  declarations: [SearchGeneralComponent, SearchBookComponent,
    SearchMagazineComponent, CartComponent, PaypalComponent, ShippingCostComponent, SearchSaleComponent],
  imports: [
    SharedModule,
    SearchRoutingModule
  ]
})
export class SearchModule {
}

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductListComponent} from './product-list/product-list.component';
import {ProductCarComponent} from './product-car/product-car.component';

const routes: Routes = [
  {
    path: "product-list", component: ProductListComponent,
    data: {
      pageTitle: 'Book-Shop',
      defaultSort: 'id,asc'
    }
  },
  {
    path: "product-car", component: ProductCarComponent,
    data: {
      pageTitle: 'Shopping',
      defaultSort: 'id,asc'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule {
}

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookListComponent} from './book-list/book-list.component';
import {RouteAccessService} from "../core/auth/route-access-service";

const routes: Routes = [
  {
    path: "book-list", component: BookListComponent,
    data: {
      pageTitle: 'Books',
      defaultSort: 'id,asc'
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "book-management", component: BookListComponent,
    data: {
      pageTitle: 'Book-Management',
    },
    canActivate: [RouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule {
}

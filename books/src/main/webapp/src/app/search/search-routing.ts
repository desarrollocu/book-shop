import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchGeneralComponent} from './search-general/search-general.component';
import {SearchBookComponent} from './search-book/search-book.component';
import {SearchMagazineComponent} from './search-magazine/search-magazine.component';
import {RouteAccessService} from "../core/auth/route-access-service";

const routes: Routes = [
  {
    path: "search-general", component: SearchGeneralComponent,
    data: {
      authorities: [],
      pageTitle: 'BooksStore',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "search-book", component: SearchBookComponent,
    data: {
      authorities: [],
      pageTitle: 'BooksStore'
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "search-magazine", component: SearchMagazineComponent,
    data: {
      authorities: [],
      pageTitle: 'BooksStore'
    },
    canActivate: [RouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchRoutingModule {
}

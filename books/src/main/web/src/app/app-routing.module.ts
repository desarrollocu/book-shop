import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from './layouts/page-not-found/page-not-found.component';
import {ErrorComponent} from "./layouts/error/error.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/search-general',
    pathMatch: 'full'
  },
  {path: '**', component: PageNotFoundComponent},
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'BooksStore'
    }
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'BooksStore',
      error403: true
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

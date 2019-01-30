import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserManagementModalComponent} from './user/user-management/user-management.modal.component';
import {RouteAccessService} from '../core/auth/route-access-service';
import {LoginModalComponent} from '../shared/login/login-modal.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {BookListComponent} from './book/book-list/book-list.component';
import {AuthorManagementComponent} from './author/author-management/author-management.component';
import {AuthorListComponent} from './author/author-list/author-list.component';
import {EditorManagementComponent} from './editor/editor-management/editor-management.component';
import {EditorListComponent} from './editor/editor-list/editor-list.component';
import {MagazineManagementComponent} from './magazine/magazine-management/magazine-management.component';
import {MagazineListComponent} from './magazine/magazine-list/magazine-list.component';
import {TopicListComponent} from './topic/topic-list/topic-list.component';
import {TopicManagementComponent} from './topic/topic-management/topic-management.component';
import {ContactListComponent} from './contact/contact-list/contact-list.component';
import {SaleListComponent} from "./sale/sale-list/sale-list.component";
import {SaleManagementComponent} from "./sale/sale-management/sale-management.component";


const routes: Routes = [
  {path: "login", component: LoginModalComponent},
  {
    path: "user-management", component: UserManagementModalComponent,
    data: {
      authorities: ['user-management'],
      pageTitle: 'User-Management'
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "user-list", component: UserListComponent,
    data: {
      authorities: ['user-list'],
      pageTitle: 'Users',
      defaultSort: 'id,asc'
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "book-list", component: BookListComponent,
    data: {
      authorities: ['book-list'],
      pageTitle: 'Books',
      defaultSort: 'id,asc'
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "book-management", component: BookListComponent,
    data: {
      authorities: ['book-management'],
      pageTitle: 'Book-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "author-management", component: AuthorManagementComponent,
    data: {
      authorities: ['author-management'],
      pageTitle: 'Author-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "author-list", component: AuthorListComponent,
    data: {
      authorities: ['author-list'],
      pageTitle: 'Author-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "editor-management", component: EditorManagementComponent,
    data: {
      authorities: ['editor-management'],
      pageTitle: 'Editor-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "editor-list", component: EditorListComponent,
    data: {
      authorities: ['editor-list'],
      pageTitle: 'Editor-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "magazine-management", component: MagazineManagementComponent,
    data: {
      authorities: ['magazine-management'],
      pageTitle: 'Magazine-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "magazine-list", component: MagazineListComponent,
    data: {
      authorities: ['magazine-list'],
      pageTitle: 'Magazine-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "topic-list", component: TopicListComponent,
    data: {
      authorities: ['topic-list'],
      pageTitle: 'Topic-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "topic-management", component: TopicManagementComponent,
    data: {
      authorities: ['topic-management'],
      pageTitle: 'Topic-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "sale-list", component: SaleListComponent,
    data: {
      authorities: ['sale-list'],
      pageTitle: 'Sale-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "sale-management", component: SaleManagementComponent,
    data: {
      authorities: ['sale-management'],
      pageTitle: 'Sale-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "info-management", component: ContactListComponent,
    data: {
      authorities: ['info-management'],
      pageTitle: 'Info-Management',
    },
    canActivate: [RouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}

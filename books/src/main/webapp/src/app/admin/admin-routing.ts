import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserManagementModalComponent} from './user/user-management/user-management.modal.component';
import {RouteAccessService} from '../core/auth/route-access-service';
import {LoginModalComponent} from '../shared/login/login-modal.component';
import {UserListComponent} from './user/user-list/user-list.component';
import {BookListComponent} from './book/book-list/book-list.component';
import {AuthorManagementComponent} from "./author/author-management/author-management.component";
import {AuthorListComponent} from "./author/author-list/author-list.component";
import {EditorManagementComponent} from "./editor/editor-management/editor-management.component";
import {EditorListComponent} from "./editor/editor-list/editor-list.component";
import {PublisherManagementComponent} from "./publisher/publisher-management/publisher-management.component";
import {PublisherListComponent} from "./publisher/publisher-list/publisher-list.component";
import {DescriptorManagementComponent} from "./descriptor/descriptor-management/descriptor-management.component";
import {DescriptorListComponent} from "./descriptor/descriptor-list/descriptor-list.component";
import {CountryManagementComponent} from "./country/country-management/country-management.component";
import {CountryListComponent} from "./country/country-list/country-list.component";
import {MagazineManagementComponent} from "./magazine/magazine-management/magazine-management.component";
import {MagazineListComponent} from "./magazine/magazine-list/magazine-list.component";
import {TopicListComponent} from "./topic/topic-list/topic-list.component";
import {TopicManagementComponent} from "./topic/topic-management/topic-management.component";

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
    path: "publisher-management", component: PublisherManagementComponent,
    data: {
      authorities: ['publisher-management'],
      pageTitle: 'Publisher-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "publisher-list", component: PublisherListComponent,
    data: {
      authorities: ['publisher-list'],
      pageTitle: 'Publisher-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "descriptor-management", component: DescriptorManagementComponent,
    data: {
      authorities: ['descriptor-management'],
      pageTitle: 'Descriptor-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "descriptor-list", component: DescriptorListComponent,
    data: {
      authorities: ['descriptor-list'],
      pageTitle: 'Descriptor-List',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "country-management", component: CountryManagementComponent,
    data: {
      authorities: ['country-management'],
      pageTitle: 'Country-Management',
    },
    canActivate: [RouteAccessService]
  },
  {
    path: "country-list", component: CountryListComponent,
    data: {
      authorities: ['country-list'],
      pageTitle: 'Country-List',
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
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}

import {NgModule} from '@angular/core';

import {UserManagementModalComponent} from './user/user-management/user-management.modal.component';
import {SharedModule} from '../shared/shared.module';
import {AdminRoutingModule} from './admin-routing';
import {UserListComponent} from './user/user-list/user-list.component';
import {AuthorListComponent} from './author/author-list/author-list.component';
import {AuthorManagementComponent} from './author/author-management/author-management.component';
import {EditorManagementComponent} from './editor/editor-management/editor-management.component';
import {EditorListComponent} from './editor/editor-list/editor-list.component';
import {DescriptorListComponent} from './descriptor/descriptor-list/descriptor-list.component';
import {DescriptorManagementComponent} from './descriptor/descriptor-management/descriptor-management.component';
import {PublisherManagementComponent} from './publisher/publisher-management/publisher-management.component';
import {PublisherListComponent} from './publisher/publisher-list/publisher-list.component';
import {CountryListComponent} from './country/country-list/country-list.component';
import {CountryManagementComponent} from './country/country-management/country-management.component';
import {BookListComponent} from './book/book-list/book-list.component';
import {BookManagementComponent} from './book/book-management/book-management.component';
import {MagazineManagementComponent} from './magazine/magazine-management/magazine-management.component';
import {MagazineListComponent} from './magazine/magazine-list/magazine-list.component';

@NgModule({
  declarations: [
    UserManagementModalComponent,
    UserListComponent,
    BookListComponent,
    BookManagementComponent,
    AuthorListComponent,
    AuthorManagementComponent,
    EditorManagementComponent,
    EditorListComponent,
    DescriptorListComponent,
    DescriptorManagementComponent,
    PublisherManagementComponent,
    PublisherListComponent,
    CountryListComponent,
    CountryManagementComponent,
    MagazineManagementComponent,
    MagazineListComponent],
  imports: [
    SharedModule,
    AdminRoutingModule
  ],
  entryComponents: [UserManagementModalComponent]
})
export class AdminModule {
}

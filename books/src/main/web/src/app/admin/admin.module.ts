import {NgModule} from '@angular/core';
import {AngularEditorModule} from '@kolkov/angular-editor';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';

import {UserManagementModalComponent} from './user/user-management/user-management.modal.component';
import {SharedModule} from '../shared/shared.module';
import {AdminRoutingModule} from './admin-routing';
import {UserListComponent} from './user/user-list/user-list.component';
import {AuthorListComponent} from './author/author-list/author-list.component';
import {AuthorManagementComponent} from './author/author-management/author-management.component';
import {EditorManagementComponent} from './editor/editor-management/editor-management.component';
import {EditorListComponent} from './editor/editor-list/editor-list.component';
import {BookListComponent} from './book/book-list/book-list.component';
import {BookManagementComponent} from './book/book-management/book-management.component';
import {MagazineManagementComponent} from './magazine/magazine-management/magazine-management.component';
import {MagazineListComponent} from './magazine/magazine-list/magazine-list.component';
import {TopicManagementComponent} from './topic/topic-management/topic-management.component';
import {TopicListComponent} from './topic/topic-list/topic-list.component';
import {ContactListComponent} from './contact/contact-list/contact-list.component';
import {SaleListComponent} from './sale/sale-list/sale-list.component';
import {SaleManagementComponent} from './sale/sale-management/sale-management.component';
import {BookTraceComponent} from './book/book-trace/book-trace.component';


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
    MagazineManagementComponent,
    MagazineListComponent,
    TopicManagementComponent,
    TopicListComponent,
    ContactListComponent,
    SaleListComponent,
    SaleManagementComponent,
    BookTraceComponent
  ],
  imports: [
    BrowserModule, FormsModule, AngularEditorModule,
    SharedModule,
    AdminRoutingModule
  ],
  entryComponents: [AuthorManagementComponent, BookManagementComponent, BookTraceComponent, EditorManagementComponent,
    MagazineManagementComponent, SaleManagementComponent, TopicManagementComponent, UserManagementModalComponent]
})
export class AdminModule {
}

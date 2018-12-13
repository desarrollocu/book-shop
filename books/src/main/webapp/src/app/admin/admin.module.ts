import {NgModule} from '@angular/core';

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
import {DocsComponent} from './docs/docs.component';

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
    DocsComponent],
  imports: [
    SharedModule,
    AdminRoutingModule
  ],
  entryComponents: [UserManagementModalComponent, AuthorManagementComponent,
    TopicManagementComponent, BookManagementComponent, EditorManagementComponent]
})
export class AdminModule {
}

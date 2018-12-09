import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {MainComponent} from './layouts/main/main.component';
import {CoreModule} from './core/core.module';
import {AdminModule} from './admin/admin.module';
import {SharedModule} from "./shared/shared.module";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {AuthExpiredInterceptor} from './core/interceptor/auth-expired.interceptor';
import {PageNotFoundComponent} from './layouts/page-not-found/page-not-found.component';
import {NavbarComponent} from './layouts/navbar/navbar.component';
import {SearchModule} from './search/search.module';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {ErrorComponent} from './layouts/error/error.component';
import {SidebarComponent} from './layouts/sidebar/sidebar.component';
import {FooterComponent} from './layouts/footer/footer.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NgxWebstorageModule.forRoot(),
    SharedModule.forRoot(),
    CoreModule,
    AdminModule,
    SearchModule,
    AppRoutingModule
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageNotFoundComponent,
    ErrorComponent,
    SidebarComponent,
    FooterComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    }
  ],
  bootstrap: [MainComponent]
})
export class AppModule {
}

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

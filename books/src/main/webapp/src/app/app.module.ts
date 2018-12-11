import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {NgxWebstorageModule} from 'ngx-webstorage';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';


import {AppRoutingModule} from './app-routing.module';
import {CoreModule} from './core/core.module';
import {AdminModule} from './admin/admin.module';
import {SharedModule} from "./shared/shared.module";
import {SearchModule} from './search/search.module';
import {AuthExpiredInterceptor} from './core/interceptor/auth-expired.interceptor';

import {MainComponent} from './layouts/main/main.component';
import {PageNotFoundComponent} from './layouts/page-not-found/page-not-found.component';
import {NavbarComponent} from './layouts/navbar/navbar.component';
import {ErrorComponent} from './layouts/error/error.component';
import {SidebarComponent} from './layouts/sidebar/sidebar.component';
import {FooterComponent} from './layouts/footer/footer.component';

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

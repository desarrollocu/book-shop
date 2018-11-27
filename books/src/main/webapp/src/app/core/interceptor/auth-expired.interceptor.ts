import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {AuthServerProvider} from '../auth/auth.service';
import {Router} from "@angular/router";


@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
  constructor(
    private authServerProvider: AuthServerProvider,
    private router: Router
  ) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap(
        (event: HttpEvent<any>) => {
        },
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401 && err.url && !err.url.includes('/api/account')) {
              this.authServerProvider.logout();
              this.router.navigateByUrl('/product-list');
            }
            if (err.status === 403 && err.url && !err.url.includes('/api/account')) {
              this.authServerProvider.logout();
              this.router.navigateByUrl('/product-list');
            }
          }
        }
      )
    );
  }
}

import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

import {LoginModalService} from '../login/login-modal.service';
import {AuthServerProvider} from '../auth/auth.service';
import {StateStorageService} from '../auth/state-storage.service';


@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
  constructor(
    private loginModalService: LoginModalService,
    private authServerProvider: AuthServerProvider,
    private stateStorageService: StateStorageService
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
              const destination = this.stateStorageService.getDestinationState();
              if (destination !== null) {
                const to = destination.destination;
                const toParams = destination.params;
                if (to.name === 'search-general') {
                  this.stateStorageService.storePreviousState(to.name, toParams);
                }
              } else {
                this.stateStorageService.storeUrl('/');
              }

              this.authServerProvider.logout();
              this.loginModalService.open();
            }
          }
          // if (err instanceof HttpErrorResponse) {
          //   if (err.status === 401 && err.url) {
          //     this.stateStorageService.storeUrl('/');
          //     this.authServerProvider.logout();
          //     this.loginModalService.open();
          //     this.router.navigateByUrl('');
          //   }
          // }
        }
      )
    );
  }
}

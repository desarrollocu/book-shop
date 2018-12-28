import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

import {Observable} from 'rxjs';
import {Principal} from './principal.service';
import {StateStorageService} from './state-storage.service';
import {LoginModalService} from '../login/login-modal.service';


@Injectable({providedIn: 'root'})
export class RouteAccessService implements CanActivate {

  constructor(
    private router: Router,
    private loginModalService: LoginModalService,
    private principal: Principal,
    private stateStorageService: StateStorageService,
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const authorities = route.data['authorities'];
    // We need to call the checkLogin / and so the principal.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    // This could happen on a page refresh.
    let temp = state.url.split("/");
    return this.checkLogin(authorities, temp[1]);
  }

  checkLogin(authorities: string[], url: string): Promise<boolean> {
    const principal = this.principal;
    return Promise.resolve(
      principal.identity(true).then(account => {
        if (!authorities || authorities.length === 0) {
          return true;
        }

        if (account) {
          return principal.hasAnyAuthority(url).then(response => {
            if (response) {
              return true;
            }
            else {
              if (!url.split("-").includes("management"))
                this.stateStorageService.storeUrl(url);
              this.router.navigate(['/search-general']).then(() => {
                // only show the login dialog, if the user hasn't logged in yet
                if (!account) {
                  this.loginModalService.open();
                }
              });
              return false;
            }
          });
        }

        if (!url.split("-").includes("management"))
          this.stateStorageService.storeUrl(url);
        this.router.navigate(['/search-general']).then(() => {
          // only show the login dialog, if the user hasn't logged in yet
          if (!account) {
            this.loginModalService.open();
          }
        });
        return false;
      })
    );
  }
}

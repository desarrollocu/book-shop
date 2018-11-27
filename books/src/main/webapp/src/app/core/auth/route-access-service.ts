import {Injectable, isDevMode} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

import {Observable} from 'rxjs';
import {Principal} from './principal.service';


@Injectable({providedIn: 'root'})
export class RouteAccessService implements CanActivate {

  constructor(
    private router: Router,
    private principal: Principal,
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    // const authorities = route.data['authorities'];
    let auth = state.url.split('/');
    return this.checkLogin(auth[1]);
  }

  checkLogin(url: string): Promise<boolean> {
    const principal = this.principal;
    return Promise.resolve(
      principal.identity().then(account => {
        if (account) {
          return principal.hasAnyAuthority(url).then(response => {
            if (response) {
              return true;
            }
            if (isDevMode()) {
              console.error('User has not any of required authorities: ', url);
            }
            return false;
          });
        }
        // else if (!authorities || authorities.length === 0) {
        //   return true;
        // }


        // this.stateStorageService.storeUrl(url);
        // this.router.navigate(['accessdenied']).then(() => {
        //   // only show the login dialog, if the user hasn't logged in yet
        //   if (!account) {
        this.router.navigateByUrl('/product-list');
        //   }
        // });
        return false;
      })
    );
  }
}

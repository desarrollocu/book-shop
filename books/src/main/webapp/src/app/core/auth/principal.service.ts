import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {TranslateService} from '@ngx-translate/core';

import {AccountService} from './account.service';
import {StateStorageService} from './state-storage.service';

@Injectable({
  providedIn: 'root'
})
export class Principal {
  private userIdentity: any;
  private authenticated = false;
  private authenticationState = new Subject<any>();
  private fullNameSubject = new Subject<string>();

  constructor(private account: AccountService,
              private translate: TranslateService,
              private stateStorageService: StateStorageService) {
    this.fullNameSubject.asObservable();
  }

  authenticate(identity) {
    this.userIdentity = identity;
    this.authenticated = identity !== null;
    this.authenticationState.next(this.userIdentity);
    this.fullNameSubject.next(this.userIdentity !== null ? this.userIdentity.fullName : null);
  }

  hasAnyAuthority(url: string): Promise<boolean> {
    return Promise.resolve(this.hasAnyAuthorityDirect(url));
  }

  hasAnyAuthorityDirect(url: string): boolean {
    if (!this.authenticated || !this.userIdentity || !this.userIdentity.authorities) {
      return false;
    }

    if (this.userIdentity.authorities.includes(url)) {
      return true;
    }

    return false;
  }

  hasAuthority(authority: string): Promise<boolean> {
    if (!this.authenticated) {
      return Promise.resolve(false);
    }

    return this.identity().then(
      id => {
        return Promise.resolve(id.authorities && id.authorities.includes(authority));
      },
      () => {
        return Promise.resolve(false);
      }
    );
  }

  identity(force?: boolean): Promise<any> {
    if (force === true) {
      this.userIdentity = undefined;
    }

    // check and see if we have retrieved the userIdentity data from the server.
    // if we have, reuse it by immediately resolving
    if (this.userIdentity) {
      return Promise.resolve(this.userIdentity);
    }

    // retrieve the userIdentity data from the server, update the identity object, and then resolve.
    return this.account
      .get()
      .toPromise()
      .then(response => {
        const account = response.body;
        if (account) {
          this.userIdentity = account;
          this.authenticated = true;
          let lang = this.userIdentity.langKey;
          if (lang) {
            this.stateStorageService.storeLanguage(lang);
            this.translate.use(lang);
          }
        } else {
          this.userIdentity = null;
          this.authenticated = false;
          // this.stateStorageService.resetAuth();
          this.stateStorageService.resetLanguage();
        }
        this.authenticationState.next(this.userIdentity);
        this.fullNameSubject.next(this.userIdentity !== null ? this.userIdentity.fullName : null);
        return this.userIdentity;
      })
      .catch(err => {
        this.userIdentity = null;
        this.authenticated = false;
        // this.stateStorageService.storeAuth(this.userIdentity);
        this.authenticationState.next(this.userIdentity);
        return null;
      });
  }

  getFullNameSubject() {
    return this.fullNameSubject;
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  fullName() {
    return this.fullNameSubject;
  }

  isAdmin(): boolean {
    if (this.isIdentityResolved()) {
      if (this.userIdentity !== null)
        return this.userIdentity.isAdmin === "true" ? true : false;
    }

    return false;
  }

  isIdentityResolved(): boolean {
    return this.userIdentity !== undefined;
  }

  getAuthenticationState(): Observable<any> {
    return this.authenticationState.asObservable();
  }

  getImageUrl(): string {
    return this.isIdentityResolved() ? this.userIdentity.image : null;
  }
}

import {Injectable} from '@angular/core';
import {SessionStorageService} from 'ngx-webstorage';

@Injectable({providedIn: 'root'})
export class StateStorageService {
  constructor(private $sessionStorage: SessionStorageService) {
  }

  getPreviousState() {
    return this.$sessionStorage.retrieve('previousState');
  }

  resetPreviousState() {
    this.$sessionStorage.clear('previousState');
  }

  storePreviousState(previousStateName, previousStateParams) {
    const previousState = {name: previousStateName, params: previousStateParams};
    this.$sessionStorage.store('previousState', previousState);
  }

  getDestinationState() {
    return this.$sessionStorage.retrieve('destinationState');
  }

  storeUrl(url: string) {
    this.$sessionStorage.store('previousUrl', url);
  }

  getUrl() {
    return this.$sessionStorage.retrieve('previousUrl');
  }
  //
  // storeAuth(identity: any) {
  //   this.$sessionStorage.store('authenticate', identity);
  // }
  //
  // getAuth() {
  //   return this.$sessionStorage.retrieve('authenticate');
  // }
  //
  // resetAuth() {
  //   this.$sessionStorage.clear('authenticate');
  // }

  storeLanguage(language: string) {
    this.$sessionStorage.store('language', language);
  }

  getLanguage() {
    return this.$sessionStorage.retrieve('language');
  }

  resetLanguage() {
    this.$sessionStorage.clear('language');
  }

  storeDestinationState(destinationState, destinationStateParams, fromState) {
    const destinationInfo = {
      destination: {
        name: destinationState.name,
        data: destinationState.data
      },
      params: destinationStateParams,
      from: {
        name: fromState.name
      }
    };
    this.$sessionStorage.store('destinationState', destinationInfo);
  }
}

import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

// import {SERVER_API_URL} from '../../app.constants';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) {
  }

  get(): Observable<HttpResponse<Account>> {
    return this.http.get<Account>('api/account', {observe: 'response'});
  }

  save(account: any): Observable<HttpResponse<any>> {
    return this.http.post('api/account', account, {observe: 'response'});
  }
}

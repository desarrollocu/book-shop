import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

// import {SERVER_API_URL} from '../../app.constants';

@Injectable({
  providedIn: 'root'
})
export class AuthServerProvider {
  constructor(private http: HttpClient) {
  }

  login(credentials): Observable<any> {
    const data =
      'sys_username=' +
      encodeURIComponent(credentials.username) +
      '&sys_password=' +
      encodeURIComponent(credentials.password) +
      // '&remember-me=' +
      // credentials.rememberMe +
      '&submit=Login';
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

    return this.http.post('api/auth', data, {headers});
  }

  logout(): Observable<any> {
    // logout from the server
    return this.http.post('api/logout', {}, {observe: 'response'}).pipe(
      map((response: HttpResponse<any>) => {
        // to get a new csrf token call the api
        this.http.get('api/account').subscribe(() => {
        }, () => {
        });
        return response;
      })
    );
  }
}

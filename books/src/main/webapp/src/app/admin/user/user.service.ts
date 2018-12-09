import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";
import {createRequestOption} from '../../shared/util/request-util';
import {User} from '../../core/user/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUsers(req?: any): Observable<HttpResponse<User[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<User[]>('api/users', req.userDTO, {params: options, observe: 'response'});
  }

  getUser(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>('api/user', user, {observe: 'response'});
  }

  saveUser(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>('api/saveUser', user, {observe: 'response'});
  }

  deleteUser(userId: string): Observable<HttpResponse<User>> {
    return this.http.delete(`api/deleteUser/${userId}`, {observe: 'response'});
  }

  changeLang(lang: string): Observable<HttpResponse<User>> {
    return this.http.post('api/changeLang', lang, {observe: 'response'});
  }

  changePassword(currentPassword: string, newPassword: string): Observable<HttpResponse<User>> {
    return this.http.post('api/changePassword', {currentPassword, newPassword}, {observe: 'response'});
  }
}

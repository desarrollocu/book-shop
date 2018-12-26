import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";
import {createRequestOption} from '../../shared/util/request-util';

import {Author} from './model/author';
import {Country} from '../user/model/country';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private http: HttpClient) {
  }

  getAuthors(req?: any): Observable<HttpResponse<Author[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Author[]>('api/authors', req.authorDTO, {params: options, observe: 'response'});
  }

  getCountries(): Observable<Country[]> {
    return this.http.post<Country[]>('api/allCountries', {observe: 'response'});
  }

  getAllAuthors(): Observable<Author[]> {
    return this.http.post<Author[]>('api/allAuthors', {observe: 'response'});
  }

  getAuthor(author: Author): Observable<HttpResponse<Author>> {
    return this.http.post<Author>('api/author', author, {observe: 'response'});
  }

  saveAuthor(author: Author): Observable<HttpResponse<Author>> {
    return this.http.post<Author>('api/saveAuthor', author, {observe: 'response'});
  }

  deleteAuthor(authorId: string): Observable<any> {
    return this.http.delete(`api/deleteAuthor/${authorId}`, {observe: 'response'});
  }
}

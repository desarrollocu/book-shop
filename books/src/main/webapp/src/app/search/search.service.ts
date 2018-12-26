import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {createRequestOption} from '../shared/util/request-util';

import {Book} from '../admin/book/model/book';
import {Magazine} from '../admin/magazine/model/magazine';


@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) {
  }

  searchBook(req?: any): Observable<HttpResponse<Book[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Book[]>('api/searchBook', req.searchDTO, {params: options, observe: 'response'});
  }

  searchMagazine(req?: any): Observable<HttpResponse<Magazine[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Magazine[]>('api/searchMagazine', req.searchDTO, {params: options, observe: 'response'});
  }
}

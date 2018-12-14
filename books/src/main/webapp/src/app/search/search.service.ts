import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";
import {Book} from "../admin/book/model/book";
import {createRequestOption} from "../shared/util/request-util";


@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) {
  }

  generalSearch(req?: any): Observable<HttpResponse<Book[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Book[]>('api/generalSearch', req.bookDTO, {params: options, observe: 'response'});
  }
}

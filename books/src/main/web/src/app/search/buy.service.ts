import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {createRequestOption} from "../shared/util/request-util";

import {SearchSale} from './search-sale/model/searchSale';


@Injectable({
  providedIn: 'root'
})
export class BuyService {

  constructor(private http: HttpClient) {
  }

  getSales(req?: any): Observable<HttpResponse<SearchSale[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<SearchSale[]>('api/salesByUser', req.saleDTO, {params: options, observe: 'response'});
  }
}

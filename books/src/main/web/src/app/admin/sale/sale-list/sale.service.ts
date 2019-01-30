import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {createRequestOption} from '../../../shared/util/request-util';

import {SearchSale} from '../../../search/search-sale/model/searchSale';


@Injectable({
  providedIn: 'root'
})
export class SaleService {

  constructor(private http: HttpClient) {
  }

  getSales(req?: any): Observable<HttpResponse<SearchSale[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<SearchSale[]>('api/sales', req.saleDTO, {params: options, observe: 'response'});
  }

  getSale(sale: SearchSale): Observable<HttpResponse<SearchSale>> {
    return this.http.post<SearchSale>('api/sale', sale, {observe: 'response'});
  }

  saveSale(sale: SearchSale): Observable<HttpResponse<SearchSale>> {
    return this.http.post<SearchSale>('api/updateSale', sale, {observe: 'response'});
  }
}

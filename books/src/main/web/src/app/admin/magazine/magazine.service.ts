import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";

import {createRequestOption} from '../../shared/util/request-util';
import {Magazine} from "./model/magazine";

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient) {
  }

  getMagazines(req?: any): Observable<HttpResponse<Magazine[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Magazine[]>('api/magazines', req.magazineDTO, {params: options, observe: 'response'});
  }

  getMagazine(magazine: Magazine): Observable<HttpResponse<Magazine>> {
    return this.http.post<Magazine>('api/magazine', magazine, {observe: 'response'});
  }

  saveMagazine(magazine: Magazine): Observable<HttpResponse<Magazine>> {
    return this.http.post<Magazine>('api/saveMagazine', magazine, {observe: 'response'});
  }

  deleteMagazine(magazineId: string): Observable<any> {
    return this.http.delete(`api/deleteMagazine/${magazineId}`, {observe: 'response'});
  }
}

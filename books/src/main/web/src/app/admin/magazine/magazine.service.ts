import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";

import {createRequestOption} from '../../shared/util/request-util';
import {Magazine} from "./model/magazine";
import {Book} from "../book/model/book";

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

  saveMagazine(magazine: Magazine, file: File): Observable<HttpResponse<Magazine>> {
    const formData: FormData = new FormData();
    if (file === undefined)
      file = null;
    formData.append('file', file);
    formData.append('magazineDTO', new Blob([JSON.stringify(magazine)], {type: 'application/json'}));

    return this.http.post<Book>('api/saveMagazine', formData, {observe: 'response'});
  }

  deleteMagazine(magazineId: string): Observable<any> {
    return this.http.delete(`api/deleteMagazine/${magazineId}`, {observe: 'response'});
  }
}

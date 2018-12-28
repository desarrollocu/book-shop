import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";

import {Classification} from "./model/classification";

@Injectable({
  providedIn: 'root'
})
export class ClassificationService {

  constructor(private http: HttpClient) {
  }

  getAllClassifications(req?: any): Observable<Classification[]> {
    return this.http.post<Classification[]>('api/allClassification', {observe: 'response'});
  }
}

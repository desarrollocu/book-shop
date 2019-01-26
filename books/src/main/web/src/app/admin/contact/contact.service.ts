import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {UiData} from '../../search/model/uiData';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient) {
  }

  getUIData(uiData: UiData): Observable<HttpResponse<UiData>> {
    return this.http.post<UiData>('api/uiData', uiData, {observe: 'response'});
  }

  saveUIData(uiData: UiData): Observable<HttpResponse<UiData>> {
    return this.http.post<UiData>('api/saveUIData', uiData, {observe: 'response'});
  }
}

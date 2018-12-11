import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";
import {createRequestOption} from '../../shared/util/request-util';
import {Editor} from "./model/editor";

@Injectable({
  providedIn: 'root'
})
export class EditorService {

  constructor(private http: HttpClient) {
  }

  getEditors(req?: any): Observable<HttpResponse<Editor[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Editor[]>('api/editors', req.editorDTO, {params: options, observe: 'response'});
  }

  getEditor(editor: Editor): Observable<HttpResponse<Editor>> {
    return this.http.post<Editor>('api/editor', editor, {observe: 'response'});
  }

  saveEditor(editor: Editor): Observable<HttpResponse<Editor>> {
    return this.http.post<Editor>('api/saveEditor', editor, {observe: 'response'});
  }

  deleteEditor(editorId: string): Observable<any> {
    return this.http.delete(`api/deleteEditor/${editorId}`, {observe: 'response'});
  }
}

import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";
import {createRequestOption} from '../../shared/util/request-util';
import {Topic} from "./model/topic";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {
  }

  getTopics(req?: any): Observable<HttpResponse<Topic[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Topic[]>('api/topics', req.topicDTO, {params: options, observe: 'response'});
  }

  getElement(topic: Topic): Observable<HttpResponse<Topic>> {
    return this.http.post<Topic>('api/topic', topic, {observe: 'response'});
  }

  saveTopic(topic: Topic): Observable<HttpResponse<Topic>> {
    return this.http.post<Topic>('api/saveTopic', topic, {observe: 'response'});
  }

  deleteTopic(topicId: string): Observable<any> {
    return this.http.delete(`api/deleteTopic/${topicId}`, {observe: 'response'});
  }
}

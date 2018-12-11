import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";

import {createRequestOption} from '../../shared/util/request-util';
import {Book} from "./model/book";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) {
  }

  getBooks(req?: any): Observable<HttpResponse<Book[]>> {
    const options = createRequestOption(req.pageable);
    return this.http.post<Book[]>('api/books', req.bookDTO, {params: options, observe: 'response'});
  }

  getBook(book: Book): Observable<HttpResponse<Book>> {
    return this.http.post<Book>('api/book', book, {observe: 'response'});
  }

  saveBook(book: Book): Observable<HttpResponse<Book>> {
    return this.http.post<Book>('api/saveBook', book, {observe: 'response'});
  }

  deleteBook(bookId: string): Observable<any> {
    return this.http.delete(`api/deleteBook/${bookId}`, {observe: 'response'});
  }
}

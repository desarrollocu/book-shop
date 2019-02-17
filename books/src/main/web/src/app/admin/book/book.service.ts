import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs";

import {createRequestOption} from '../../shared/util/request-util';
import {Book} from './model/book';
import {BookTrace} from './model/book-trace';

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

  findTrace(book: Book): Observable<HttpResponse<BookTrace>> {
    return this.http.post<BookTrace>('api/bookTrace', book, {observe: 'response'});
  }

  saveBook(book: Book, file: File): Observable<HttpResponse<Book>> {
    const formData: FormData = new FormData();
    if (file === undefined)
      file = null;
    formData.append('file', file);
    formData.append('bookDTO', new Blob([JSON.stringify(book)], {type: 'application/json'}));

    return this.http.post<Book>('api/saveBook', formData, {observe: 'response'});
  }

  deleteBook(bookId: string): Observable<any> {
    return this.http.delete(`api/deleteBook/${bookId}`, {observe: 'response'});
  }
}

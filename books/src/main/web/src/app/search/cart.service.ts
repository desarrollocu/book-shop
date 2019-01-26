import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

import {Payment} from './cart/model/payment';
import {Cart} from './cart/model/cart';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  cartSubject: Subject<number>;
  cartCant: number = 0;

  constructor(private http: HttpClient) {
    this.cartSubject = new Subject<number>();
    this.cartSubject.asObservable();
    this.getCarSubject().next(this.cartCant);
  }

  getProducts(req?: any[]): Observable<HttpResponse<Cart>> {
    return this.http.post<Cart>('api/searchToShop', req, {observe: 'response'});
  }

  createPayment(trans: any): Promise<Payment> {
    return this.http.post<Payment>('api/createPayment', trans).toPromise();
  }

  addToCart(item: any): Observable<HttpResponse<any>> {
    return this.http.post<any>('api/addToCart', item, {observe: 'response'});
  }

  removeFormCart(item: any): Observable<HttpResponse<any>> {
    return this.http.post<any>('api/removeFormCart', item, {observe: 'response'});
  }

  elementsInCart(): Observable<HttpResponse<any>> {
    return this.http.post<any>('api/elementsInCart', {observe: 'response'});
  }

  executePayment(payment: any): Promise<HttpResponse<Object>> {
    return this.http.post('api/executePayment', payment, {observe: 'response'})
      .toPromise();
  }

  setCartCant(cant) {
    this.cartCant = cant;
  }

  getCarSubject() {
    return this.cartSubject;
  }
}

import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

import {Payment} from './cart/model/payment';
import {Cart} from './cart/model/cart';
import {ShippingInfo} from "./cart/model/shipping-info";


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

  getProducts(): Observable<Cart> {
    return this.http.post<Cart>('api/searchToShop', {observe: 'response'});
  }

  createPayment(trans: any): Promise<Payment> {
    return this.http.post<Payment>('api/createPayment', trans).toPromise();
  }

  removeSession(item?: any): Observable<HttpResponse<any>> {
    return this.http.post<any>('api/removeSession', item, {observe: 'response'});
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

  addShippingInfo(item: any): Observable<HttpResponse<ShippingInfo>> {
    return this.http.post<ShippingInfo>('api/addShippingInfo', item, {observe: 'response'});
  }

  getShippingInfo(): Observable<ShippingInfo> {
    return this.http.post<ShippingInfo>('api/getShippingInfo', {observe: 'response'});
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

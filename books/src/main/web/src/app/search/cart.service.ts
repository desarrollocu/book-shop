import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

import {Doc} from './cart/model/doc';
import {Product} from './cart/model/product';
import {Sale} from "./cart/model/sale";
import {AlertService} from "../shared/alert/alert.service";
import {Payment} from "./cart/model/payment";


@Injectable({
  providedIn: 'root'
})
export class CartService {
  productList: Product[];
  cartSubject: Subject<number>;
  paymentId: string;

  constructor(private http: HttpClient, private alertService: AlertService) {
    this.productList = [];
    this.cartSubject = new Subject<number>();
    this.cartSubject.asObservable();
  }

  getProducts(req?: any[]): Observable<HttpResponse<Doc[]>> {
    return this.http.post<Doc[]>('api/searchToShop', req, {observe: 'response'});
  }

  addToCar(product?: any, book?: boolean) {
    let val = this.existProduct(product.id);
    if (val !== -1) {
      this.alertService.info('info.inCart', null, null);
    }
    else {
      let prod = new Product();
      prod.id = product.id;
      prod.cant = 1;
      prod.book = book;
      this.productList.push(prod);
      let cant = 0;
      for (let i in this.productList) {
        cant += this.productList[i].cant;
      }
      this.getCarSubject().next(cant);

      this.alertService.info('shopping.success', null, null);
    }
  }

  getCarSubject() {
    return this.cartSubject;
  }

  removeFromCar(prod?) {
    let i = -1;
    for (let j = 0; j < this.productList.length; j++) {
      if (this.productList[j].id === prod.id) {
        i = j;
        break;
      }
    }
    if (i > -1) {
      this.productList.splice(i, 1);
    }

    let cant = 0;
    for (let i in this.productList) {
      cant += this.productList[i].cant;
    }
    this.getCarSubject().next(cant);
    return this.productList;
  }

  getProductList() {
    return this.productList;
  }

  updateCant(element, action) {
    if (action === '+') {
      if (element.cant < element.realCant) {
        element.cant++;
        for (let i = 0; i < this.productList.length; i++) {
          if (element.id === this.productList[i].id) {
            this.productList[i].cant = element.cant;
            break;
          }
        }

        let cant = 0;
        for (let i in this.productList) {
          cant += this.productList[i].cant;
        }
        this.getCarSubject().next(cant);
      }
      else
        this.alertService.info('info.maxProduct', null, null);
    }
    else {
      if (element.cant > 1) {
        element.cant--;
        for (let i = 0; i < this.productList.length; i++) {
          if (element.id === this.productList[i].id) {
            this.productList[i].cant = element.cant;
            break;
          }
        }

        let cant = 0;
        for (let i in this.productList) {
          cant += this.productList[i].cant;
        }
        this.getCarSubject().next(cant);
      }
    }
  }

  cleanCart() {
    this.productList = [];
    this.getCarSubject().next(0);
  }

  private existProduct(id?: string) {
    for (let i in this.productList) {
      if (this.productList[i].id === id)
        return i;
    }

    return -1;
  }

  saveSale(sale: Sale): Observable<HttpResponse<Sale>> {
    return this.http.post<Sale>('api/saveSale', sale, {observe: 'response'});
  }

  createPayment(trans: any): Observable<HttpResponse<Payment>> {
    return this.http.post<Payment>('api/createPayment', trans, {observe: 'response'});
  }

  executePayment(payment: any): Promise<HttpResponse<Object>> {
    return this.http.post('api/executePayment', payment, {observe: 'response'})
      .toPromise();
  }

  getPaymentId() {
    return this.paymentId;
  }

  setPaymentId(temp) {
    this.paymentId = temp;
  }
}

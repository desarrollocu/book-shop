import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

import {Doc} from './cart/model/doc';
import {Product} from './cart/model/product';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  productList: Product[];
  cartSubject: Subject<number>;

  constructor(private http: HttpClient) {
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
      this.productList[val].cant += 1;
    }
    else {
      let prod = new Product();
      prod.id = product.id;
      prod.cant = 1;
      prod.book = book;
      this.productList.push(prod);
    }

    let cant = 0;
    for (let i in this.productList) {
      cant += this.productList[i].cant;
    }
    this.getCarSubject().next(cant);
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

  updateCant(element) {
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

  private existProduct(id?: string) {
    for (let i in this.productList) {
      if (this.productList[i].id === id)
        return i;
    }

    return -1;
  }
}

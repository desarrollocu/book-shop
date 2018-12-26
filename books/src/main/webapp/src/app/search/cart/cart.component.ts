import {Component, OnInit} from '@angular/core';

import {CartService} from '../cart.service';
import {AlertService} from '../../shared/alert/alert.service';

import {Doc} from './model/doc';


@Component({
  selector: 'app-shopping-car',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  elementList: Doc[];
  total: number;
  load: boolean;

  constructor(private cartService: CartService,
              private alertService: AlertService) {
    this.load = false;
    this.elementList = [];
    this.total = 0;
  }

  ngOnInit() {
    this.getProducts(this.cartService.getProductList());
  }

  getProducts(val?: any) {
    this.cartService.getProducts(val)
      .subscribe(response => this.onSuccess(response),
        response => this.onError(response));
  }

  private onSuccess(res) {
    this.elementList = [];
    this.elementList = res.body;
    this.sumTotal(null);
    this.load = true;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  sumTotal(element) {
    this.total = 0;
    for (let i in this.elementList) {
      this.total += (this.elementList[i].cant * this.elementList[i].salePrice);
    }
    if (element !== null)
      this.cartService.updateCant(element);
  }

  removeProduct(prod) {
    let i = -1;
    for (let j = 0; j < this.elementList.length; j++) {
      if (this.elementList[j].id === prod.id) {
        i = j;
        break;
      }
    }
    if (i > -1) {
      this.elementList.splice(i, 1);
      this.cartService.removeFromCar(prod);
      this.sumTotal(null);
    }
  }

  trackIdentity(index, item: Element) {
    return item.id;
  }
}

import {AfterViewChecked, Component, OnInit} from '@angular/core';

import {CartService} from '../cart.service';
import {AlertService} from '../../shared/alert/alert.service';

import {Doc} from './model/doc';

declare let paypal: any;

@Component({
  selector: 'app-shopping-car',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit, AfterViewChecked {
  elementList: Doc[];
  total: number;
  load: boolean;

  addScript: boolean = false;
  paypalLoad: boolean = true;

  finalAmount: number = 1;

  paypalConfig = {
    env: 'sandbox',
    client: {
      sandbox: 'fbmkddpqf79rzxzh$23544910adadd67f0b3bed0d508e3d2a',
      // production: '<your-production-key here>'
    },
    commit: true,
    payment: (data, actions) => {
      return actions.payment.create({
        payment: {
          transactions: [
            {amount: {total: this.finalAmount, currency: 'USD'}}
          ]
        }
      });
    },
    onAuthorize: (data, actions) => {
      return actions.payment.execute().then((payment) => {
        //Do something when payment is successful.
      })
    }
  };

  constructor(private cartService: CartService,
              private alertService: AlertService) {
    this.load = false;
    this.elementList = [];
    this.total = 0;
  }

  ngOnInit() {
    this.getProducts(this.cartService.getProductList());
  }

  ngAfterViewChecked(): void {
    if (!this.addScript) {
      this.addPaypalScript().then(() => {
        paypal.Button.render(this.paypalConfig, '#paypal-checkout-btn');
        this.paypalLoad = false;
      })
    }
  }

  addPaypalScript() {
    this.addScript = true;
    return new Promise((resolve, reject) => {
      let scripttagElement = document.createElement('script');
      scripttagElement.src = 'https://www.paypalobjects.com/api/checkout.js';
      scripttagElement.onload = resolve;
      document.body.appendChild(scripttagElement);
    })
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

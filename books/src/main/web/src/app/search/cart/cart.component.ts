import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {CartService} from '../cart.service';
import {AlertService} from '../../shared/alert/alert.service';

import {Doc} from './model/doc';
import {PayPalConfig} from '../paypal/model/paypal-models';
import {PayPalIntegrationType} from '../paypal/model/paypal-integration';
import {PayPalEnvironment} from '../paypal/model/paypal-environment';
import {Sale} from "./model/sale";
import {Detail} from "./model/detail";


@Component({
  selector: 'app-shopping-car',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  total: number;
  load: boolean;
  elementList: Doc[];
  payPalItemList: any[];
  payPalConfig?: PayPalConfig;
  paymentId: string;
  reLoadPayPal: boolean = false;

  constructor(private cartService: CartService,
              private router: Router,
              private alertService: AlertService) {
    this.load = false;
    this.elementList = [];
    this.total = 0;
  }

  ngOnInit() {
    this.getProducts(this.cartService.getProductList());
    this.initConfig();
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

  private createPaymentId() {
    this.paymentId = null;
    this.reLoadPayPal = false;

    if (this.elementList.length > 0) {
      this.payPalItemList = [];
      for (let i = 0; i < this.elementList.length; i++) {
        this.payPalItemList.push({
          name: this.elementList[i].title,
          quantity: this.elementList[i].cant,
          price: this.elementList[i].salePrice,
          // tax: '0.00',
          // sku: '1',
          currency: 'USD'
        });
      }

      let transaction = {
        amount: {
          total: this.total,
          currency: 'USD',
          details: {
            subtotal: this.total,
            tax: 0.00,
            shipping: 0.00,
            handling_fee: 0.00,
            shipping_discount: 0.00,
            insurance: 0.00
          }
        },
        item_list: {
          items: this.payPalItemList,
          // shipping_address: {
          //   recipient_name: 'Cart Cart',
          //   line1: '6554th Floor',
          //   line2: 'Unit #34',
          //   city: 'San Jose',
          //   country_code: 'US',
          //   postal_code: '95131',
          //   phone: '011862212345678',
          //   state: 'CA'
          // }
        }
      };

      this.cartService.createPayment(transaction)
        .subscribe(response => {
            this.paymentId = response.body.paymentID;
            this.cartService.setPaymentId(this.paymentId);
          },
          response => {
            this.reLoadPayPal = true;
            this.alertService.error('error.payPalConnection', null, null);
          });
    }
  }

  reloadPayPalFunction() {
    this.createPaymentId();
  }

  private initConfig(): void {
    this.payPalConfig = new PayPalConfig(PayPalIntegrationType.ServerSideREST, PayPalEnvironment.Sandbox, {
      commit: true,
      button: {
        label: 'paypal',
      },
      onAuthorize: (data, actions) => {
        let sale = new Sale();
        let details = [];
        for (let j in this.elementList) {
          let detail = new Detail();
          detail.title = this.elementList[j].title;
          detail.id = this.elementList[j].id;
          detail.salePrice = this.elementList[j].salePrice;
          detail.cant = this.elementList[j].cant;
          detail.city = this.elementList[j].city;
          detail.editor = this.elementList[j].editor;
          detail.topic = this.elementList[j].topic;
          detail.isbn = this.elementList[j].isbn;
          detail.mount = detail.cant * detail.salePrice;
          details.push(detail);
        }
        sale.detailList = details;
        sale.total = this.total;

        return this.cartService.executePayment({
          paymentID: data.paymentID,
          playerID: data.payerID,
          saleDTO: sale
        }).then((info) => {
          console.log('onAuthorize');
          this.alertService.success('success.sale', null, null);
          this.cartService.cleanCart();
          this.router.navigateByUrl('search-general');
        })
      },
      onError: (err) => {
        this.alertService.error('error.payPal', null, null);
      }
    });
  }

  sumTotal(element) {
    this.total = 0;
    if (element === null) {
      for (let i in this.elementList) {
        this.total += (this.elementList[i].cant * this.elementList[i].salePrice);
      }
    }
    else {
      this.cartService.updateCant(element, '+');
      for (let i in this.elementList) {
        this.total += (this.elementList[i].cant * this.elementList[i].salePrice);
      }
    }

    this.createPaymentId();
  }

  deleteOne(element) {
    this.total = 0;
    this.cartService.updateCant(element, '-');
    for (let i in this.elementList) {
      this.total += (this.elementList[i].cant * this.elementList[i].salePrice);
    }

    this.createPaymentId();
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
    else
      this.createPaymentId();
  }

  saveSale(sale) {
    this.cartService.saveSale(sale)
      .subscribe(response => this.onSuccessSale(response),
        response => this.onErrorSale(response));
  }

  private onSuccessSale(res) {
  }

  private onErrorSale(response) {
    let error = response.error;
    let fields = error.fields;
  }

  trackIdentity(index, item: Element) {
    return item.id;
  }
}

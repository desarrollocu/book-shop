import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {CartService} from '../cart.service';
import {AlertService} from '../../shared/alert/alert.service';

import {Doc} from './model/doc';
import {PayPalConfig} from '../paypal/model/paypal-models';
import {PayPalIntegrationType} from '../paypal/model/paypal-integration';
import {PayPalEnvironment} from '../paypal/model/paypal-environment';
import {Sale} from './model/sale';
import {UserDetail} from "./model/user-detail";
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

  private initConfig(): void {
    this.payPalConfig = new PayPalConfig(PayPalIntegrationType.ClientSideREST, PayPalEnvironment.Sandbox, {
      commit: true,
      client: {
        sandbox: 'Ae3kB7nSbmR3Ty9NKg6bIrHHU64mt0hZutwVG5Wz80tpQsn2HTblJeoKA2nJQPOUXjGYUA1nxidsCUGu',
      },
      button: {
        label: 'paypal',
      },
      onPaymentComplete: (data, actions, payment) => {
        let sale = new Sale();
        let details = [];
        for (let j in this.elementList) {
          let detail = new Detail();
          detail.title = this.elementList[j].title;
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

        let userDetail = new UserDetail();
        userDetail.firstName = payment.payer.payer_info.first_name;
        userDetail.lastName = payment.payer.payer_info.last_name;
        userDetail.email = payment.payer.payer_info.email;
        userDetail.recipientName = payment.payer.payer_info.shipping_address.recipient_name;
        userDetail.address = payment.payer.payer_info.shipping_address.city +
          payment.payer.payer_info.shipping_address.country_code +
          payment.payer.payer_info.shipping_address.line1 +
          payment.payer.payer_info.shipping_address.line2;

        sale.userDetail = userDetail;
        sale.total = this.total;

        this.saveSale(sale);
        this.alertService.success('success.sale', null, null);
      },
      onCancel: (data, actions) => {
        console.log('OnCancel');
      },
      onError: (err) => {
        this.alertService.error('error.paypal', null, null);
      },
      transactions: []
    });
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
    this.updatePaypalAmount();
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

    this.updatePaypalAmount();
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

    this.updatePaypalAmount();
  }

  updatePaypalAmount() {
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

    this.payPalConfig.transactions = [];
    this.payPalConfig.transactions.push({
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
    });
  }

  saveSale(sale) {
    this.cartService.saveSale(sale)
      .subscribe(response => this.onSuccessSale(response),
        response => this.onErrorSale(response));
  }

  private onSuccessSale(res) {
    this.cartService.cleanCart();
    this.router.navigateByUrl('search-general');
  }

  private onErrorSale(response) {
    let error = response.error;
    let fields = error.fields;
  }

  trackIdentity(index, item: Element) {
    return item.id;
  }
}

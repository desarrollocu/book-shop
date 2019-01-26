import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalConfig} from '@ng-bootstrap/ng-bootstrap';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {CartService} from '../cart.service';
import {AlertService} from '../../shared/alert/alert.service';
import {AuthorService} from '../../admin/author/author.service';

import {Doc} from './model/doc';
import {PayPalConfig} from '../paypal/model/paypal-models';
import {PayPalIntegrationType} from '../paypal/model/paypal-integration';
import {PayPalEnvironment} from '../paypal/model/paypal-environment';
import {Sale} from './model/sale';
import {Detail} from './model/detail';
import {ShippingInfo} from './model/shipping-info';
import {Country} from '../../admin/user/model/country';


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
  loadPayment: boolean = false;
  shippingInfo: ShippingInfo;
  countryList: Country[];
  currentLang: string;
  shippingCost: number = 0;
  totalKgs: number;

  constructor(private cartService: CartService,
              private router: Router,
              private modalService: NgbModal,
              private authorService: AuthorService,
              private translateService: TranslateService,
              config: NgbModalConfig,
              private alertService: AlertService) {
    this.load = false;
    this.elementList = [];
    this.total = 0;
    this.totalKgs = 0;
    this.shippingInfo = this.cartService.getShippingInfo();
    this.currentLang = this.translateService.currentLang;
    config.backdrop = 'static';
    config.keyboard = false;
  }

  ngOnInit() {
    this.getProducts({
      productDTOList: this.cartService.getProductList(),
      countryDTO: this.shippingInfo.country
    }, this.shippingInfo.country !== undefined, false);
    this.initConfig();
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  getProducts(val?: any, toPayment?: boolean, cancel?: boolean) {
    this.cartService.getProducts(val)
      .subscribe(response => this.onSuccess(response.body, toPayment, cancel),
        response => this.onError(response));
  }

  private onSuccess(res, toPayment, cancel) {
    this.elementList = [];
    this.elementList = res.shopDTOList;
    this.total = res.amount;
    this.totalKgs = res.totalKgs;
    this.shippingCost = res.shippingCost;
    let cartCant = res.cant;
    this.cartService.setCartCant(cartCant);
    this.cartService.getCarSubject().next(cartCant);
    this.load = true;

    if (toPayment) {
      this.createPaymentId(cancel);
    }
  }

  addElement(element) {
    if (element.realCant >= element.cant + 1) {
      this.cartService.toCar(element, element.isBook, false, true);
      this.getProducts({
        productDTOList: this.cartService.getProductList(),
        countryDTO: this.shippingInfo.country
      }, this.shippingInfo.country !== undefined,false);
    }
  }

  deleteOne(element) {
    if (element.cant > 1) {
      this.cartService.toCar(element, element.isBook, false, false);
      this.getProducts({
        productDTOList: this.cartService.getProductList(),
        countryDTO: this.shippingInfo.country
      }, this.shippingInfo.country !== undefined, false);
    }
  }

  removeProduct(prod) {
    this.cartService.removeFromCar(prod);
    this.getProducts({
      productDTOList: this.cartService.getProductList(),
      countryDTO: this.shippingInfo.country
    }, this.shippingInfo.country !== undefined, false);
  }

  shippingDialog(checkoutModal) {
    this.modalService.open(checkoutModal);
  }

  saveShippingInfo() {
    this.cartService.setShippingInfo(this.shippingInfo);

    this.getProducts({
      productDTOList: this.cartService.getProductList(),
      countryDTO: this.shippingInfo.country
    }, this.shippingInfo.country !== undefined, true);
  }

  private createPaymentId(cancel) {
    this.loadPayment = true;
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
            subtotal: this.total - this.shippingCost,
            tax: 0.00,
            shipping: this.shippingCost,
            handling_fee: 0.00,
            shipping_discount: 0.00,
            insurance: 0.00
          }
        },
        item_list: {
          items: this.payPalItemList,
          shipping_address: {
            recipient_name: this.shippingInfo.fullName,
            line1: this.shippingInfo.address,
            line2: '',
            city: this.shippingInfo.city,
            country_code: this.shippingInfo.country.code,
            postal_code: this.shippingInfo.postalCode,
            phone: this.shippingInfo.phone,
            state: this.shippingInfo.state
          }
        }
      };

      this.cartService.createPayment(transaction)
        .subscribe(response => {
            this.paymentId = response.body.paymentID;
            this.cartService.setPaymentId(this.paymentId);
            this.loadPayment = false;
            if (cancel)
              this.cancelMy();
          },
          response => {
            this.reLoadPayPal = true;
            let error = response.error;
            let fields = error.fields;
            this.loadPayment = false;
            this.alertService.error(error.error, fields, null);
          });
    }
  }

  reloadPayPalFunction() {
    this.createPaymentId(null);
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
          this.alertService.success('success.sale', null, null);
          this.cartService.cleanCart();
          this.router.navigateByUrl('search-general');
        })
      },
      onError: (err) => {
        let error = err.error;
        let fields = error.fields;
        this.alertService.error(error.error, fields, null);
      }
    });
  }

  getCountries() {
    this.authorService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
  }

  private countryLanguage() {
    let temp = this.countryList;
    this.countryList = [];
    if (temp) {
      for (let i in temp) {
        this.countryList = [...this.countryList, temp[i]];
      }
    }
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  trackIdentity(index, item: Element) {
    return item.id;
  }

  cancelMy() {
    this.cartService.setShippingInfo(this.shippingInfo);
    this.modalService.dismissAll('cancel')
  }
}

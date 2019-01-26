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
  shippingInfo: ShippingInfo;
  countryList: Country[];
  currentLang: string;
  shippingCost: number = 0;
  totalKgs: number;
  shippingFlag: boolean = false;
  enableState: boolean = true;
  errorFlag: boolean = false;

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
    this.currentLang = this.translateService.currentLang;
    config.backdrop = 'static';
    config.keyboard = false;
  }

  ngOnInit() {
    this.getProducts({
      countryDTO: this.shippingInfo ? this.shippingInfo.country : null
    }, false);
    this.initConfig();
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  getProducts(val?: any, flag?: boolean) {
    this.cartService.getProducts(val)
      .subscribe(response => this.onSuccess(response.body, flag),
        response => this.onError(response));
  }

  private onSuccess(res, flag) {
    this.elementList = [];
    this.elementList = res.shopDTOList;
    this.total = res.amount;
    this.totalKgs = res.totalKgs;
    this.shippingCost = res.shippingCost;
    let cartCant = res.cant;
    this.cartService.setCartCant(cartCant);
    this.cartService.getCarSubject().next(cartCant);
    this.load = true;

    if (flag) {
      this.shippingFlag = true;
      this.cancelMy();
    }
  }

  addElement(element) {
    if (element.realCant >= element.cant + 1) {
      this.cartService.addToCart({
        id: element.id,
        cant: element.cant + 1,
        book: element.book
      }).subscribe(response => this.onCarSuccess(response.body),
        response => this.onError(response));
    }
    else
      this.alertService.info('info.maxProduct', null, null);
  }

  deleteOne(element) {
    if (element.cant > 1) {
      this.cartService.addToCart({
        id: element.id,
        cant: element.cant - 1,
        book: element.book
      }).subscribe(response => this.onCarSuccess(response.body),
        response => this.onError(response));
    }
  }

  onCarSuccess(resp) {
    this.getProducts({
      countryDTO: this.shippingInfo ? this.shippingInfo.country : null
    }, false);

    this.cartService.getCarSubject().next(resp.cant);
  }

  removeProduct(prod) {
    this.cartService.removeFormCart({
      id: prod.id,
      cant: 1,
      book: false
    })
      .subscribe(response => this.onCarSuccess(response.body),
        response => this.onError(response));
  }

  shippingDialog(checkoutModal) {
    if (this.shippingInfo === undefined)
      this.shippingInfo = new ShippingInfo();

    this.modalService.open(checkoutModal);
  }

  saveShippingInfo() {
    this.getProducts({
      countryDTO: this.shippingInfo ? this.shippingInfo.country : null
    }, true);
  }

  cancelShippingInfo() {
    if (this.shippingFlag === false)
      this.shippingInfo = undefined;
    this.cancelMy();
  }

  private initConfig(): void {
    this.payPalConfig = new PayPalConfig(PayPalIntegrationType.ServerSideREST, PayPalEnvironment.Sandbox, {
      commit: true,
      button: {
        label: 'paypal',
      },
      payment: () => {
        if (this.shippingInfo) {
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

          return this.cartService.createPayment(transaction);
        }
        else {
          this.errorFlag = true;
          this.alertService.info('info.missingShippingInfo', null, null);
        }
      },
      onAuthorize: (data, actions) => {
        return this.cartService.executePayment({
          paymentID: data.paymentID,
          playerID: data.payerID
        }).then((info) => {
          this.cartService.getCarSubject().next(0);
          this.alertService.success('success.sale', null, null);
          this.router.navigateByUrl('search-general');
        })
      },
      onError:
        (err) => {
          if (!this.errorFlag) {
            if (err) {
              if (err.error) {
                let error = err.error;
                let fields = error.fields ? error.fields : null;
                this.alertService.error(error.error, fields, null);
              }
              this.alertService.error('error.E67', null, null);
            }
            else
              this.alertService.error('error.E67', null, null);
          }
          else
            this.errorFlag = false;
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

  stateEnable(val) {
    if (val != undefined) {
      val.spanishName === 'Estados Unidos de América' ? this.enableState = false : this.enableState = true;
    }
    else
      this.enableState = true;
  }

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }
}

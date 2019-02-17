import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
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
              private alertService: AlertService) {
    this.load = false;
    this.elementList = [];
    this.total = 0;
    this.totalKgs = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getShippingInfo(null);
    this.initConfig();
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
  }

  getProducts(flag?: boolean) {
    this.cartService.getProducts()
      .subscribe(response => this.onSuccess(response, flag),
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
    this.getProducts(false);
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
    this.getShippingInfo(checkoutModal);
  }

  getShippingInfo(checkoutModal) {
    this.cartService.getShippingInfo()
      .subscribe(response => this.onShippingInfoSuccess(response, checkoutModal),
        response => this.onError(response));
  }

  onShippingInfoSuccess(resp, checkoutModal) {
    this.shippingInfo = resp;
    if ((this.shippingInfo === undefined || this.shippingInfo === null) && checkoutModal != null)
      this.shippingInfo = new ShippingInfo();

    if (this.shippingInfo != null)
      this.stateEnable(this.shippingInfo.country);

    this.getProducts(false);
    if (checkoutModal != null) {
      let config: NgbModalOptions = {
        backdrop: 'static',
        keyboard: false
      };
      this.modalService.open(checkoutModal, config);
    }
  }

  saveShippingInfo() {
    this.cartService.addShippingInfo(this.shippingInfo)
      .subscribe(response => this.onSaveShippingInfoSuccess(response.body),
        response => this.onError(response));
  }

  onSaveShippingInfoSuccess(resp) {
    this.shippingInfo = resp;
    this.getProducts(true);
  }

  cancelShippingInfo() {
    this.cancelMy();
  }

  private initConfig(): void {
    this.payPalConfig = new PayPalConfig(PayPalIntegrationType.ServerSideREST, PayPalEnvironment.Sandbox, {
      commit: true,
      button: {
        label: 'paypal',
      },
      payment: () => {
        if (this.shippingInfo && this.shippingInfo != null) {
          if (this.shippingInfo.line1 && this.shippingInfo.line1 != null
            && this.shippingInfo.city && this.shippingInfo.city !== null
            && this.shippingInfo.country && this.shippingInfo.country !== null
            && this.shippingInfo.postalCode && this.shippingInfo.postalCode != null
            && this.shippingInfo.phone && this.shippingInfo.phone != null
            && this.shippingInfo.fullName && this.shippingInfo.fullName != null
            && this.shippingInfo.email && this.shippingInfo.email !== null) {
            return this.cartService.createPayment({});
          }
          else {
            this.errorFlag = true;
            this.alertService.info('info.missingShippingInfo', null, null);
          }
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
          this.getProducts(false);
        }).catch(reason => {
          if (this.payPalConfig.onError) {
            this.payPalConfig.onError(reason);
          }
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
              else
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

  cancelMy() {
    this.modalService.dismissAll('cancel')
  }

  stateEnable(val) {
    if (val != undefined) {
      if (val.code === 'US' || val.code === 'MX' || val.code === 'AR'
        || val.code === 'AU' || val.code === 'CA' || val.code === 'C2' || val.code === 'HK' || val.code === 'ID'
        || val.code === 'JP' || val.code === 'RU' || val.code === 'CH' || val.code === 'TH') {
        this.enableState = false
      } else {
        this.enableState = true;
        this.shippingInfo.state = null;
      }
    }
    else {
      this.enableState = true;
      this.shippingInfo.state = null;
    }
  }
}

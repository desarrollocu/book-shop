import {Component, OnInit} from '@angular/core';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {Principal} from '../../core/auth/principal.service';
import {CartService} from '../../search/cart.service';

import {UiData} from '../../search/model/uiData';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  images: string[];
  cartCant: number;
  uiData: UiData;
  currentLang: string;

  constructor(private principal: Principal,
              private translateService: TranslateService,
              private cartService: CartService) {
    this.images = ["assets/images/image.gif", "assets/images/login.gif", "assets/images/image.gif"];
    this.currentLang = this.translateService.currentLang;
    this.uiData = new UiData();
  }

  ngOnInit() {
    this.getUIData();
    this.getCant();
    this.cartService.getCarSubject().subscribe((value => {
      this.cartCant = value;
    }));
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
    });
  }

  getUIData() {
    this.principal.getUIData({})
      .subscribe(response => this.onUIDataSuccess(response),
        response => this.onUIDataError(response));
  }

  getCant() {
    this.cartService.elementsInCart()
      .subscribe(response => this.onCarSuccess(response),
      response => this.onError(response));
  }

  onCarSuccess(resp) {
    this.cartCant = resp.cant;
  }

  onError(resp) {
    this.cartCant = 0
  }

  private onUIDataError(response) {
    let error = response.error;
  }

  private onUIDataSuccess(res) {
    this.uiData = res.body;
  }

  isAdmin() {
    return this.principal.isAdmin();
  }
}

import {Component, OnInit} from '@angular/core';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';

import {Principal} from '../../core/auth/principal.service';

import {UiData} from '../../search/model/uiData';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  uiData: UiData;
  currentLang: string;

  constructor(private principal: Principal,
              private translateService: TranslateService) {
    this.currentLang = this.translateService.currentLang;
    this.uiData = new UiData();
  }

  ngOnInit() {
    this.getUIData();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
    });
  }

  getUIData() {
    this.principal.getUIData({})
      .subscribe(response => this.onUIDataSuccess(response),
        response => this.onUIDataError(response));
  }

  private onUIDataError(response) {
    let error = response.error;
  }

  private onUIDataSuccess(res) {
    this.uiData = res.body;
  }
}

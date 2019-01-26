import {Component, OnInit} from '@angular/core';
import {LangChangeEvent, TranslateService} from '@ngx-translate/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {ContactService} from '../contact.service';
import {AlertService} from '../../../shared/alert/alert.service';

import {UiData} from '../../../search/model/uiData';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.scss']
})
export class ContactListComponent implements OnInit {
  uiData: UiData;
  currentLang: string;
  load: boolean = false;

  constructor(private translateService: TranslateService,
              private modalService: NgbModal,
              private alertService: AlertService,
              private contactService: ContactService) {
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
    this.contactService.getUIData({})
      .subscribe(response => this.onUIDataSuccess(response),
        response => this.onUIDataError(response));
  }

  private onUIDataError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  private onUIDataSuccess(res) {
    this.uiData = res.body;
  }

  private onUISaveDataSuccess(res) {
    this.uiData = res.body;
    this.load = false;
    this.alertService.success('success.edited', null, null);
  }

  saveUIData() {
    this.load = true;
    this.contactService.saveUIData(this.uiData)
      .subscribe(response => this.onUISaveDataSuccess(response),
        response => this.onUIDataError(response));
  }
}

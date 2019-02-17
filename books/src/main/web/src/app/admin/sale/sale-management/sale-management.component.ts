import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {SaleService} from '../sale-list/sale.service';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-sale-management',
  templateUrl: './sale-management.component.html',
  styleUrls: ['./sale-management.component.scss']
})
export class SaleManagementComponent implements OnInit {
  @Input() sale;
  stateList: string[];
  load: boolean = false;

  constructor(private alertService: AlertDialogService,
              private saleService: SaleService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.stateList = ['inProcess', 'requestedPaid', 'inTransit', 'received'];
  }

  ngOnInit() {
    this.findSale();
  }

  findSale() {
    this.saleService.getSale(this.sale)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveSale() {
    this.load = true;
    this.saleService.saveSale(this.sale)
      .subscribe(response => this.onSuccess(response, this.sale),
        response => this.onError(response));
  }

  private onSuccess(response, sale) {
    let msg = 'success.edited';
    this.sale = response.body;
    this.load = false;
    this.alertService.success(msg, null, null);
    this.activeModal.close();
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.sale = result.body;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.load = false;
    this.alertService.error(error.error, fields, null);
  }

  cancel() {
    this.activeModal.dismiss();
  }
}

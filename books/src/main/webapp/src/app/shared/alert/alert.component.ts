import {Component, OnDestroy, OnInit} from '@angular/core';
import {AlertService} from './alert.service';

@Component({
  selector: 'app-alert',
  template: `
        <div class="alerts" role="alert">
            <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
                <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
                    <pre [innerHTML]="alert.msg"></pre>
                </ngb-alert>
            </div>
        </div>`
})
export class AlertComponent implements OnInit, OnDestroy {
  alerts: any[];

  constructor(private alertService: AlertService) {
  }

  ngOnInit() {
    this.alerts = this.alertService.get();
  }

  setClasses(alert) {
    return {
      toast: !!alert.toast,
      [alert.position]: true
    };
  }

  ngOnDestroy() {
    this.alerts = [];
  }
}

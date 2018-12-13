import {Component, OnDestroy, OnInit} from '@angular/core';
import {AlertDialogService} from './alert.dialog.service';

@Component({
  selector: 'app-alert-dialog',
  template: `
    <div class="alerts" role="alert">
      <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
        <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
          <pre [innerHTML]="alert.msg"></pre>
        </ngb-alert>
      </div>
    </div>`
})
export class AlertDialogComponent implements OnInit, OnDestroy {
  alerts: any[];

  constructor(private alertService: AlertDialogService) {
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

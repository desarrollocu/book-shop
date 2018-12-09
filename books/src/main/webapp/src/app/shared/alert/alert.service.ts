import {Injectable, Sanitizer, SecurityContext} from "@angular/core";
import {TranslateService} from "@ngx-translate/core";

export declare type AlertType = 'success' | 'danger' | 'warning' | 'info';

export interface Alert {
  id?: number;
  type: AlertType;
  msg: string;
  params?: any;
  timeout?: number;
  toast?: boolean;
  position?: string;
  scoped?: boolean;
  close?: (alerts: Alert[]) => void;
}

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private alertId;
  private alerts;
  private timeout;
  private toast;
  private i18nEnabled;

  constructor(private sanitizer: Sanitizer, private translateService: TranslateService) {
    this.alertId = 0; // unique id for each alert. Starts from 0.
    this.toast = true;
    this.i18nEnabled = true;
    this.alerts = [];
    this.timeout = 5000;
  }

  clear() {
    this.alerts.splice(0, this.alerts.length);
  }

  get() {
    return this.alerts;
  }

  success(msg, params, position) {
    return this.addAlert({
      type: 'success',
      msg: msg,
      params: params,
      timeout: this.timeout,
      toast: this.isToast(),
      position: position
    }, []);
  }

  error(msg, params, position) {
    return this.addAlert({
      type: 'danger',
      msg: msg,
      params: params,
      timeout: 10000,
      toast: this.isToast(),
      position: position
    }, []);
  }

  warning = function (msg, params, position) {
    return this.addAlert({
      type: 'warning',
      msg: msg,
      params: params,
      timeout: this.timeout,
      toast: this.isToast(),
      position: position
    }, []);
  };

  info(msg, params, position) {
    return this.addAlert({
      type: 'info',
      msg: msg,
      params: params,
      timeout: this.timeout,
      toast: this.isToast(),
      position: position
    }, []);
  };

  factory(alertOptions) {
    let _this = this;
    let alert = {
      type: alertOptions.type,
      msg: this.sanitizer.sanitize(SecurityContext.HTML, alertOptions.msg),
      id: alertOptions.id,
      timeout: alertOptions.timeout,
      toast: alertOptions.toast,
      position: alertOptions.position ? alertOptions.position : 'top right',
      scoped: alertOptions.scoped,
      close: function (alerts) {
        return _this.closeAlert(alertOptions.id, alerts);
      }
    };
    if (!alert.scoped) {
      this.alerts.push(alert);
    }
    return alert;
  };

  addAlert(alertOptions, extAlerts) {
    this.clear();
    let _this = this;
    alertOptions.id = this.alertId++;
    if (this.i18nEnabled && alertOptions.msg) {
      alertOptions.msg = this.translateService.instant(alertOptions.msg);
      if (alertOptions.params) {
        if (Array.isArray(alertOptions.params)) {
          for (let v in alertOptions.params) {
            let key = alertOptions.params[v];
            let trans = this.translateService.instant(key);
            alertOptions.msg += ' (' + trans + ')';
          }
        }
        else {
          alertOptions.msg += ' (' + this.translateService.instant(alertOptions.params + ')');
        }
      }
    }
    let alert = this.factory(alertOptions);
    if (alertOptions.timeout && alertOptions.timeout > 0) {
      setTimeout(function () {
        _this.closeAlert(alertOptions.id, extAlerts);
      }, alertOptions.timeout);
    }
    return alert;
  };

  closeAlert(id, extAlerts) {
    let thisAlerts = (extAlerts && extAlerts.length > 0) ? extAlerts : this.alerts;
    return this.closeAlertByIndex(thisAlerts.map(function (e) {
      return e.id;
    }).indexOf(id), thisAlerts);
  };

  closeAlertByIndex(index, thisAlerts) {
    return thisAlerts.splice(index, 1);
  };

  isToast() {
    return this.toast;
  };
}

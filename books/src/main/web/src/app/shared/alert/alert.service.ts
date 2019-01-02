import {Injectable, Sanitizer} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {NgxAlertsService} from '@ngx-plus/ngx-alerts';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  i18nEnabled;
  item: any = {
    title: 'Notify Title',
    body: 'Notify Body',
  };

  constructor(private sanitizer: Sanitizer,
              private translateService: TranslateService,
              private alertService: NgxAlertsService,) {
    this.i18nEnabled = true;
  }

  success(msg, params, position) {
    this.item.title = this.translateService.instant('success.title');
    if (this.i18nEnabled && msg) {
      msg = this.translateService.instant(msg);
      if (params) {
        if (Array.isArray(params)) {
          for (let v in params) {
            let key = params[v];
            let trans = this.translateService.instant(key);
            msg += ' (' + trans + ')';
          }
        }
        else {
          msg += ' (' + this.translateService.instant(params + ')');
        }
      }
    }

    this.item.body = msg;
    this.alertService.notifySuccess(this.item);
  }

  error(msg, params, position) {
    if (msg !== 'Unauthorized') {
      this.item.title = this.translateService.instant('error.title');
      if (this.i18nEnabled && msg) {
        msg = this.translateService.instant(msg);
        if (params) {
          if (Array.isArray(params)) {
            for (let v in params) {
              let key = params[v];
              let trans = this.translateService.instant(key);
              msg += ' (' + trans + ')';
            }
          }
          else {
            msg += ' (' + this.translateService.instant(params + ')');
          }
        }
      }

      this.item.body = msg;
      this.alertService.notifyError(this.item);
    }
  }

  warning = function (msg, params, position) {
    this.item.title = this.translateService.instant('warning.title');
    if (this.i18nEnabled && msg) {
      msg = this.translateService.instant(msg);
      if (params) {
        if (Array.isArray(params)) {
          for (let v in params) {
            let key = params[v];
            let trans = this.translateService.instant(key);
            msg += ' (' + trans + ')';
          }
        }
        else {
          msg += ' (' + this.translateService.instant(params + ')');
        }
      }
    }

    this.item.body = msg;
    this.alertService.notifyWarning(this.item);
  };

  info(msg, params, position) {
    this.item.title = this.translateService.instant('info.title');
    if (this.i18nEnabled && msg) {
      msg = this.translateService.instant(msg);
      if (params) {
        if (Array.isArray(params)) {
          for (let v in params) {
            let key = params[v];
            let trans = this.translateService.instant(key);
            msg += ' (' + trans + ')';
          }
        }
        else {
          msg += ' (' + this.translateService.instant(params + ')');
        }
      }
    }

    this.item.body = msg;
    this.alertService.notifyInfo(this.item);
  };
}

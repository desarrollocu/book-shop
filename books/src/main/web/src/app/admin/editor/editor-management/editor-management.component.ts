import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {EditorService} from '../editor.service';

import {Editor} from '../model/editor';
import {Country} from '../../user/model/country';
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-editor-management',
  templateUrl: './editor-management.component.html',
  styleUrls: ['./editor-management.component.scss']
})
export class EditorManagementComponent implements OnInit {
  @Input() editor;
  countryList: Country[];
  currentLang: string;

  constructor(private alertService: AlertDialogService,
              private editorService: EditorService,
              private translateService: TranslateService,
              public activeModal: NgbActiveModal) {
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.findEditor();
    this.getCountries();
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.countryLanguage();
    });
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

  findEditor() {
    this.editorService.getEditor(this.editor)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  getCountries() {
    this.editorService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
  }

  saveEditor() {
    this.editorService.saveEditor(this.editor)
      .subscribe(response => this.onSuccess(response, this.editor),
        response => this.onError(response));
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  private onSuccess(response, editor) {
    let msg = 'success.add';
    if (editor.id) {
      msg = 'success.edited';
    }
    this.editor = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.close();
  }

  private onSearchSuccess(result) {
    if (result.body !== null)
      this.editor = result.body;
    else {
      this.editor = new Editor();
    }
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  cancel() {
    this.activeModal.dismiss();
  }
}

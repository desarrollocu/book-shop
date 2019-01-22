import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {EditorManagementComponent} from "../editor-management/editor-management.component";
import {AlertService} from '../../../shared/alert/alert.service';
import {EditorService} from '../editor.service';

import {Editor} from '../model/editor';
import {Country} from "../../user/model/country";
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-editor-list',
  templateUrl: './editor-list.component.html'
})
export class EditorListComponent implements OnInit {
  page: number;
  editor = new Editor();
  itemsPerPage: number;
  totalItems: any;
  editorList: Editor[];
  predicate: any;
  reverse: any;
  remEditor: Editor;
  countryList: Country[];
  currentLang: string;

  constructor(private editorService: EditorService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private modalService: NgbModal) {
    this.itemsPerPage = 5;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getEditors(null);
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

  getEditors(param) {
    if (param === 'btn')
      this.page = 0;

    this.editorList = [];
    this.editorService.getEditors({
      editorDTO: this.editor,
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: HttpResponse<Editor[]>) => this.onSuccess(response),
        (response: HttpResponse<any>) => this.onError(response)
      );
  }

  addEditor() {
    const modalRef = this.modalService.open(EditorManagementComponent, {size: 'lg'});
    modalRef.componentInstance.editor = new Editor();
    modalRef.result.then(value => {
    }, (reason => {
      this.getEditors('btn');
    }))
  }

  editEditor(editor) {
    const modalRef = this.modalService.open(EditorManagementComponent, {size: 'lg'});
    modalRef.componentInstance.editor = editor;
    modalRef.result.then(value => {
    }, (reason => {
      this.getEditors('btn');
    }))
  }

  removeEditor() {
    this.editorService.deleteEditor(this.remEditor.id)
      .subscribe(response => this.onSuccessDelete(),
        response => this.onErrorDelete(response));
    this.cancel();
  }

  openDialog(deleteEditor, editor) {
    this.remEditor = editor;
    this.modalService.open(deleteEditor);
  }

  private onSuccess(res) {
    this.editorList = res.body.elements;
    this.totalItems = res.body.total;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  onSuccessDelete() {
    this.alertService.success('success.remove', null, null);
    this.getEditors('btn');
  }

  onErrorDelete(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  trackIdentity(index, item: Editor) {
    return item.id;
  }

  getCountries() {
    this.editorService.getCountries()
      .subscribe(response => this.onCountrySuccess(response),
        response => this.onError(response));
  }

  private onCountrySuccess(result) {
    this.countryList = [];
    if (result !== null)
      this.countryList = result;
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

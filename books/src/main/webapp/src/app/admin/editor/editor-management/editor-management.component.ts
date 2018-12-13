import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {AlertDialogService} from '../../../shared/alert/alert.dialog.service';
import {EditorService} from '../editor.service';

import {Editor} from '../model/editor';

@Component({
  selector: 'app-editor-management',
  templateUrl: './editor-management.component.html',
  styleUrls: ['./editor-management.component.css']
})
export class EditorManagementComponent implements OnInit {
  @Input() editor;

  constructor(private alertService: AlertDialogService,
              private editorService: EditorService,
              public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
    this.findEditor();
  }

  findEditor() {
    this.editorService.getEditor(this.editor)
      .subscribe(response => this.onSearchSuccess(response),
        response => this.onError(response));
  }

  saveEditor() {
    this.editorService.saveEditor(this.editor)
      .subscribe(response => this.onSuccess(response, this.editor),
        response => this.onError(response));
  }

  private onSuccess(response, editor) {
    let msg = 'success.add';
    if (editor.id) {
      msg = 'success.edited';
    }
    this.editor = response.body;
    this.alertService.success(msg, null, null);
    this.activeModal.dismiss('cancel');
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
    this.activeModal.dismiss('cancel');
  }
}

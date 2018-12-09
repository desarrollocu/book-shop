import {Injectable} from '@angular/core';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {LoginModalComponent} from '../../shared/login/login-modal.component';


@Injectable({providedIn: 'root'})
export class LoginModalService {
  private isOpen = false;

  constructor(private modalService: NgbModal) {
  }

  open(): NgbModalRef {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    // const modalRef = this.modalService.open(LoginModalComponent, {windowClass:'modal-md'});
    const modalRef = this.modalService.open(LoginModalComponent);
    modalRef.result.then(
      result => {
        this.isOpen = false;
      },
      reason => {
        this.isOpen = false;
      }
    );
    return modalRef;
  }
}

import {Directive, Input, TemplateRef, ViewContainerRef} from '@angular/core';
import {Principal} from "../../core/auth/principal.service";


@Directive({
  selector: '[hasAnyAuthority]'
})
export class HasAnyAuthorityDirective {
  private authorities: string;

  constructor(private principal: Principal, private templateRef: TemplateRef<any>, private viewContainerRef: ViewContainerRef) {
  }

  @Input()
  set hasAnyAuthority(value: string) {
    this.authorities = value;
    this.updateView();
    // Get notified each time authentication state changes.
    this.principal.getAuthenticationState().subscribe(identity => this.updateView());
  }

  private updateView(): void {
    this.principal.hasAnyAuthority(this.authorities).then(result => {
      this.viewContainerRef.clear();
      if (result) {
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      }
    });
  }
}

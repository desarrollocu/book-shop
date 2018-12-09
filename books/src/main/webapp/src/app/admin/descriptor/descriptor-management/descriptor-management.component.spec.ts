import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DescriptorManagementComponent } from './descriptor-management.component';

describe('DescriptorManagementComponent', () => {
  let component: DescriptorManagementComponent;
  let fixture: ComponentFixture<DescriptorManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DescriptorManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DescriptorManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

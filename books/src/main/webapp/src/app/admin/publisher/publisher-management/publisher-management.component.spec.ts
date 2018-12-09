import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublisherManagementComponent } from './publisher-management.component';

describe('PublisherManagementComponent', () => {
  let component: PublisherManagementComponent;
  let fixture: ComponentFixture<PublisherManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublisherManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublisherManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

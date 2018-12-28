import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineManagementComponent } from './magazine-management.component';

describe('MagazineManagementComponent', () => {
  let component: MagazineManagementComponent;
  let fixture: ComponentFixture<MagazineManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

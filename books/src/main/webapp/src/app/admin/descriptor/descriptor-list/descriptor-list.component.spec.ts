import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DescriptorListComponent } from './descriptor-list.component';

describe('DescriptorListComponent', () => {
  let component: DescriptorListComponent;
  let fixture: ComponentFixture<DescriptorListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DescriptorListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DescriptorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

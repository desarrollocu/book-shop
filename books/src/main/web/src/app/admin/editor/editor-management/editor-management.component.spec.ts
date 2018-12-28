import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorManagementComponent } from './editor-management.component';

describe('EditorManagementComponent', () => {
  let component: EditorManagementComponent;
  let fixture: ComponentFixture<EditorManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

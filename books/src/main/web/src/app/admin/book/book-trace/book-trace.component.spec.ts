import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookTraceComponent } from './book-trace.component';

describe('BookTraceComponent', () => {
  let component: BookTraceComponent;
  let fixture: ComponentFixture<BookTraceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookTraceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookTraceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

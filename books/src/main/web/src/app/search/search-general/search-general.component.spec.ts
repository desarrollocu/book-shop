import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchGeneralComponent } from './search-general.component';

describe('SearchGeneralComponent', () => {
  let component: SearchGeneralComponent;
  let fixture: ComponentFixture<SearchGeneralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchGeneralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

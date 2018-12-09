import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchMagazineComponent } from './search-magazine.component';

describe('SearchMagazineComponent', () => {
  let component: SearchMagazineComponent;
  let fixture: ComponentFixture<SearchMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

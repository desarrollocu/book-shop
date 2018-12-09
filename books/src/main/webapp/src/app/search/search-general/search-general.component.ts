import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-search-general',
  templateUrl: './search-general.component.html',
  styleUrls: ['./search-general.component.css'],
  providers: [NgbRatingConfig],
  encapsulation: ViewEncapsulation.None,
})
export class SearchGeneralComponent implements OnInit {
  page: number;
  currentRate = 2;

  constructor(config: NgbRatingConfig) {
    config.max = 5;
  }

  ngOnInit() {
  }

  imageClick(){
    alert('OK');
  }
}

import {Component, OnInit} from '@angular/core';
import {LangChangeEvent, TranslateService} from "@ngx-translate/core";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HttpResponse} from '@angular/common/http';

import {SearchService} from '../search.service';
import {AlertService} from '../../shared/alert/alert.service';
import {TopicService} from '../../admin/topic/topic.service';
import {CartService} from '../cart.service';

import {Search} from '../model/search';
import {Topic} from '../../admin/topic/model/topic';
import {Magazine} from '../../admin/magazine/model/magazine';

@Component({
  selector: 'app-search-magazine',
  templateUrl: './search-magazine.component.html',
  styleUrls: ['./search-magazine.component.scss']
})
export class SearchMagazineComponent implements OnInit {
  page: number;
  search = new Search();
  itemsPerPage: number;
  totalItems: any;
  magazineList: Magazine[];
  predicate: any;
  reverse: any;
  selectedMagazine: Magazine;
  images: string[];
  topics: Topic[];
  currentLang: string;
  searchMore: boolean;
  load: boolean;

  constructor(private searchService: SearchService,
              private alertService: AlertService,
              private translateService: TranslateService,
              private topicService: TopicService,
              private cartService: CartService,
              private modalService: NgbModal) {
    this.searchMore = false;
    this.load = false;
    this.itemsPerPage = 10;
    this.predicate = 'title';
    this.reverse = true;
    this.page = 0;
    this.selectedMagazine = new Magazine();
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getMagazines(null, false);
    this.findTopics();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
    });
  }

  getMagazines(param, flag) {
    if (param === 'btn')
      this.page = 0;
    else {
      this.predicate = param;
      if (flag)
        this.reverse = !this.reverse;
    }

    this.magazineList = [];
    this.searchService.searchMagazine({
      searchDTO: this.search,
      pageable: {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      }
    })
      .subscribe(
        (response: HttpResponse<Magazine[]>) => this.onSuccess(response),
        (response: HttpResponse<any>) => this.onError(response)
      );
  }

  private onSuccess(res) {
    this.magazineList = res.body.elements;
    this.totalItems = res.body.total;
    this.load = true;
  }

  private onError(response) {
    let error = response.error;
    let fields = error.fields;
    this.alertService.error(error.error, fields, null);
  }

  sort() {
    this.predicate = this.predicate !== null ? this.predicate : 'id';
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    return result;
  }

  trackIdentity(index, item: Magazine) {
    return item.id;
  }

  findTopics() {
    this.topicService.getAllTopics()
      .subscribe(response => this.onTopicsSuccess(response),
        response => this.onError(response));
  }

  private onTopicsSuccess(response) {
    this.topics = [];
    if (response) {
      this.topics = response;
      this.topicLanguage();
    }
  }

  private topicLanguage() {
    let temp = this.topics;
    this.topics = [];
    if (temp) {
      for (let i in temp) {
        this.topics = [...this.topics, temp[i]];
      }
    }
  }

  addToCar(magazine) {
    if (magazine !== undefined) {
      this.cartService.addToCart({
        id: magazine !== undefined ? magazine.id : this.selectedMagazine.id,
        cant: 1,
        book: false
      }).subscribe(response => this.onAddCarSuccess(response.body),
        response => this.onError(response));
    }
  }

  onAddCarSuccess(resp) {
    if (resp.exist)
      this.alertService.info('info.inCart', null, null);
    else
      this.alertService.info('shopping.success', null, null);

    this.cartService.getCarSubject().next(resp.cant);
    this.cancel();
  }

  magazineDetails(details, magazine) {
    this.selectedMagazine = new Magazine();
    this.selectedMagazine = magazine;
    this.selectMagazine(magazine);
    this.modalService.open(details, {size: 'lg'});
  }

  selectMagazine(magazine) {
    this.selectedMagazine = new Magazine();
    this.selectedMagazine = magazine;
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

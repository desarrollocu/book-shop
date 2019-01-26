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
  currentRate = 2;
  years: string[];
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
              private shoppingService: CartService,
              private modalService: NgbModal) {
    this.searchMore = false;
    this.load = false;
    this.itemsPerPage = 12;
    this.predicate = 'id';
    this.reverse = true;
    this.page = 0;
    this.selectedMagazine = new Magazine();
    this.currentLang = this.translateService.currentLang;
  }

  ngOnInit() {
    this.getMagazines(null);
    this.findTopics();

    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      this.currentLang = this.translateService.currentLang;
      this.topicLanguage();
    });
  }

  searchMoreChange() {
    this.searchMore = !this.searchMore;
  }

  getMagazines(param) {
    if (param === 'btn')
      this.page = 0;

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
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
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

  selectMagazine(magazine) {
    this.selectedMagazine = new Magazine();
    this.selectedMagazine = magazine;
  }

  addToCar() {
    this.shoppingService.toCar(this.selectedMagazine, false);
    this.cancel();
  }

  magazineDetails(details) {
    this.modalService.open(details);
  }

  cancel() {
    this.modalService.dismissAll('cancel')
  }
}

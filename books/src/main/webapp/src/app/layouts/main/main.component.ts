import {Component, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, NavigationEnd, Router} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {TranslateService} from '@ngx-translate/core';
import {NgSelectConfig} from '@ng-select/ng-select';

import {StateStorageService} from '../../core/auth/state-storage.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  sidebarOpen = false;

  constructor(private titleService: Title,
              private router: Router,
              private stateStorageService: StateStorageService,
              private translate: TranslateService,
              private config: NgSelectConfig) {
    let language = this.stateStorageService.getLanguage();
    if (language) {
      translate.setDefaultLang(language);
    }
    else {
      translate.setDefaultLang('en');
    }
    this.config.notFoundText = '';
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'BooksStore';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
      }
    });
  }

  toggleNavbar() {
    this.sidebarOpen = !this.sidebarOpen;
  }
}

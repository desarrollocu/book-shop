import {Component, OnInit} from '@angular/core';
import {Principal} from '../../core/auth/principal.service';
import {CartService} from '../../search/cart.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  images: string[];
  cartCant: number;

  constructor(private principal: Principal, private cartService: CartService) {
    this.images = ["assets/images/img.jpg", "assets/images/login.png", "assets/images/img.jpg"];
    this.cartCant = this.cartService.getProductList().length;
  }

  ngOnInit() {
    this.cartService.getCarSubject().subscribe((value => {
      this.cartCant = value;
    }))
  }

  isAdmin() {
    return this.principal.isAdmin();
  }
}

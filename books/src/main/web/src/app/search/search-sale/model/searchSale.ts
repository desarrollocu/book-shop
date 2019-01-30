import {UserSale} from './userSale';
import {Shipping} from './shipping';

export class SearchSale {
  constructor(
    public id?: string,
    public total?: number,
    public shippingCost?: number,
    public items?: string,
    public user?: UserSale,
    public shipping?: Shipping,
    public info?: string,
    public saleState?: string,
    public saleDate?: string,
    public description?: string
  ) {
  }
}

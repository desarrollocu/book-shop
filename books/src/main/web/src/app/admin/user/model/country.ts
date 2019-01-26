import {DhlPrice} from './dhl-price';

export class Country {
  constructor(
    public id?: any,
    public spanishName?: string,
    public englishName?: string,
    public code?: string,
    public name?: string,
    public group?: string,
    public priceList?: DhlPrice[]
  ) {
  }
}

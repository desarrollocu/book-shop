import {DocumentSale} from './documentSale';

export class DetailSale {
  constructor(
    public id?: string,
    public document?: DocumentSale,
    public cant?: number,
    public mount?: number
  ) {
  }
}

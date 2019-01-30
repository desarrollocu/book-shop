import {Country} from '../../../admin/user/model/country';

export class Shipping {
  constructor(
    public fullName?: string,
    public email?: string,
    public phone?: string,
    public address?: string,
    public state?: string,
    public city?: string,
    public postalCode?: string,
    public country?: Country,
  ) {
  }
}

import {Country} from '../../../admin/user/model/country';

export class Shipping {
  constructor(
    public fullName?: string,
    public email?: string,
    public phone?: string,
    public line1?: string,
    public line2?: string,
    public state?: string,
    public city?: string,
    public postalCode?: string,
    public country?: Country,
  ) {
  }
}

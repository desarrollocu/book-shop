import {Country} from '../../../admin/user/model/country';

export class ShippingInfo {
  constructor(
    public id?: any,
    public fullName?: any,
    public email?: string,
    public phone?: string,
    public country?: Country,
    public city?: string,
    public state?: string,
    public postalCode?: string,
    public line1?: string,
    public line2?: string,
  ) {
  }
}

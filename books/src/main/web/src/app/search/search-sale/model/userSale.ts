import {Country} from '../../../admin/user/model/country';

export class UserSale {
  constructor(
    public id?: string,
    public userId?: string,
    public userName?: string,
    public fullName?: string,
    public email?: string,
    public line1?: string,
    public line2?: string,
    public country?: Country,
    public state?: string,
    public city?: string,
    public cp?: string,
    public phone?: number,
  ) {
  }
}

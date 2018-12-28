import {Country} from '../../user/model/country';

export class Author {
  constructor(
    public id?: any,
    public fullName?: string,
    public firstName?: string,
    public lastName?: string,
    public city?: string,
    public state?: string,
    public country?: Country,
    public bornDate?: string,
    public deathDate?: string,
  ) {
  }
}

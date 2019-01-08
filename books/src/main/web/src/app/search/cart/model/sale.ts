import {Detail} from './detail';
import {UserDetail} from './user-detail';

export class Sale {
  constructor(
    public id?: any,
    public total?: number,
    public saleDate?: Date,
    public detailList?: Detail[],
    public userDetail?: UserDetail
  ) {
  }
}

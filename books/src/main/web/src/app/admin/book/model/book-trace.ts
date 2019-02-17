import {User} from '../../../core/user/user.model';

export class BookTrace {
  constructor(
    public user?: User,
    public date?: string,
    public action?: string
  ) {
  }
}

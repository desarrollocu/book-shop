import {Country} from '../../../admin/user/model/country';

export class CartHelp {
  constructor(
    public productDTOList?: any[],
    public countryDTO?: Country
  ) {
  }
}

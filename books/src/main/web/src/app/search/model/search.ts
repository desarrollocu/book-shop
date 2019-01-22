import {Country} from "../../admin/user/model/country";

export class Search {
  constructor(
    public author?: string,
    public title?: string,
    public subTitle?: string,
    public editor?: string,
    public city?: string,
    public editionCity?: string,
    public editionCountry?: Country,
    public editionYear?: string,
    public descriptors?: string,
    public topicList?: any,
    public isbn?: any,
    public classification?: any
  ) {
  }
}

export class Magazine {
  constructor(
    public id?: any,
    public title?: string,
    public editorList?: any[],
    public topicList?: any[],
    public isbn?: string,
    public comments?: string,
    public frequency?: string,
    public salePrice?: number,
    public coin?: string,
    public publishYear?: string,
    public image?: string,
    public stockNumber?: number,
    public toShow?: any,
    public visit?: number,
    public weight?: number,
    public createdBy?: string,
    public createdDate?: string,
    public lastModifiedBy?: string,
    public lastModifiedDate?: string,
    public topicsEnglish?: string,
    public topicsSpanish?: string,
    public editors?: string,
    public authors?: string,
    public cities?: string,
    public countriesEnglish?: string,
    public countriesSpanish?: string
  ) {
  }
}

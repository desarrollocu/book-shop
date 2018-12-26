export class Book {
  constructor(
    public id?: any,
    public authorList?: any[],
    public title?: string,
    public subTitle?: string,
    public city?: string,
    public editor?: any,
    public classification?: any,
    public editionYear?: string,
    public descriptors?: string,
    public pages?: number,
    public size?: string,
    public isbn?: string,
    public topic?: any,
    public salePrice?: number,
    public coin?: string,
    public stockNumber?: number,
    public image?: string,
    public descriptorList?: any[],
    public visit?: number,
    public createdBy?: string,
    public createdDate?: string,
    public lastModifiedBy?: string,
    public lastModifiedDate?: string
  ) {
    this.stockNumber = 0;
  }
}

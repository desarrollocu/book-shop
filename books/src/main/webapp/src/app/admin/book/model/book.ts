export class Book {
  constructor(
    public id?: any,
    public authorList?: any[],
    public title?: string,
    public subTitle?: string,
    public city?: string,
    public editor?: any,
    public editionDate?: string,
    public pages?: number,
    public size?: string,
    public isbn?: string,
    public topic?: any,
    public salePrice?: number,
    public coin?: string,
    public image?: string,
    public edition?: string,
    public editorial?: string,
    public descriptorList?: any[],
    public visit?: number,
    public createdBy?: string,
    public createdDate?: string,
    public lastModifiedBy?: string,
    public lastModifiedDate?: string,
  ) {
  }
}

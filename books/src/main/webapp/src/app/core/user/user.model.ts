export class User {
  constructor(
    public id?: any,
    public userName?: string,
    public fullName?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public isAdmin?: string,
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string
  ) {
  }
}

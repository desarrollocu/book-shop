export class Doc {
  constructor(
    public id?: string,
    public salePrice?: number,
    public title?: string,
    public image?: string,
    public cant?: number,
    public weight?: number,
    public totalWeight?: number,
    public realCant?: number,
    public mount?: number,
    public isBook?: boolean
  ) {
  }
}

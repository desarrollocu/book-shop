import {Country} from "../../user/model/country";

export class Editor {
  constructor(
    public id?: any,
    public name?: string,
    public city?: string,
    public country?: Country,
  ) {
  }
}

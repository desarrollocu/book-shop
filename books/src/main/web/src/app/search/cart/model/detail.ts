import {Editor} from "../../../admin/editor/model/editor";
import {Topic} from "../../../admin/topic/model/topic";

export class Detail {
  constructor(
    public id?: any,
    public title?: string,
    public city?: string,
    public editor?: Editor,
    public salePrice?: number,
    public isbn?: string,
    public topic?: Topic,
    public cant?: number,
    public mount?: number,
  ) {
  }
}

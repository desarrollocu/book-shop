import {Editor} from "../../../admin/editor/model/editor";
import {Topic} from "../../../admin/topic/model/topic";

export class Doc {
  constructor(
    public id?: string,
    public title?: string,
    public coin?: string,
    public image?: string,
    public salePrice?: number,
    public cant?: number,
    public realCant?: number,
    public city?: string,
    public editor?: Editor,
    public isbn?: string,
    public topic?: Topic,
    public mount?: number,
  ) {
  }
}

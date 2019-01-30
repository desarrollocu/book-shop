import {Topic} from "../../../admin/topic/model/topic";
import {Editor} from "../../../admin/editor/model/editor";

export class DocumentSale {
  constructor(
    public id?: string,
    public documentId?: string,
    public title?: string,
    public editorList?: Editor[],
    public salePrice?: number,
    public isbn?: string,
    public weight?: number,
    public topicList?: Topic[],
    public coin?: string,
    public stockNumber?: number,
  ) {
  }
}

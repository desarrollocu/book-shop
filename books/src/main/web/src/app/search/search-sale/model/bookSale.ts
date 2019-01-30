import {Topic} from '../../../admin/topic/model/topic';
import {Editor} from '../../../admin/editor/model/editor';
import {DocumentSale} from './documentSale';
import {Author} from '../../../admin/author/model/author';
import {Classification} from '../../../admin/classification/model/classification';

export class BookSale extends DocumentSale {
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
    public authorList?: Author[],
    public subTitle?: string,
    public editionYear?: number,
    public pages?: number,
    public size?: string,
    public classification?: Classification,
    public descriptorList?: string[],
  ) {
    super(id, documentId, title, editorList, salePrice, isbn, weight, topicList, coin, stockNumber);
  }
}

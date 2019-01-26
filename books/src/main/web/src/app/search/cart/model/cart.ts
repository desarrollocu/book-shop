import {Doc} from "./doc";

export class Cart {
  constructor(
    public amount?: number,
    public totalKgs?: number,
    public cant?: number,
    public shippingCost?: number,
    public shopDTOList?: Doc[],
  ) {
  }
}

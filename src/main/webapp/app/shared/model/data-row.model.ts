export interface IDataRow {
  deliveryDate?: string;
  product?: string;
  category?: string;
  declaredAmount?: number;
  fee?: number;
  feeAmount?: number;
}
export class DataRow implements IDataRow {

  constructor(
    public deliveryDate?: string,
    public product?: string,
    public category?: string,
    public declaredAmount?: number,
    public fee?: number,
    public feeAmount?: number
  ) {
  }
}

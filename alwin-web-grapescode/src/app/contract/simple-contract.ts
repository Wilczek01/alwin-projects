/**
 * Umowa
 */
export class SimpleContract {
  id: number;
  contractNo: string;
  signDate: Date;
  endDate: Date;
  stateSymbol: number;
  stateDescription: string;
  active: boolean;
  invoiced: boolean;
  currency: string;
  financingType: string;
  financingState: string;
  distributionChannel: string;
  distributionOperator: string;
  contractValue: number;
}

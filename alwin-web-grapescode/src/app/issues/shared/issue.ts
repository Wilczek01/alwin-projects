import {Customer} from '../../customer/shared/customer';
import {Contract} from '../../customer/shared/contract';
import {KeyLabel} from '../../shared/key-label';
import {IssueType} from './issue-type';
import {OperatorUser} from '../../operator/operator-user';
import {Invoice} from './invoice';
import {Tag} from '../../tag/tag';

/**
 * Zlecenie windykacyjne
 */
export class Issue {
  id: number;
  assignee: OperatorUser;
  customer: Customer;
  contract: Contract;
  startDate: Date;
  expirationDate: Date;
  terminationCause: string;
  issueType: IssueType;
  issueState: KeyLabel;
  rpbPLN: number;
  balanceStartPLN: number;
  paymentsPLN: number;
  currentBalancePLN: number;
  rpbEUR: number;
  balanceStartEUR: number;
  paymentsEUR: number;
  currentBalanceEUR: number;
  totalBalanceStartPLN: number;
  totalCurrentBalancePLN: number;
  totalPaymentsPLN: number;
  issueInvoice: Array<Invoice>;
  priority: KeyLabel;
  tags: Tag[];
  /**
   * dpd podczas tworzenia zlecenia
   */
  dpdStart: number;
  /**
   * szacowane dpd na dzień zakończenia zlecenia
   */
  dpdEstimated: number;
  /**
   * Data ostatniej aktualizacji salda
   */
  balanceUpdateDate: Date;
  /**
   * Numer klienta w LEO
   */
  extCompanyId: number;

}

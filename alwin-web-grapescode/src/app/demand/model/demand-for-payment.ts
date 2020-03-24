import {DemandForPaymentType} from './demand-for-payment-type';
import {FormalDebtCollectionInvoice} from '../../issues/shared/formal-debt-collection-invoice';
import {Operator} from '../../operator/operator';

/**
 * Wezwanie do zapłaty
 */
export class DemandForPayment {

  id: number;

  /**
   * Data wystawienia
   */
  issueDate: Date;

  /**
   * Data zapłaty
   */
  dueDate: Date;

  /**
   * Nr faktury, który spowodował wysłanie
   */
  initialInvoiceNo: string;

  /**
   * Nr klienta
   */
  extCompanyId: number;

  /**
   * Nazwa klienta
   */
  companyName: string;

  contractNumber: string;
  type: DemandForPaymentType;

  /**
   * Nr faktury, która jest kosztem
   */
  chargeInvoiceNo: string;

  state: string;
  attachmentRef: string;
  invoices: FormalDebtCollectionInvoice[];

  manualCreationMessage: string[];

  /**
   * Data zmiany stanu
   */
  stateChangeDate: Date;

  /**
   * Przyczyna zmiany stanu
   */
  stateChangeReason: string;

  /**
   * Identyfikator operatora zmieniającego stan
   */
  stateChangeOperator: Operator;

  /**
   * Flaga określająca, czy wezwanie zastąpione w procesie wezwaniem utworzonym ręcznie (wyłączone z procesu)
   */
  aborted: boolean;

  /**
   * Słownikowa przyczyna anulowania wezwania
   */
  reasonTypeLabel: string;
}

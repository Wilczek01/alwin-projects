import {FinancingElement} from './financing-element';
import {FinancingPayment} from './financing-payment';

/**
 * Rata harmonogramu
 */
export class Instalment {
  id: number;
  paymentDate: Date;
  remainingPayment: number;
  index: number;
  capital: number;
  interest: number;
  payment: number;
  financingElement: FinancingElement;
  financingPayment: FinancingPayment;
}

import {KeyLabel} from '../../shared/key-label';

/**
 * Typ wezwania do zapłaty
 */
export class DemandForPaymentType {
  id: number;
  key: string;
  dpdStart: number;
  numberOfDaysToPay: number;
  charge: number;
  segment: KeyLabel;
  minDuePaymentValue: number;
  minDuePaymentPercent: number;
}

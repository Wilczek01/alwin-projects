import {DemandForPayment} from './demand-for-payment';

/**
 * Nowe wezwanie do zapłaty z możliwością zaznaczenia czy ma być wysłane czy usunięte
 */
export class NewDemandForPayment {
  demand: DemandForPayment;
  send = false;
  reject = false;
  rejectReason: string;
}

import {Instalment} from './instalment';

/**
 * Harmonogram płatności wraz z ratami
 */
export class PaymentScheduleWithInstalments {
  id: number;
  calculationType: number;
  name: string;
  active: boolean;
  instalments: Instalment[];
}

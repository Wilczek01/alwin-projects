import {CustomerOutOfService} from './customer-out-of-service';

/**
 * Blokada klienta w windykacji formalnej
 */
export class FormalDebtCollectionCustomerOutOfService extends CustomerOutOfService {
  /**
   * Wybrana przyczyna wykluczenia
   */
  reasonType: string;

  /**
   * Opis wybranej przyczyny wykluczenia
   */
  reasonTypeLabel: string;

  /**
   * Typ wezwania do zapłaty
   */
  demandForPaymentType: string;
}

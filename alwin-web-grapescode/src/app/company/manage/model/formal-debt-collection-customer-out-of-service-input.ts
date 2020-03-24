/**
 * Obiekt do zapisu/aktualizacji blokady klienta w windykacji formalnej
 */
import {CustomerOutOfServiceInput} from './customer-out-of-service-input';
import {FormalDebtCollectionCustomerOutOfService} from './formal-debt-collection-customer-out-of-service';

export class FormalDebtCollectionCustomerOutOfServiceInput extends CustomerOutOfServiceInput {

  constructor(customerOutOfService?: FormalDebtCollectionCustomerOutOfService) {
    super(customerOutOfService);
    if (customerOutOfService != null) {
      this.reasonType = customerOutOfService.reasonType;
      this.demandForPaymentType = customerOutOfService.demandForPaymentType;
    }
  }

  /**
   * Wybrana przyczyna wykluczenia
   */
  reasonType: string;

  /**
   * Typ wezwania do zapłaty
   */
  demandForPaymentType: string;
}

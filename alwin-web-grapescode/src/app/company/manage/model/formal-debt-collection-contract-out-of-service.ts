import {ContractOutOfService} from './contract-out-of-service';

/**
 * Blokada kontraktu w windykacji formalnej
 */
export class FormalDebtCollectionContractOutOfService extends ContractOutOfService {
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

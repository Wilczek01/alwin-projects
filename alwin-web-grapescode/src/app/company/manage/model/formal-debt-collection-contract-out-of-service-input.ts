/**
 * Obiekt do zapisu/aktualizacji blokady kontraktu w windykacji formalnej
 */
import {ContractOutOfServiceInput} from './contract-out-of-service-input';
import {FormalDebtCollectionContractOutOfService} from './formal-debt-collection-contract-out-of-service';

export class FormalDebtCollectionContractOutOfServiceInput extends ContractOutOfServiceInput {

  constructor(contractOutOfService?: FormalDebtCollectionContractOutOfService) {
    super(contractOutOfService);
    if (contractOutOfService != null) {
      this.reasonType = contractOutOfService.reasonType;
      this.demandForPaymentType = contractOutOfService.demandForPaymentType;
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

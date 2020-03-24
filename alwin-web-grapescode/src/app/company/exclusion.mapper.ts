import {CustomerOutOfService} from './manage/model/customer-out-of-service';
import {ContractOutOfService} from './manage/model/contract-out-of-service';
import {ContractWithExclusions} from '../contract/contract-with-exclusions';
import {FormalDebtCollectionCustomerOutOfService} from './manage/model/formal-debt-collection-customer-out-of-service';
import {ContractWithFormalDebtCollectionExclusions} from '../contract/contract-with-formal-debt-collection-exclusions';
import {FormalDebtCollectionContractOutOfService} from './manage/model/formal-debt-collection-contract-out-of-service';

/**
 * Klasa mapująca daty w obiektach wykluczeń ustawiając im odpowiedni typ
 */
export class ExclusionMapper {

  /**
   * Mapuje daty wykluczeń klienta na odpowiedni typ
   * @param {CustomerOutOfService[]} exclusions - wykluczenia klienta
   * @returns {CustomerOutOfService[]} wykluczenia klienta
   */
  static mapCustomerOutOfService(exclusions: CustomerOutOfService[]) {
    if (exclusions != null) {
      exclusions.forEach(exclusion => {
        exclusion.startDate = exclusion.startDate != null ? new Date(exclusion.startDate) : null;
        exclusion.endDate = exclusion.endDate != null ? new Date(exclusion.endDate) : null;
      });
    }
    return exclusions;
  }

  /**
   * Mapuje daty wykluczeń klienta na odpowiedni typ
   * @param {CustomerOutOfService[]} exclusions - wykluczenia klienta
   * @returns {CustomerOutOfService[]} wykluczenia klienta
   */
  static mapFormalDebtCollectionCustomerOutOfService(exclusions: FormalDebtCollectionCustomerOutOfService[]) {
    if (exclusions != null) {
      exclusions.forEach(exclusion => {
        exclusion.startDate = exclusion.startDate != null ? new Date(exclusion.startDate) : null;
        exclusion.endDate = exclusion.endDate != null ? new Date(exclusion.endDate) : null;
      });
    }
    return exclusions;
  }

  /**
   * Mapuje daty wykluczeń umów klienta na odpowiedni typ
   * @param {ContractOutOfService[]} exclusions - wykluczenia umów klienta
   * @returns {ContractOutOfService[]} wykluczenia umów klienta
   */
  static mapContractOutOfService(exclusions: ContractOutOfService[]) {
    if (exclusions != null) {
      exclusions.forEach(exclusion => {
        exclusion.startDate = exclusion.startDate != null ? new Date(exclusion.startDate) : null;
        exclusion.endDate = exclusion.endDate != null ? new Date(exclusion.endDate) : null;
      });
    }
    return exclusions;
  }

  /**
   * Mapuje daty wykluczeń umów klienta na odpowiedni typ
   * @param {ContractOutOfService[]} exclusions - wykluczenia umów klienta
   * @returns {ContractOutOfService[]} wykluczenia umów klienta
   */
  static mapFormalDebtCollectionContractOutOfService(exclusions: FormalDebtCollectionContractOutOfService[]) {
    if (exclusions != null) {
      exclusions.forEach(exclusion => {
        exclusion.startDate = exclusion.startDate != null ? new Date(exclusion.startDate) : null;
        exclusion.endDate = exclusion.endDate != null ? new Date(exclusion.endDate) : null;
      });
    }
    return exclusions;
  }

  /**
   * Mapuje daty wykluczeń umów klienta na odpowiedni typ w obiekcie umowy
   * @param {ContractWithExclusions[]} contracts - umowy klienta z wykluczeniami
   * @returns {ContractWithExclusions[]} umowy klienta z wykluczeniami
   */
  static mapContractWithExclusions(contracts: ContractWithExclusions[]) {
    if (contracts != null) {
      contracts.forEach(contract => {
        contract.exclusions = this.mapContractOutOfService(contract.exclusions);
      });
    }
    return contracts;
  }

  /**
   * Mapuje daty wykluczeń umów klienta na odpowiedni typ w obiekcie umowy
   * @param {ContractWithExclusions[]} contracts - umowy klienta z wykluczeniami
   * @returns {ContractWithExclusions[]} umowy klienta z wykluczeniami
   */
  static mapContractWithFormalDebtCollectionExclusions(contracts: ContractWithFormalDebtCollectionExclusions[]) {
    if (contracts != null) {
      contracts.forEach(contract => {
        contract.exclusions = this.mapFormalDebtCollectionContractOutOfService(contract.exclusions);
      });
    }
    return contracts;
  }
}

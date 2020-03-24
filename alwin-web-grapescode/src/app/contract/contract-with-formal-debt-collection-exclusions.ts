import {SimpleContract} from './simple-contract';
import {FormalDebtCollectionContractOutOfService} from '../company/manage/model/formal-debt-collection-contract-out-of-service';

/**
 * Umowa wraz z blokadami w windykacji formalnej
 */
export class ContractWithFormalDebtCollectionExclusions extends SimpleContract {
  exclusions: FormalDebtCollectionContractOutOfService[];
  excluded: boolean;
}

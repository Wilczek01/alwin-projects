import {ContractSubject} from '../../contract/contract-subject';
import {TerminationContractSubject} from './termination-contract-subject';

/**
 * Przedmiot leasingu na wypowiedzeniu wraz z kompletem danych o przedmiocie
 */
export class TerminationContractFullSubject {

  terminationData: TerminationContractSubject;
  subjectData: ContractSubject;
}

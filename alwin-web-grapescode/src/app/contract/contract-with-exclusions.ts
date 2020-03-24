import {SimpleContract} from './simple-contract';
import {ContractOutOfService} from '../company/manage/model/contract-out-of-service';

/**
 * Umowa wraz z blokadami
 */
export class ContractWithExclusions extends SimpleContract {
  exclusions: ContractOutOfService[];
  excluded: boolean;
}

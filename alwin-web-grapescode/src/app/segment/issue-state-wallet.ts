import {Wallet} from './wallet';
import {KeyLabel} from '../shared/key-label';

/**
 * Portfel zleceń liczony dla zleceń o podanym statusie zlecenia
 */
export class IssueStateWallet extends Wallet {
  /**
   * Status zlecenia
   */
  issueState: KeyLabel;
}

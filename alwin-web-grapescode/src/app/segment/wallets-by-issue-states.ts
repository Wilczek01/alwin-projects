import {KeyLabel} from '../shared/key-label';
import {IssueWallet} from './issue-wallet';

/**
 * Lista statusów dla których pobrane są portfele
 */
export class WalletsByIssueStates {
  /**
   * typ zlecenia
   */
  issueStates: KeyLabel[];

  /**
   * Lista portfeli dla statusów
   */
  wallets: IssueWallet[];
}

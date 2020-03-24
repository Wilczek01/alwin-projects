import {TagWallet} from './tag-wallet';
import {IssueStateWallet} from './issue-state-wallet';
import {WalletsByIssueStates} from './wallets-by-issue-states';

/**
 * Wszystkie portfele prezentowane na stronie segmentów
 */
export class AllWallets {

  /**
   * Portfele dla otwartych zleceń windykacyjnych
   */
  walletsByIssueStates: WalletsByIssueStates;

  /**
   * Portfel dla zleceń czekających na zamknięcie
   */
  waitingForTerminationIssuesWallet: IssueStateWallet;

  /**
   * Portfele dla etykiet
   */
  tagWallets: TagWallet[];
}

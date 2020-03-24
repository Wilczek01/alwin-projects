import {IssueType} from '../issues/shared/issue-type';
import {KeyLabel} from '../shared/key-label';
import {Wallet} from './wallet';

/**
 * Portfel zleceń liczony po typie zlecenia oraz segmencie klienta.
 * Portfele są liczone tylko dla podanych w dao statusów zleceń.
 * Dlatego ten sam typ zlecenia i segment klienta może mieć więcej portfeli.
 */
export class IssueWallet extends Wallet {
  /**
   * typ zlecenia
   */
  issueType: IssueType;

  /**
   * segment klienta
   */
  segment: KeyLabel;

  /**
   * suma sald otwartych zleceń w portfelu (PLN)
   */
  currentBalancePLN: number;

  /**
   * suma sald otwartych zleceń w portfelu (EUR)
   */
  currentBalanceEUR: number;

  /**
   * Czas trwania zleceń w portfelu
   */
  duration: string;

}

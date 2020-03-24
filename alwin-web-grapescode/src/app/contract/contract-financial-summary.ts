import {FinancialSummary} from './financial-summary';

/**
 * Zestawienie finansowe umowy
 */
export class ContractFinancialSummary {

  /**
   * Numer umowy
   */
  contractNo: string;

  /**
   * DPD umowy
   */
  dpd: number;

  /**
   * Czy umowa wykluczona z obsługi
   */
  excluded: boolean;

  /**
   * Zestawienie należności per waluta
   */
  currencyToSummary: Map<string, FinancialSummary>;
}

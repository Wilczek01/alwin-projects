/**
 * Portfel
 */
export class Wallet {
  /**
   * liczba klientów z otwartym zleceniem danego typu w danym segmencie
   */
  companyCount: number;

  /**
   * liczba zleceń z czynnościami do wykonania na dzień dzisiejszy (bieżące + zaległe)
   */
  currentIssueQueueCount: number;

  /**
   * suma zaangażowań otwartych zleceń w portfelu
   */
  involvement: number;

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

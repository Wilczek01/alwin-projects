/**
 * Rozrachunek faktury
 */
export class InvoiceSettlement {

  /**
   * Identyfikator rozrachunku
   */
  settlementId: number;

  /**
   * Kwota
   */
  amountSettled: number;

  /**
   * Strona
   */
  side: string;

  /**
   * Data
   */
  setDate: Date;

  /**
   * Tytuł
   */
  title: string;

}

/**
 * Rozrachunek
 */
export class Settlement {

  /**
   * identyfikator
   */
  id: number;

  /**
   * Kwota
   */
  amount: number;

  /**
   * Typ operacji (uznanie/obciążenie)
   */
  operation: string;

  /**
   * Data operacji
   */
  paymentDate: Date;
}

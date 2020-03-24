import {Invoice} from './invoice';

/**
 * Korekta faktury
 */
export class InvoiceCorrection extends Invoice {

  /**
   * Różnica kwoty brutto z poprzednią fakturą w łańcuchu korekt
   */
  previousCorrectionGrossAmountDiff: number;

  /**
   * Różnica kwoty brutto z fakturą główną
   */
  rootInvoiceGrossAmountDiff: number;
}

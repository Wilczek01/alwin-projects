/**
 * Pozycja faktury
 */
export class InvoiceEntry {

  /**
   * Numer pozycji
   */
  positionNumber: number;

  /**
   * Jednostka
   */
  quantityUnit: string;

  /**
   * Ilość
   */
  quantity: number;

  /**
   * stawka VAT
   */
  vatRate: string;

  /**
   * Wartość netto
   */
  netAmount: number;

  /**
   * VAT
   */
  vatAmount: number;

  /**
   * Nazwa pozycji
   */
  paymentTitle: string;
}

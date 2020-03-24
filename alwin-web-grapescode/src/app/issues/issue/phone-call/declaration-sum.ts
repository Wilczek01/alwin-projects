/**
 * Suma deklaracji
 */
export class DeclarationSum {
  invoicesPLNSum: number;
  invoicesEURSum: number;

  constructor(invoicesPLNSum: number, invoicesEURSum: number) {
    this.invoicesPLNSum = invoicesPLNSum;
    this.invoicesEURSum = invoicesEURSum;
  }
}

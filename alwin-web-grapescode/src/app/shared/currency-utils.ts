export class CurrencyUtils {

  private static PLN = 'PLN';
  private static EUR = 'EUR';

  /**
   * Sprawdza czy podany symbol waluty to PLN
   * @param currency - symbol waluty
   */
  static isCurrencyPLN(currency: string): boolean {
    return currency === this.PLN;
  }

  /**
   * Sprawdza czy podany symbol waluty to PLN
   * @param currency - symbol waluty
   */
  static isCurrencyEUR(currency: string): boolean {
    return currency === this.EUR;
  }
}

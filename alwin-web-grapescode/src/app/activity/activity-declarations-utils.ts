export class ActivityDeclarationsUtils {

  /**
   * Pobiera domyślną kwotę deklaracji na podstawie salda
   * @param {number} balance - saldo
   * @returns {number} domyślna kwota deklaracji
   */
  static getDefaultDeclaredPaymentAmount(balance: number) {
    if (balance > 0) {
      return 0;
    }
    return Math.abs(balance);
  }
}

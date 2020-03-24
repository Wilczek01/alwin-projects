/**
 * Opakowanie dla salda wymaganego i niewymaganego
 */
export class BalanceStartAndAdditional {

  /**
   * Saldo wymagane (do startu zlecenia)
   */
  balanceStart: number;

  /**
   * Saldo niewymagane (od startu do zakończenia zlecenia)
   */
  balanceAdditional: number;
}

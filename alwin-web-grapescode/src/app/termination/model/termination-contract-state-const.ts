/**
 * Status wypowiedzenia umowy
 */
export class TerminationContractStateConst {

  /**
   * Wypowiedznie utworzone
   */
  static NEW = 'NEW';

  /**
   * Wypowiedzenie wysłane
   */
  static ISSUED = 'ISSUED';

  /**
   * Wypowiedzenie odroczone
   */
  static POSTPONED = 'POSTPONED';

  /**
   * Wypowiedzenie odrzucone
   */
  static REJECTED = 'REJECTED';

  /**
   * Wypowiedzenie czeka na odpowiedź z eFaktury
   */
  static WAITING = 'WAITING';

  /**
   * Wypowiedzenie nie zostało pomyślnie przesłane do eFaktury
   */
  static FAILED = 'FAILED';

  /**
   * Umowa aktywowana
   */
  static CONTRACT_ACTIVATED = 'CONTRACT_ACTIVATED';

}

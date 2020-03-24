/**
 * Rezultat manulanego utworzenia wezwania do zapłaty
 */
export class CreateDemandForPaymentResultConst {
  /**
   * Utworzono nowe wezwanie
   */
  static SUCCESSFUL = 'SUCCESSFUL';

  /**
   * Wystąpił błąd systemu w trakcie tworzenia wezwania
   */
  static FAILED = 'FAILED';

  /**
   * Brak dłużnych dokumentów dla umowy
   */
  static NO_DUE_INVOICES_FOR_CONTRACT = 'NO_DUE_INVOICES_FOR_CONTRACT';

  /**
   * Umowa jest już na etapie wypowiedzeń
   */
  static CONTRACT_ALREADY_IN_TERMINATION_STAGE = 'CONTRACT_ALREADY_IN_TERMINATION_STAGE';

  /**
   * Nie znaleziono umowy
   */
  static CONTRACT_NOT_FOUND = 'CONTRACT_NOT_FOUND';

}

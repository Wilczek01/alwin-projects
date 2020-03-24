export class FinancialSummary {

  /**
   * Wymagalne: suma sald wszystkich dokumentów, dla których upłynął termin płatności
   */
  requiredPayment: number;

  /**
   * Niewymagalne: suma sald dokumentów, dla których termin płatności minie w trakcie obsługi zlecenia
   */
  notRequiredPayment: number;

  /**
   * Nadpłaty: suma dodatnich sald ze wszystkich dokumentów
   */
  overpayment: number;

  /**
   * Razem: wymagalne + niewymagalne - nadpłaty
   */
  total: number;
}

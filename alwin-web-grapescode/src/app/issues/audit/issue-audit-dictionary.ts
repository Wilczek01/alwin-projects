export class IssueAuditDictionary {

  private static FIELD_NAMES = new Map<string, string>([
    ['Issue.null', 'Zlecenie'],
    ['Issue.startDate', 'Data rozpoczecia zlecenia'],
    ['Issue.expirationDate', 'Data wygasniecia zlecenia'],
    ['Issue.terminationCause', 'Przyczyna przerwania realizacji zlecenia'],
    ['Issue.terminationDate', 'Data przerwania realizacji zlecenia'],
    ['Issue.rpbPLN', 'Kapital w PLN pozostały do spłaty w chwili utworzenia zlecenia'],
    ['Issue.balanceStartPLN', 'Saldo zlecenia w PLN w chwili rozpoczecia'],
    ['Issue.currentBalancePLN', 'Aktualne saldo dokumentów będących przedmiotem zlecenia w PLN'],
    ['Issue.paymentsPLN', 'Suma dokonanych wplat PLN'],
    ['Issue.rpbEUR', 'Kapital w EUR pozostały do spłaty w chwili utworzenia zlecenia'],
    ['Issue.balanceStartEUR', 'Saldo zlecenia w EUR w chwili rozpoczecia'],
    ['Issue.currentBalanceEUR', 'Aktualne saldo dokumentów będących przedmiotem zlecenia w EUR'],
    ['Issue.paymentsEUR', 'Suma dokonanych wplat EUR'],
    ['Issue.excludedFromStats', 'Czy zlecenie wykluczone z liczenia statystyk'],
    ['Issue.exclusionCause', 'Przyczyna wykluczenia ze statystyk'],
    ['Issue.balanceUpdateDate', 'Data ostaniej akutalizacji sald'],
    ['Issue.priority', 'Priorytet obsługi zlecenia'],
    ['Issue.totalBalanceStartPLN', 'Całkowite saldo zlecenia w chwili rozpoczęcia'],
    ['Issue.totalCurrentBalancePLN', 'Całkowite saldo dokumentów, które są przedmiotem zlecenia'],
    ['Issue.totalPaymentsPLN', 'Całkowita suma dokonanych wpłat'],
    ['Issue.issueState', 'Status zlecenia'],
    ['Issue.createdManually', 'Czy zlecenie stworzone manualnie'],
    ['Activity.null', 'Czynność windykacyjna'],
    ['Activity.state', 'Stan czynności'],
    ['Activity.activityDate', 'Data wykonania czynności'],
    ['Activity.plannedDate', 'Zaplanowana data wykonania czynności'],
    ['Activity.creationDate', 'Data utworzenia czynności'],
    ['ActivityDetail.null', 'Cecha dodatkowa czynności'],
    ['ActivityDetail.value', 'Wartość cechy dodatkowej dla czynności'],
    ['Address.null', 'Adres'],
    ['ContactDetail.null', 'Dane kontaktowe'],
    ['Declaration.null', 'Deklaracja spłaty'],
    ['Declaration.cashPaidEUR', 'Kwota dokonanej wpłaty (EUR)'],
    ['Declaration.cashPaidPLN', 'Kwota dokonanej wpłaty (PLN)'],
    ['Declaration.declarationDate', 'Data złożenia deklaracji'],
    ['Declaration.paid', 'Czy deklaracja zostałą spłacona']
  ]);

  private static CHANGE_TYPE = new Map<string, string>([
    ['VALUE_CHANGE', 'zmiana wartości'],
    ['REFERENCE_CHANGE', 'zmiana referencji'],
    ['OBJECT_REMOVED', 'usunięcie obiektu'],
    ['NEW_OBJECT', 'utworzenie obiektu']
  ]);

  private static LOG_ENTRY_VALUE = new Map<string, string>([
    ['NEW', 'Nowy'],
    ['IN_PROGRESS', 'Realizowane'],
    ['DONE', 'Zakonczone'],
    ['CANCELED', 'Anulowane'],
    ['WAITING_FOR_TERMINATION', 'Czekające na potwierdzenie zamknięcia przez managera'],
    ['PLANNED', 'Zaplanowana'],
    ['EXECUTED', 'Wykonana'],
    ['true', 'Tak'],
    ['false', 'Nie']
  ]);

  /**
   * Pobiera przetłumaczoną nazwę zmienionego pola
   *
   * @param {string} rootName - nazwa źródłowa pola
   * @returns {string} przetłumaczona nazwa pola
   */
  static getFieldName(objectName: string, fieldName: string): string {
    const keyValue = objectName + '.' + fieldName;
    if (this.FIELD_NAMES.has(keyValue)) {
      return this.FIELD_NAMES.get(keyValue);
    }
    return keyValue;
  }

  /**
   * Pobiera przetłumaczoną nazwę typu zmiany
   *
   * @param {string} rootName - nazwa źródłowa typu zmiany
   * @returns {string} przetłumaczona nazwa zmiany
   */
  static getChangeType(rootName: string): string {
    return this.CHANGE_TYPE.get(rootName);
  }

  /**
   * Pobiera przetłumaczoną nazwę zmiany
   *
   * @param {string} logEntryValue - nazwa źródłowa zmiany
   * @returns {string} przetłumaczona nazwa zmiany
   */
  static getLogEntryValue(logEntryValue: string) {
    if (this.LOG_ENTRY_VALUE.has(logEntryValue)) {
      return this.LOG_ENTRY_VALUE.get(logEntryValue);
    }
    return logEntryValue;
  }
}

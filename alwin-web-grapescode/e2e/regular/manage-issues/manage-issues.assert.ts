import {ManageIssuesPage} from '../../util/po/manage-issues.po';
import {browser, by, element} from 'protractor';

/**
 * Klasa sprawdzająca wyświetlane dane na stronie zarządzania zleceniami
 */
export class ManageIssuesAssert {

  constructor(private page: ManageIssuesPage) {
  }

  /**
   * Sprawdza wyświetlane zlecenia
   */
  expectIssuesWithTagsInTable() {
    browser.wait(() => element.all(by.tagName('mat-row')).isPresent(), 5000).then(() => {
      this.expectIssuesWithTags();

      this.expectIssue(2, true, 'Firma 9993', '10.07.2020', '10.08.2020',
        'phone', 'Nowe', '180,12 PLN\n0,00 EUR', '65 432,44 PLN\n0,00 EUR',
        '234 642,67 PLN\n0,00 EUR', '987 654,23 PLN\n0,00 EUR', '5', '36');

      this.expectTags();
    });
  }

  /**
   * Sprawdza dwa pierwsze wyświetlane zlecenia
   */
  private expectIssuesWithTags() {
    this.expectIssue(0, true, 'Nazwa Firmmy Sp. z o.o.', '10.07.2017', '10.08.2017',
      'phone', 'Nowe', '290,12 PLN\n0,00 EUR', '65 432,44 PLN\n1 230,12 EUR',
      '234 642,67 PLN\n0,00 EUR', '100 000,23 PLN\n0,00 EUR', '5', '36');

    this.expectIssue(1, true, 'Firma 9995', '10.07.2018', '10.08.2018',
      'phone', 'Nowe', '190,12 PLN\n0,00 EUR', '-65 432,44 PLN\n230,23 EUR',
      '-234 642,67 PLN\n0,00 EUR', '987 654,23 PLN\n0,00 EUR', '5', '36');
  }

  /**
   * Sprawdza wyświetlane zlecenia przefiltrowane po etykietach
   */
  expectIssuesFilteredTagsInTable() {
    this.expectIssuesWithTags();
    this.expectTags();
    expect(this.page.getTableRow(2).isPresent()).toBe(false);
  }

  /**
   * Sprawdza wyświetlane zlecenia przefiltrowane po numerach klientów
   */
  expectIssuesFilteredExtCompanyIdsInTable() {
    this.expectIssueCompany(0, 'Firma 9992');
    this.expectIssueCompany(1, 'Firma 9988');
  }

  /**
   * Sprawdza czy przełącznik do pokazywania nie zamkniętych zleceń istnieje
   */
  expectClosedIssuesToggleNotPresented() {
    expect(element(by.id('include-closed')).isPresent()).toBeFalsy();
  }

  /**
   * Sprawdza wyświetlane zlecenia z uwzględnieniem zamkniętych
   */
  expectIssuesIncludingClosed() {
    this.expectIssue(0, true, 'Nazwa Firmmy Sp. z o.o.', '10.07.2023', '10.08.2024',
      'phone', 'Zakonczone', '333 432,12 PLN\n0,00 EUR', '44 432,44 PLN\n0,00 EUR',
      '-6 142,76 PLN\n-3 142,76 EUR', '667 654,23 PLN\n0,00 EUR', '0', '0');

    this.expectIssue(1, false, 'Nazwa Firmmy Sp. z o.o.', '10.07.2023', '10.08.2024',
      'phone', 'Zakonczone', '333 432,12 PLN\n0,00 EUR', '44 432,44 PLN\n0,00 EUR',
      '-5 342,54 PLN\n-65 142,12 EUR', '667 654,23 PLN\n0,00 EUR', '0', '0');
  }

  /**
   * Sprawdza poprawność danych dla zlecenia z wiersza o podanym numerze
   * @param {number} rowNo - numer wiersza
   * @param {boolean} highPriority - czy zlecenie ma wysoki priorytet
   * @param {string} customer - nazwa klienta
   * @param {string} startDate - data rozpoczęcia zlecenia
   * @param {string} expirationDate - data zakończenia zlecenia
   * @param {string} type - nazwa ikony dla typu zlecenia
   * @param {string} state - stan zlecenia
   * @param {string} rpb - kapitał
   * @param {string} balanceStart - saldo początkowe
   * @param {string} currentBalance - saldo bieżące
   * @param {string} dpdStart - DPD utworzenia zlecenia
   * @param {string} dpdEstimated - Szacowane DPD na dzień zakończenia zlecenia
   * @param {string} payments - wpłaty
   */
  expectIssue(rowNo: number, highPriority: boolean, customer: string, extCompanyId: string, startDate: string, expirationDate: string, type: string, state: string, rpb: string,
              balanceStart: string, currentBalance: string, payments: string, dpdStart: string, dpdEstimated: string) {
    expect(this.page.getTableRowCellText(rowNo, 1)).toBe(highPriority ? 'priority_high' : '');
    expect(this.page.getTableRowCellText(rowNo, 2)).toBe(customer);
    expect(this.page.getTableRowCellText(rowNo, 3)).toBe(extCompanyId);
    expect(this.page.getTableRowCellText(rowNo, 4)).toBe(dpdStart);
    expect(this.page.getTableRowCellText(rowNo, 5)).toBe(dpdEstimated);
    expect(this.page.getTableRowCellText(rowNo, 6)).toBe(startDate);
    expect(this.page.getTableRowCellText(rowNo, 7)).toBe(expirationDate);
    expect(this.page.getTableRowCellText(rowNo, 8)).toBe(type);
    expect(this.page.getTableRowCellText(rowNo, 9)).toBe(state);
    // TODO: asercja do dodania po naprawie wyliczonego kapitału (do przesunięcia kolumny +1)
    // expect(this.page.getTableRowCellText(rowNo, 8)).toBe(rpb);
    expect(this.page.getTableRowCellText(rowNo, 10)).toBe(balanceStart);
    expect(this.page.getTableRowCellText(rowNo, 11)).toBe(currentBalance);
    expect(this.page.getTableRowCellText(rowNo, 12)).toBe(payments);
  }

  /**
   * Sprawdza czy zlecenie jest wystawione dla danego klienta
   * @param {number} rowNo - numer wiersza (od 0)
   * @param {string} customer - nazwa klienta
   */
  expectIssueCompany(rowNo: number, customer: string) {
    expect(this.page.getTableRowCellText(rowNo, 2)).toBe(customer);
  }

  /**
   * Sprawdza wyświetlane etykiety dla zleceń
   */
  expectTags() {
    this.expectTag(0, 7, 0, 'rgba(51, 51, 51, 1)');
    this.expectTag(0, 7, 1, 'rgba(153, 153, 153, 1)');
    this.expectTag(1, 7, 0, 'rgba(51, 51, 51, 1)');
  }

  /**
   * Sprawdza etykietę o podanym numerze porzadkowym w tabeli o podanym wierszu i kolumnie
   * @param {number} rowNo - numer wiersza w tabeli
   * @param {number} cellNo - numer kolumny w tabeli
   * @param {number} tagNo - numer porzadkowy etykiety
   * @param {string} color - oczekiwany kolor dla etykiety
   */
  expectTag(rowNo: number, cellNo: number, tagNo: number, color: string) {
    const row = this.page.getTableRowCells(rowNo);
    expect(this.page.getBookmarkColor(row.get(cellNo), tagNo)).toBe(color);
  }

  /**
   * Sprawdza ilość zleceń w tabeli
   * @param {number} resultCount - oczekiwana ilość zleceń
   */
  expectNumberOfIssues(resultCount: number) {
    expect(this.page.getTableRows().count()).toBe(resultCount);
  }

  /**
   * Sprawdza występowanie oznaczenia wysokiego priorytetu dla podanego wiersza
   * @param {number} rowNo - numer wiersza w tabeli
   */
  expectIssuePriorityIconExists(rowNo: number) {
    this.expectIssuePriority(rowNo, true);
  }

  /**
   * Sprawdza brak oznaczenia wysokiego priorytetu dla podanego wiersza
   * @param {number} rowNo - numer wiersza w tabeli
   */
  expectIssuePriorityIconNotExists(rowNo: number) {
    this.expectIssuePriority(rowNo, false);
  }

  /**
   * Sprawdza oznaczenie wysokiego priorytetu dla podanego wiersza
   * @param {number} rowNo - numer wiersza w tabeli
   * @param prioritySignExists - oczekiwana widoczność oznaczenia wysokiego priorytetu
   */
  expectIssuePriority(rowNo: number, prioritySignExists: boolean) {
    expect(this.page.getTableRowCellText(rowNo, 1)).toBe(prioritySignExists ? 'priority_high' : '');
  }

  /**
   * Sprawdza widoczność okna dialogowego do aktualizacji priorytetu
   */
  expectUpdatePriorityDialogVisible() {
    expect(this.page.getUpdatePriorityDialog().isPresent()).toBe(true);
  }

  /**
   * Sprawdza zaznaczenie wartości HIGH dla priorytetu w oknie dialogowym do aktualizacji priorytetu
   */
  expectHighPrioritySelectedInUpdatePriorityDialog() {
    expect(this.page.getPriorityRadioButton('HIGH').getAttribute('class')).toContain('mat-radio-checked');
  }

  /**
   * Sprawdza treść okna dialogowego z podaną wartością
   * @param {string} content - oczekiwana treść okna dialogowego
   */
  expectSecondDialogVisibleWithContent(content: string) {
    expect(element.all(by.className('mat-dialog-content')).get(1).getText()).toBe(content);
  }

  /**
   * Sprawdza oczekiwaną wartość do wyboru w filtrze
   * @param {number} idx - sprawdzana pozycja
   * @param {string} expectedLabel - oczekiwana etykieta
   */
  expectFilterValue(idx: number, expectedLabel: string) {
    expect(element.all(by.tagName('mat-option')).get(idx).getText()).toBe(expectedLabel);
  }

  /**
   * Sprawdza czy nie istnieje więcej wartości do wyboru w filtrze
   * @param {number} size - oczekiwana etykieta
   */
  expectFilterValuesSize(size: number) {
    expect(element.all(by.tagName('mat-option')).count()).toBe(size);
  }

  /**
   * Sprawdza czy na liście etykiet znajduje się tylko jedna o nazwie 'Koszyk 1' i posiada ikonę etykiety oraz przycisk usunięcia
   */
  expectIssueToBeTagged() {
    const tagChipList = this.page.getTagChipList();
    expect(tagChipList.count()).toBe(1);
    expect(tagChipList.get(0).getText()).toBe('bookmark\nKoszyk 1\ncancel');
  }

  /**
   * Dodaje nową etykietę dla zlecenia
   */
  expectIssueToTag() {
    const tagInput = this.page.getTagIssueInput();
    this.page.setValueOnInputElement(tagInput, 'koszyk 2');
    this.page.getElementByClassName('mat-option-text').click();
    const tagChipList = this.page.getTagChipList();
    expect(tagChipList.count()).toBe(2);
    expect(tagChipList.get(0).getText()).toBe('bookmark\nKoszyk 1\ncancel');
    expect(tagChipList.get(1).getText()).toBe('bookmark\nKoszyk 2\ncancel');
  }

  /**
   * Dodaje nową etykietę dla zleceń
   */
  expectIssuesToBeTagged() {
    const tagInput = this.page.getTagIssueInput();
    this.page.setValueOnInputElement(tagInput, 'koszyk 2');
    this.page.getElementByClassName('mat-option-text').click();
    const tagChipList = this.page.getTagChipList();
    expect(tagChipList.count()).toBe(1);
    expect(tagChipList.get(0).getText()).toBe('bookmark\nKoszyk 2\ncancel');
  }

  /**
   * Ignoruje dodanie etkiety, która została już dodana
   */
  expectSameTagsNumberAfterAddingAlreadyAddedTag() {
    const tagChipListBeforeAdd = this.page.getTagChipList();
    expect(tagChipListBeforeAdd.count()).toBe(2);
    const tagInput = this.page.getTagIssueInput();
    tagInput.sendKeys('koszyk 1');
    this.page.getElementByClassName('mat-option-text').click();
    const tagChipListAfterAdd = this.page.getTagChipList();
    expect(tagChipListAfterAdd.count()).toBe(2);
  }

  /**
   * Weryfikuje czy przycisk wyszukiwania zleceń jest nieaktywny
   */
  expectSearchButtonDisabled() {
    expect(element(by.id('filter-managed-issues')).isEnabled()).toBe(false, 'Przycisk wyszukiwania zleceń jest aktywny');
  }

  /**
   * Weryfikuje czy wyświetlone zostały filtry dla operatora
   */
  expectIssueFiltersForPhoneDebtCollector() {
    this.expectIssueFilters(true);
  }

  /**
   * Weryfikuje czy filtry dla managera nie zostały wyświeltone
   */
  doNotExpectIssueFiltersForPhoneDebtCollectorManager() {
    this.expectIssueFilters(false);
  }


  expectIssueFilters(expectFilters: boolean) {
    expect(element(by.name('companyNip')).isPresent()).toBe(expectFilters);
    expect(element(by.name('companyRegon')).isPresent()).toBe(expectFilters);
    expect(element(by.name('companyName')).isPresent()).toBe(expectFilters);
    expect(element(by.name('contactPhone')).isPresent()).toBe(expectFilters);
    expect(element(by.name('contactEmail')).isPresent()).toBe(expectFilters);
    expect(element(by.name('contactName')).isPresent()).toBe(expectFilters);
    expect(element(by.name('personName')).isPresent()).toBe(expectFilters);
    expect(element(by.name('personPesel')).isPresent()).toBe(expectFilters);
    expect(element(by.name('invoiceContractNo')).isPresent()).toBe(expectFilters);
  }
}

import {SchedulersConfiguration} from '../../../util/po/schedulers-configuration.po';
import {browser, by, element} from 'protractor';

export class SchedulersConfigurationAssert {

  constructor(private page: SchedulersConfiguration) {
  }

  /**
   * Sprawdza poprawność danych w tabeli konfiguracji zadań cyklicznych
   */
  expectSchedulersConfigurationsInTable() {
    this.expectSchedulersConfigurations();
    this.expectSchedulerExecutionConfiguration(12, 'Aktualizacja sald, otwarcie nowych oraz zamknięcie przeterminowanych zleceń. ' +
      'Przetwarzanie przeterminowanych dokumentów i wysłanie raportu email. Aktualizacja firm: dane adresowe i kontaktowe, zaangażowanie firm. ' +
      'Wysłanie raportu o nierozwiązanych zleceniach. Utworzenie czynności windykacyjnych typu \'telefon wychodzący\'. Utworzenie nowych wezwań do zapłaty ' +
      'oraz wypowiedzeń umów. Aktualizacja sald deklaracji spłat', '3:00 mode_edit', '22.05.2018 12:00');
  }

  expectSchedulersConfigurations() {
    this.expectSchedulerExecutionConfiguration(0, 'Uaktualnienie sald otwartych zleceń windykacyjnych i powiązanych z nimi faktur oraz zamknięcie przeterminowanych zleceń i w razie potrzeby utworzenie nowych z odpowiednim typem', '-', '-');
    this.expectSchedulerExecutionConfiguration(1, 'Uaktualnienie sald otwartych zleceń windykacyjnych oraz powiązanych z nimi faktur', '-', '-');
    this.expectSchedulerExecutionConfiguration(2, 'Utworzenie nowych zleceń windykacyjnych', '-', '-');
    this.expectSchedulerExecutionConfiguration(3, 'Zamknięcie przeterminowanych zleceń i w razie potrzeby utworzenie nowych z odpowiednim typem', '-', '-');
    this.expectSchedulerExecutionConfiguration(4, 'Zebranie listy przeterminowanych dokumentów i wysłanie raportu e-mailem do managera', '-', '-');
    this.expectSchedulerExecutionConfiguration(5, 'Uaktualnienie danych firm', '-', '-');
    this.expectSchedulerExecutionConfiguration(6, 'Uaktualnienie zaangażowania firm', '-', '-');
    this.expectSchedulerExecutionConfiguration(7, 'Zebranie listy nierozwiązanych zleceń i wysłanie raportu e-mailem do managera', '-', '-');
    this.expectSchedulerExecutionConfiguration(8, 'Tworzenie czynności windykacyjnych typu \'telefon wychodzący\'', '-', '-');
    this.expectSchedulerExecutionConfiguration(9, 'Oznaczanie zaległych zleceń predefiniowaną etykietą', '-', '-');
    this.expectSchedulerExecutionConfiguration(10, 'Aktualizacja sald deklaracji spłat', '-', '-');
    this.expectSchedulerExecutionConfiguration(11, 'Utworzenie nowych wezwań do zapłaty oraz wypowiedzeń umów', '-', '-');
  }

  /**
   * Sprawdza czy dane konfiguracji zadań cyklicznych są zgodne z przekazanymi
   * @param {number} rowNo - numer wiersza
   * @param {string} label - opis zadania
   * @param {string} startDate - ustawiona godzina startu zadania
   * @param {string} lastUpdated - data ostatniej zmiany godziny startu zadania
   */
  private expectSchedulerExecutionConfiguration(rowNo: number, label: string, startDate: string, lastUpdated: string) {
    expect(this.page.getTableRowCellText(rowNo, 0)).toBe(label);
    expect(this.page.getTableRowCellText(rowNo, 1)).toBe(startDate);
    expect(this.page.getTableRowCellText(rowNo, 2)).toBe(lastUpdated);
  }

  /**
   * Sprawdza czy został wyświetlony dialog potwierdzający start zadania
   *
   * @param {string} message - treść pytania w dialogu potwierdzającym start
   */
  expectConfirmationDialogVisible(message: string) {
    browser.waitForAngular();
    expect(this.page.getElementById('mat-dialog-title-0').getText()).toBe('Uruchomienie zadania');
    expect(this.page.getElementByClassName('mat-dialog-content').getText()).toBe(message);
  }

  /**
   * Sprawdza czy został wyświetlony dialog edycji godziny startu godziny i opisie procesu
   *
   * @param {string} processLabel - opis zadania którego dotyczy dialog zmiany godziny
   */
  expectSchedulerEditDialogVisible(processLabel: string) {
    browser.waitForAngular();
    expect(this.page.getElementById('dialog-title').getText()).toBe('Zmień godzinę wykonania zadania');
    expect(this.page.getElementByClassName('process-label').getText()).toBe(processLabel);
  }

  expectExecutionStartHour(hour: number) {
    browser.waitForAngular();
    const dialog = this.page.getElementByClassName('mat-dialog-container');
    expect(dialog.element(by.className('mat-input-element')).getAttribute('value')).toBe(`${hour}`);
  }

  issueaPaginatorLabel() {
    return element(by.className('mat-paginator-range-label'));
  }

  expectIssuesWasCreated() {
    expect(this.issueaPaginatorLabel().getText()).toBe('1 - 6 z 6');
  }
}

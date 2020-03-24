import {ConfigurationPage} from '../../util/po/configuration.po';

export class SchedulersAssert {

  constructor(private page: ConfigurationPage) {
  }

  /**
   * Sprawdza poprawność danych w tabeli uruchomień zadań cyklicznych
   */
  expectSchedulersExecutionsInTable() {
    this.expectSchedulerExecution(0, '09.05.2018 05:15', 'trwa', null, '', 'Uaktualnienie zaangażowania firm', 'Nie');
    this.expectSchedulerExecution(1, '09.05.2018 05:00', '09.05.2018 07:35', '2h 35m 40s', 'To zadanie wykonało się z bardzo długim komentarzem, ' +
      'np. została podana jakaś bardzo długa nazwa wyjątku: javax.resource.ResourceException: Unable to get managed connection',
      'Uaktualnienie sald otwartych zleceń windykacyjnych oraz powiązanych z nimi faktur', 'Tak');
  }

  /**
   * Sprawdza czy dane uruchomienia zadania cyklicznego w wierszu o podanym numerze są zgoodne z przekazanymi
   * @param {number} rowNo - numer wiersza
   * @param {string} startDate - data rozpoczęcia
   * @param {string} endDate - data zakończenia
   * @param {string} duration - czas trwania (jeśli podany null parametr będzie zignorowany)
   * @param {string} state - stan, dodatkowy komentarz przy zakończeniu wykonania błędem
   * @param {string} type - typ uruchomienia
   * @param {string} manual - czy uruchomienie ręczne
   */
  private expectSchedulerExecution(rowNo: number, startDate: string, endDate: string, duration: string, state: string, type: string, manual: string) {
    expect(this.page.getTableRowCellText(rowNo, 0)).toBe(startDate);
    expect(this.page.getTableRowCellText(rowNo, 1)).toBe(endDate);
    if (duration) {
      expect(this.page.getTableRowCellText(rowNo, 2)).toBe(duration);
    }
    expect(this.page.getTableRowCellText(rowNo, 3)).toBe(state);
    expect(this.page.getTableRowCellText(rowNo, 4)).toBe(type);
    expect(this.page.getTableRowCellText(rowNo, 5)).toBe(manual);
  }
}

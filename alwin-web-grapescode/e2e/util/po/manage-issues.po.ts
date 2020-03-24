import {AbstractPage} from '../abstract.po';
import {by, element, ElementFinder} from 'protractor';

export class ManageIssuesPage extends AbstractPage {

  /**
   * Zwraca kolor wybranej etykiety z podanej komórki
   *
   * @param {ElementFinder} td - komórka w tabeli
   * @param {number} no - numer etykiety
   * @returns {any}
   */
  getBookmarkColor(td: ElementFinder, no: number) {
    return this.getBookmark(td, no).getCssValue('color');
  }

  /**
   * Zwraca wybraną etykietę z podanej komórki
   *
   * @param {ElementFinder} td - komórka w tabeli
   * @param {number} no - numer etykiety
   * @returns {ElementFinder} wybrana etykieta
   */
  private getBookmark(td: ElementFinder, no: number) {
    return td.all(by.className('mat-icon')).get(no);
  }

  /**
   * Przełącza przycisk decydujacy o tym czy zwracać równiez zamknięte zlecenia
   */
  includeClosed() {
    this.extendFiltersPanel();
    this.slideToggle('include-closed');
  }

  /**
   * Rozwija panel z filtrami
   */
  extendFiltersPanel() {
    this.clickElementById('mat-expansion-panel-header-0');
  }

  /**
   * Zwraca dialog zmiany priorytetu
   * @returns {ElementFinder}
   */
  getUpdatePriorityDialog() {
    return element(by.tagName('alwin-update-priority-dialog'));
  }

  /**
   * Zwraca dialog przypisywania etykiety do zlecenia
   * @returns {ElementFinder}
   */
  getUpdateTagsDialog() {
    return element(by.tagName('issue-tags-dialog'));
  }

  /**
   * Zwraca RadioButton z dialogu do zmiany priorytetów dla podanego identyfikatora
   * @param {string} id - identyfikator priorytetu np. 'NORMAL'
   * @returns {ElementFinder}
   */
  getPriorityRadioButton(id: string) {
    return this.getUpdatePriorityDialog().$(`#${id}`);
  }

  /**
   * Wykonuje kliknięcie w menu w tabeli zleceń
   */
  clickIssuesTableMenu() {
    element(by.css('.table-header .mat-icon-button')).click();
  }

  /**
   * Klika w przycisk akceptacji w dialogu do zmiany priorytetów
   */
  clickAcceptButtonOnPriorityDialog() {
    this.clickElementWithWait(this.getUpdatePriorityDialog().all(by.className('update-button')).get(0));
  }

  /**
   * Klika w przycisk akceptacji w dialogu do dodania etykiety
   */
  clickUpdateTags() {
    this.clickElementWithWait(element(by.className('create-button')));
  }

  /**
   * Otwiera dialog dla zmiany priorytetu dla konkretnego zlecenia
   */
  openChangePriorityForIssue(issueId: number) {
    this.clickElementWithWait(element(by.id(`issue-${issueId}-more-actions-button`)));
    element(by.id(`change-issue-priority-button-${issueId}`)).click();
  }

  /**
   * Otwiera dialog dla zmiany priorytetów dla wszystkich zleceń w tabeli
   */
  openUpdateForAllDialog() {
    this.clickIssuesTableMenu();
    this.clickElementById('update-priority-for-all-issues');
  }

  /**
   * Otwiera dialog dla zmiany priorytetów dla wybranych z tabeli zleceń
   */
  openUpdateForSelectedDialog() {
    this.clickIssuesTableMenu();
    this.clickElementById('update-priority-for-issues');
  }

  /**
   * Otwiera dialog do przypisania etykiet dla wszystkich zleceń z tabeli
   */
  openUpdateTagsForAllDialog() {
    this.clickIssuesTableMenu();
    this.clickElementById('update-tags-for-all-issues');
  }

  /**
   * Otwiera dialog do przypisania etykiet dla wybranych zleceń z tabeli
   */
  openUpdateTagsForSelectedDialog() {
    this.clickIssuesTableMenu();
    this.clickElementById('update-tags-for-issues');
  }

  /**
   * Zaznacza wybraną przez parametr priorityKey opcję w oknie do zmiany priorytetów
   * @param {string} priorityKey - określa która opcja ma zostać wybrana
   */
  selectPriorityInUpdatePriorityDialog(priorityKey: string) {
    this.clickElementById(`${priorityKey}-input`);
  }

  /**
   * Wykonuje kliknięcie w checkbox o podanym numerze
   * @param {number} number - numer nadany przez framework
   */
  clickCheckbox(number: number) {
    this.clickElementWithWait(element(by.id(`issue-${number}`)));
  }

  /**
   * Zaznacza wiesz w tabeli zleceń
   * @param {number} idx - index wiersza do zaznaczenia
   */
  selectIssueInTable(idx: number) {
    this.clickElementWithWait(element.all(by.css('mat-cell.cdk-column-select')).get(idx));
  }

  /**
   * Rozwija listę filtrów
   */
  expandFilters() {
    this.clickElementWithWait(element(by.tagName('mat-expansion-panel-header')));
  }

  /**
   * Otwiera listę wyboru priorytetu zlecenia do filtrowania
   */
  clickIssuePriorityFilterSelector() {
    this.clickElementById('issue-priority-filter');
  }

  /**
   * Otwiera listę wyboru segmentów klienta do filtrowania
   */
  clickSegmentFilterSelector() {
    this.clickElementById('segment-filter');
  }

  /**
   * Otwiera listę wyboru typu zlecenia do filtrowania
   */
  clickIssueTypeFilterSelector() {
    this.clickElementById('issue-type-filter');
  }

  /**
   * Otwiera dialog dla zarządzania etykietami dla konkretnego zlecenia
   */
  openTagIssue(issueId: number) {
    this.clickElementWithWait(element(by.id(`issue-${issueId}-more-actions-button`)));
    this.clickElementById(`tag-issue-button-${issueId}`);
  }

  /**
   * Pobiera listę etykiet w oknie zarządzania etykietami dodanych dla konkretnego zlecenia
   *
   * @returns {ElementFinder} lista etykiet
   */
  getTagChipList() {
    return element.all(by.tagName('mat-chip'));
  }

  /**
   * Pobiera pole tekstowe do dodawania etykiet dla zlecenia
   *
   * @returns {any} pole tekstowe
   */
  getTagIssueInput() {
    return element(by.id('tag-issue-input'));
  }

  /**
   * Pobiera pole tekstowe "numery klientów"
   * @returns {ElementFinder} numery klientów
   */
  getExtCompanyIdsInput() {
    return element(by.id('extCompanyIds'));
  }

  /**
   * Filtruje zlecenia po etykietach
   */
  filterIssuesByTags() {
    this.getElementByClassName('mat-expansion-panel-header-title').click();
    this.clickElementById('issue-tag-filter');
    this.clickElementById('issue-tag-filter-option-1');
    this.clickElementById('filter-managed-issues');
  }

  /**
   * Filtruje zlecenia po numerach klientów
   */
  filterIssuesByExtCompanyIds() {
    this.expandFilterPanelAndPutValueIntoExtCompanyIdsInput('9992,9988,9982');
    this.clickElementById('filter-managed-issues');
  }

  /**
   * Wprowadza wartość "numery klientów" w formularzu filtrowania zleceń
   */
  expandFilterPanelAndPutValueIntoExtCompanyIdsInput(extCompanyIds: string) {
    this.getElementByClassName('mat-expansion-panel-header-title').click();
    this.setValueOnInputElement(this.getExtCompanyIdsInput(), extCompanyIds);
  }

  clickOnIssueFilters() {
    this.getElementByClassName('mat-expansion-panel-header-title').click();
  }

  /**
   * Sprawdza wybrane wartości w selektach (formularz do filtrowania zleceń)
   */
  expectDefaultFiltersSelected() {
    const selectedOptions = this.getSelectedOptionsText();
    expect(selectedOptions.get(0).getText()).toBe('Nowe, Realizowane');
    expect(selectedOptions.get(1).getText()).toBe('Windykacja telefoniczna sekcja 1');
    expect(selectedOptions.get(2).getText()).toBe('A');
  }

  /**
   * Sprawdza zaznaczone tagi w selektach (formularz do filtrowania zleceń)
   */
  expectDefaultTagFilterSelected() {
    const selectedOptions = this.getSelectedOptionsText();
    expect(selectedOptions.get(0).getText()).toBe('Koszyk 2');
  }

  /**
   * Sprawdza zaznaczone statusy zlecenia w selektach (formularz do filtrowania zleceń)
   */
  expectDefaultIssueStateFilterSelected() {
    const selectedOptions = this.getSelectedOptionsText();
    expect(selectedOptions.get(0).getText()).toBe('Czekające na zamknięcie');
  }

  /**
   * Pobieranie wszystkich wybranych wartość w selektach na stronie
   */
  private getSelectedOptionsText() {
    return element.all(by.className('mat-select-value-text'));
  }
}

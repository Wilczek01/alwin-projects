import {AbstractPage} from '../abstract.po';
import {by, element} from 'protractor';

export class ConfigurationPage extends AbstractPage {

  clickIssueTypeConfigurationTab() {
    element(by.id('mat-tab-label-0-2')).click();
  }

  clickActivityTypeConfigurationTab() {
    element(by.id('mat-tab-label-0-3')).click();
  }

  clickDefaultActivityConfigurationTab() {
    element(by.id('mat-tab-label-0-4')).click();
  }

  clickSecondIssueTypeTab() {
    element(by.id('mat-tab-label-1-1')).click();
  }

  editExistingIssueTypeConfiguration() {
    this.clickElementWithWait(element(by.id('edit-configuration-1')));
    this.expectIssueTypeConfigurationToEdit();
    element(by.id('update-configuration-button')).click();
  }

  private expectIssueTypeConfigurationToEdit() {
    expect(element(by.name('issueType')).getAttribute('value')).toBe('Windykacja telefoniczna sekcja 2');
    expect(element(by.name('duration')).getAttribute('value')).toBe('17');
    expect(element(by.name('segment')).getAttribute('value')).toBe('A');
    expect(element(by.name('createAutomatically')).getText()).toBe('Tak');
    expect(element(by.name('dpdStart')).getAttribute('value')).toBe('16');
    expect(element(by.name('minBalanceStart')).getAttribute('value')).toBe('-100');
    expect(element(by.name('operatorType')).getText()).toBe('Administrator systemu, Windykator telefoniczny sekcja 2');
  }

  editExistingIssueTypeActivityConfiguration() {
    this.clickElementWithWait(element(by.id('edit-configuration-1')));
    this.expectIssueTypeActivityConfigurationToEdit();
    element(by.id('update-activity-type-button')).click();
  }

  private expectIssueTypeActivityConfigurationToEdit() {
    expect(element(by.name('name')).getAttribute('value')).toBe('Wezwanie do zapłaty (podstawowe)');
    expect(element(by.name('canBePlanned')).getText()).toBe('Nie');
    expect(element(by.name('chargeMin')).getAttribute('value')).toBe('50');
    expect(element(by.name('chargeMax')).getAttribute('value')).toBe('203.33');
    expect(element(by.name('mayBeAutomated')).getText()).toBe('Tak');
    expect(element(by.name('mayHaveDeclaration')).getText()).toBe('Nie');
    expect(element(by.name('specific')).getText()).toBe('Tak');
    expect(element(by.name('customerContact')).getText()).toBe('Nie');
    expect(element(by.name('activityDetailProperty')).getText()).toBe('Termin zapłaty, Typ wezwania (podstawowe)');
  }

  editDefaultActivityConfiguration() {
    this.clickElementWithWait(element(by.id('edit-configuration-1')));
    this.expectDefaultActivityConfigurationToEdit();
    element(by.id('update-default-activity-button')).click();
  }

  private expectDefaultActivityConfigurationToEdit() {
    expect(element(by.name('defaultActivityNameLabel')).getAttribute('value')).toBe('Wezwanie do zapłaty (podstawowe)');
    expect(element(by.name('defaultDay')).getAttribute('value')).toBe('3');
    expect(element(by.name('version')).getAttribute('value')).toBe('2');
    expect(element(by.name('creatingDate')).getAttribute('value')).toBe('10.07.2017');
    expect(element(by.name('defaultExecutionDate')).getAttribute('value')).toBe('15.12.2012');
  }

  /**
   * Zwraca listę wszystkich rekordów w tabeli konfiguracji
   * @returns {ElementArrayFinder}
   */
  getConfigurationsTableRows(tableClassName) {
    return element.all(by.className(tableClassName)).get(0).all(by.tagName('tr'));
  }

  /**
   * Zwraca listę wszystkich wartości(kolumn) dla rekordu o podanym numerze z tabeli konfiguracji
   *
   * @param {number} no - numer rekordu
   * @returns {ElementArrayFinder}
   */
  getConfigurationsTableRow(tableClassName: string, no: number) {
    return this.getConfigurationsTableRows(tableClassName).get(no).all(by.tagName('td'));
  }
}


import {by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';

export class MyWorkActivitiesPage extends AbstractPage {

  openActivitiesTab() {
    this.clickElementWithWait(element(by.id('mat-tab-label-0-2')));
  }

  switchToPlannedActivities() {
    this.clickElementWithWait(element(by.id('planned-activities-radio')));
  }

  expectIssueExecutedActivities() {
    this.expectExecutedIssueActivity(201, '10.07.2017 00:00', '10.07.2017', 'Adam Mickiewicz', 'Telefon wychodzący', 'b/d', '-');
  }

  expectIssuePlannedActivities() {
    this.expectPlannedIssueActivity(100, '05.07.2017 00:00', '10.07.2017', 'Adam Mickiewicz', 'Telefon wychodzący', 'To jest Komentarz', 'deklaracja niespłacona');
    this.expectPlannedIssueActivity(11, '10.07.2017 00:00', '14.07.2017', 'Adam Mickiewicz', 'Telefon wychodzący', 'b/d', '-');
    this.expectPlannedIssueActivity(12, '10.07.2017 00:00', '21.08.2017', 'Adam Mickiewicz', 'Wezwanie do zapłaty (podstawowe)', 'b/d', '-');
    this.expectPlannedIssueActivity(13, '10.07.2017 00:00', '21.08.2017', 'Juliusz Słowacki', 'Telefon wychodzący', 'b/d', '-');
    this.expectPlannedIssueActivity(14, '10.07.2017 00:00', '25.08.2017', 'Adam Mickiewicz', 'Telefon wychodzący', 'b/d', '-');
  }

  expectPlannedIssueActivity(id: number, creationDate: string, plannedDate: string, operator: string, activityType: string, remark: string, summary: string) {
    expect(this.getTextFromElementById(`plannedDate-column-${id}`)).toBe(plannedDate);
    this.expectIssueActivity(id, creationDate, operator, activityType, remark, summary);
  }

  expectExecutedIssueActivity(id: number, creationDate: string, activityDate: string, operator: string, activityType: string, remark: string, summary: string) {
    expect(this.getTextFromElementById(`activityDate-column-${id}`)).toBe(activityDate);
    this.expectIssueActivity(id, creationDate, operator, activityType, remark, summary);
  }

  expectIssueActivity(id: number, creationDate: string, operator: string, activityType: string, remark: string, summary: string) {
    expect(this.getTextFromElementById(`creationDate-column-${id}`)).toBe(creationDate);
    expect(this.getTextFromElementById(`operator-column-${id}`)).toBe(operator);
    expect(this.getTextFromElementById(`activityType-column-${id}`)).toBe(activityType);
    expect(this.getTextFromElementById(`remark-column-${id}`)).toBe(remark);
    expect(this.getTextFromElementById(`summary-column-${id}`)).toBe(summary);
  }

  createNewPlannedActivity() {
    this.clickElementWithWait(element(by.id('create-new-activity-button')));
    this.clickElementWithWait(element(by.id('planned-activity-checkbox')));
    this.checkPlannedActivityTypes();
    this.setPlannedActivityData('Telefon wychodzący',
      'Bardzo grzeczny klient, polecam!',
      '10.07.2017');
    this.clickElementById('save-activity-button');
  }

  clickFailedActionButton() {
    this.clickElementById('failed-action-button');
  }

  createNewExecutedActivity() {
    this.clickElementWithWait(element(by.id('create-new-activity-button')));
    this.checkExecutedActivityTypes();
    this.setActivityData('Telefon wychodzący',
      'Bardzo grzeczny klient, polecam!',
      '9.07.2017');
    this.clickElementById('save-activity-button');
  }

  prepareActivityDialogWithOneDeclaration() {
    this.clickElementWithWait(element(by.id('create-new-activity-button')));
    this.checkExecutedActivityTypes();
    this.setActivityData('Telefon wychodzący',
      'Bardzo grzeczny klient, polecam!',
      '9.07.2017');
    this.clickElementWithWait(element(by.id('add-declaration-to-activity')));

  }

  prepareActivityDialog() {
    this.clickElementWithWait(element(by.id('create-new-activity-button')));
    this.checkExecutedActivityTypes();
    this.setActivityData('Telefon wychodzący',
      'Bardzo grzeczny klient, polecam!',
      '9.07.2017');

  }

  setPlannedActivityData(activityType: string, remark: string, plannedDate: string) {
    element(by.name('activityType')).sendKeys(activityType);
    element(by.id('remark')).sendKeys(remark);
    element(by.name('plannedDate')).sendKeys(plannedDate);
  }

  setActivityData(activityType: string, remark: string, activityDate: string) {
    element(by.name('activityType')).sendKeys(activityType);
    element(by.id('remark')).sendKeys(remark);
    element(by.name('activityDate')).sendKeys(activityDate);
  }

  editExistingPlannedActivity() {
    this.clickElementWithWait(element(by.id('edit-activity-100-button')));
    this.expectPlannedActivityToEdit('Telefon wychodzący',
      'To jest Komentarz',
      'Zaplanowana',
      '10.7.2017',
      '10.32',
      'fv1a/2017');
    this.checkIfActivityTypeIsDisabled();
    element(by.id('update-activity-button')).click();
  }

  expectPlannedActivityToEdit(activityType: string, remark: string, status: string, plannedDate: string,
                              charge: string, invoiceId: string) {
    expect(element(by.name('activityType')).getText()).toBe(activityType);
    expect(element(by.id('remark')).getAttribute('value')).toBe(remark);
    expect(element(by.name('activityState')).getText()).toBe(status);
    expect(element(by.name('plannedDate')).getAttribute('value')).toBe(plannedDate);
    expect(element(by.name('charge')).getAttribute('value')).toBe(charge);
    expect(element(by.name('invoiceId')).getAttribute('value')).toBe(invoiceId);
  }

  private checkIfActivityTypeIsDisabled() {
    expect(element(by.name('activityType')).getAttribute('disabled')).toBe('true');
  }

  checkPlannedActivityTypes() {
    element(by.name('activityType')).click();
    expect(element.all(by.tagName('mat-option')).count()).toBe(1);
    expect(element.all(by.tagName('mat-option')).get(0).getText()).toBe('Telefon wychodzący');
  }

  checkExecutedActivityTypes() {
    element(by.name('activityType')).click();
    expect(element.all(by.tagName('mat-option')).count()).toBe(4);
    expect(element.all(by.tagName('mat-option')).get(0).getText()).toBe('Telefon wychodzący');
    expect(element.all(by.tagName('mat-option')).get(1).getText()).toBe('Wezwanie do zapłaty (podstawowe)');
    expect(element.all(by.tagName('mat-option')).get(2).getText()).toBe('Wezwanie do zapłaty (ostateczne)');
    expect(element.all(by.tagName('mat-option')).get(3).getText()).toBe('Nieudana próba kontaktu tel.');
  }

  expectLimitedDeclarationDate() {
    expect(element(by.css('[name=declarationDatePicker]')).getAttribute('min')).toBe('2012-12-11T00:00:00+01:00');
  }

  expectFailedActionDialogVisible() {
    expect(element(by.css('[name="activityType"]')).getText()).toBe('Nieudana próba kontaktu tel.');
    expect(element(by.id('remark')).getAttribute('value')).toBe('Bardzo grzeczny klient, polecam!');
  }

}

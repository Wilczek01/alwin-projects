import {by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';

export class ManageCompanyPage extends AbstractPage {

  createIssueDialog() {
    return element(by.id('create-issue-dialog'));
  }

  companyIdInput() {
    return element(by.name('companyId'));
  }

  companyNameInput() {
    return element(by.name('companyName'));
  }

  searchButton() {
    return element(by.id('company-search-button'));
  }

  companyTableRows() {
    return element.all(by.className('mat-row'));
  }

  operatorList() {
    return element(by.name('operatorId'));
  }

  companyData() {
    return element(by.id('company-data'));
  }

  companyTableFirstRowIdCell() {
    return this.companyTableRows().get(0).element(by.className('mat-column-id'));
  }

  companyTableFirstRowCreateIssueButton() {
    return this.companyTableRows().get(0).element(by.tagName('button'));
  }

  companyTableFirstRowManageCompanyLink() {
    return this.companyTableRows().get(0).element(by.tagName('a'));
  }

  createIssueButton() {
    return element(by.className('create-issue-dialog-button'));
  }

  expectCompanyExclusions() {
    this.expectCompanyExclusion(1, '11.07.2017', '20.07.2017', 'Adam Mickiewicz', 'blocking reason 1', 'blocking remark 1');
    this.expectCompanyExclusion(2, '15.07.2017', '23.07.2017', 'Adam Mickiewicz', 'blocking reason 2', 'blocking remark 2');
    this.expectCompanyExclusion(3, '25.07.2017', '27.07.2017', 'Adam Mickiewicz', 'blocking reason 3', 'blocking remark 3');
  }

  expectCompanyExclusion(number: number, from: string, to: string, author: string, reason: string, comment: string) {
    const exclusion = element.all(by.className('block-table')).get(0).all(by.tagName('tr')).get(number);
    const exclusionRow = exclusion.all(by.tagName('td'));
    expect(exclusionRow.get(0).getText()).toBe(from);
    expect(exclusionRow.get(1).getText()).toBe(to);
    expect(exclusionRow.get(2).getText()).toBe(author);
    expect(exclusionRow.get(3).getText()).toBe(reason);
    expect(exclusionRow.get(4).getText()).toBe(comment);
  }

  expectActiveContract() {
    const contract = element.all(by.className('active-contract-row')).get(0).all(by.tagName('td'));
    expect(contract.get(0).getText()).toBe('10');
    expect(contract.get(1).getText()).toBe('23.08.2016');
    expect(contract.get(2).getText()).toBe('Zbiorcza zbiorcza');
    expect(contract.get(3).getText()).toBe('Wykup z regresem');
    expect(contract.get(4).getText()).toBe('Leasing operacyjny');
    expect(contract.get(5).getText()).toBe('1 000,23');
    expect(contract.get(6).getText()).toBe('Polski zloty');
    const exclusionsButton = contract.get(7).all(by.tagName('button')).get(1);
    exclusionsButton.click();
    this.expectContractExclusion(1, '11.07.2017', '20.07.2017', 'Adam Mickiewicz', 'blocking reason 1', 'comment example 1');
    this.expectContractExclusion(2, '11.07.2018', '20.07.2018', 'Adam Mickiewicz', 'blocking reason 4', 'comment example 4');
  }

  expectContractExclusion(number: number, from: string, to: string, author: string, reason: string, comment: string) {
    const exclusion = element(by.tagName('alwin-contract-exclusions-dialog')).all(by.tagName('tr')).get(number);
    const exclusionRow = exclusion.all(by.tagName('td'));
    expect(exclusionRow.get(0).getText()).toBe(from);
    expect(exclusionRow.get(1).getText()).toBe(to);
    expect(exclusionRow.get(2).getText()).toBe(author);
    expect(exclusionRow.get(3).getText()).toBe(reason);
    expect(exclusionRow.get(4).getText()).toBe(comment);
  }

  expectInactiveContract() {
    const contract = element.all(by.className('inactive-contract-row')).get(0).all(by.tagName('td'));
    expect(contract.get(0).getText()).toBe('100');
    expect(contract.get(1).getText()).toBe('22.08.2016');
    expect(contract.get(2).getText()).toBe('Zakonczona aktywna');
    expect(contract.get(3).getText()).toBe('Finansowanie własne');
    expect(contract.get(4).getText()).toBe('Leasing finasowy');
    expect(contract.get(5).getText()).toBe('435 000,15');
    expect(contract.get(6).getText()).toBe('EUR NBP sredni');
    const exclusionsButton = contract.get(7).all(by.tagName('button')).get(1);
    expect(exclusionsButton.getAttribute('disabled')).toBe('true');
  }

  expectCompanyExclusionToBeUpdated() {
    this.getFirstCompanyExclusionEditButton().click();
    expect(element(by.name('startDate')).getAttribute('value')).toBe('11.7.2017');
    expect(element(by.name('endDate')).getAttribute('value')).toBe('20.7.2017');
    expect(element(by.name('reason')).getAttribute('value')).toBe('blocking reason 1');
    expect(element(by.name('remark')).getAttribute('value')).toBe('blocking remark 1');
    element(by.id('update-exclusion-button')).click();
  }

  expectCompanyExclusionToBeAdded() {
    element(by.id('create-new-block-button')).click();
    element(by.name('startDate')).sendKeys('11.7.2017');
    element(by.name('endDate')).sendKeys('20.7.2017');
    element(by.name('reason')).sendKeys('blocking reason 1');
    element(by.name('remark')).sendKeys('blocking remark 1');
    element(by.id('save-exclusion-button')).click();
  }

  expectCompanyContractExclusionToBeAdded() {
    this.getActiveContractAddExclusionButton().click();
    element(by.name('startDate')).sendKeys('11.7.2017');
    element(by.name('endDate')).sendKeys('20.7.2017');
    element(by.name('reason')).sendKeys('blocking reason 1');
    element(by.name('remark')).sendKeys('blocking remark 1');
    element(by.id('save-exclusion-button')).click();
  }

  expectCompanyContractExclusionToBeUpdated() {
    this.getActiveContractExclusionsButton().click();
    this.expectFirstCompanyContractExclusionToBeUpdated();
  }

  expectFirstCompanyContractExclusionToBeUpdated() {
    this.getFirstCompanyContractExclusionEditButton().click();
    expect(element(by.name('startDate')).getAttribute('value')).toBe('11.7.2017');
    expect(element(by.name('endDate')).getAttribute('value')).toBe('20.7.2017');
    expect(element(by.name('reason')).getAttribute('value')).toBe('blocking reason 1');
    expect(element(by.name('remark')).getAttribute('value')).toBe('comment example 1');
    element(by.id('update-exclusion-button')).click();
  }

  getActiveContractExclusionsButton() {
    const contract = element.all(by.className('active-contract-row')).get(0).all(by.tagName('td'));
    return contract.get(7).all(by.tagName('button')).get(1);
  }

  getActiveContractAddExclusionButton() {
    const contract = element.all(by.className('active-contract-row')).get(0).all(by.tagName('td'));
    return contract.get(7).all(by.tagName('button')).get(0);
  }

  expectCompanyExclusionToBeDeleted() {
    this.getFirstCompanyExlusionDeleteButton().click();
    this.confirm();
  }

  expectCompanyContractExclusionToBeDeleted() {
    this.getActiveContractExclusionsButton().click();
    this.getFirstCompanyContractExclusionDeleteButton().click();
    this.confirm();
  }

  private confirm() {
    expect(element(by.tagName('alwin-confirmation-dialog')).isPresent()).toBe(true);
    element(by.id('confirmation-dialog-confirm-button')).click();
  }

  private getFirstCompanyExclusionEditButton() {
    return this.getFirstCompanyExclusionActionRow().all(by.tagName('button')).get(0);
  }

  private getFirstCompanyContractExclusionEditButton() {
    return this.getFirstCompanyContractExclusionActionRow().all(by.tagName('button')).get(0);
  }

  private getFirstCompanyContractExclusionDeleteButton() {
    return this.getFirstCompanyContractExclusionActionRow().all(by.tagName('button')).get(1);
  }

  private getFirstCompanyExlusionDeleteButton() {
    return this.getFirstCompanyExclusionActionRow().all(by.tagName('button')).get(1);
  }

  private getFirstCompanyExclusionActionRow() {
    const exclusion = element.all(by.className('block-table')).get(0).all(by.tagName('tr')).get(1);
    return exclusion.all(by.tagName('td')).get(5);
  }

  private getFirstCompanyContractExclusionActionRow() {
    const exclusion = element.all(by.id('contract-exclusions-table')).get(0).all(by.tagName('tr')).get(1);
    return exclusion.all(by.tagName('td')).get(5);
  }

  issueTypeSelect() {
    return element.all(by.name('issueTypeId'));
  }

  findCompanyById(extCompanyId: number) {
    this.companyIdInput().sendKeys(extCompanyId);
    this.searchButton().click();
  }

  findCompanyByName(extCompanyName: string) {
    this.companyNameInput().sendKeys(extCompanyName);
    this.searchButton().click();
  }

  clickCreateIssueButtonOnCompanyList() {
    this.companyTableFirstRowCreateIssueButton().click();
  }

  clickCompanyDetailsLinkOnCompanyList() {
    this.companyTableFirstRowManageCompanyLink().click();
  }

  chooseIssueTypePhoneDebtCollection1() {
    this.chooseFromSelect('issueTypeId', 0);
  }

  chooseIssueTypeDirectDebtCollection() {
    this.chooseFromSelect('issueTypeId', 2);
  }

  clickCreateIssueButton() {
    this.createIssueButton().click();
  }

  expectNumberOfCompanySearchResult(resultCount: number) {
    expect(this.companyTableRows().count()).toBe(resultCount);
  }

  expectCompanyWithIdFound(extCompanyId: number) {
    expect(this.companyTableFirstRowIdCell().getText()).toBe(`${extCompanyId}`);
  }

  expectToSeeCreateIssueDialog() {
    expect(this.createIssueDialog().isPresent()).toBeTruthy();
  }

  expectOperatorListPresented() {
    expect(this.operatorList().isPresent()).toBeTruthy();
  }

  expectOperatorListNotPresented() {
    expect(this.operatorList().isPresent()).toBeFalsy();
  }

  expectCompanyDataToBePresented() {
    expect(this.companyData().isPresent()).toBeTruthy();
  }
}

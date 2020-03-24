import {by, element} from 'protractor';
import {LoginUtilPage} from '../login-util.po';

export class MyIssuesPage {

  utilPage = new LoginUtilPage();

  issuesTableRows() {
    return element.all(by.className('mat-row'));
  }

  firstIssueDetailsButton() {
    return this.issuesTableRows().get(0).element(by.name('issueDetails'));
  }

  clickFirstIssueDetailsButton() {
    this.firstIssueDetailsButton().click();
  }

  expectNumberOfIssues(resultCount: number) {
    expect(this.issuesTableRows().count()).toBe(resultCount);
  }

  expectRowToBeMarkedAsPriority(rowIndex: number, priorityExpected: boolean) {
    expect(this.issuesTableRows().get(rowIndex).element(by.className('issue-priority')).getText()).toBe(priorityExpected ? 'priority_high' : '');
  }

}

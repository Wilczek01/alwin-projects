import {AbstractPage} from '../abstract.po';
import {by, element} from 'protractor';

export class MyWorkIssueTermiantionPage extends AbstractPage {

  clickTerminateIssueIcon() {
    element(by.id('terminate-issue-icon')).click();
  }

  fillIssueTerminationComment(terminationComment: string) {
    element(by.id('issue-termination-comment')).sendKeys(terminationComment);
  }

  clickAcceptIssueTerminationButton() {
    this.clickElementById('accept-button');
  }

  exceptIssueTerminationNoPermissionMessage() {
    expect(element(by.id('issue-termination-error-message')).getText()).toBe('Operator nie ma uprawnień do zarządzania żądaniami o przeterminowane' +
      ' zakończenie zlecenia');
  }
}

import {AbstractPage} from '../abstract.po';
import {browser, by, element} from 'protractor';
import {UsersTestData} from '../../critical/data/users.test-data';

export class UsersPage extends AbstractPage {

  clickResetPassword(id: number) {
    element(by.id(`reset-password-${id}`)).click();
  }

  confirmResetPassword() {
    this.clickElementById('confirmation-dialog-confirm-button');
  }

  clickEditExpandedUser() {
    this.clickElementById(`edit-user-button-${UsersTestData.CREATED_MANAGER_ID}`);
  }

  clickAddOperatorButton() {
    const button = element(by.id('add-new-operator-button'));
    this.clickElementWithWait(button);
  }

  clickUpdateUserDataButton() {
    const button = element(by.id('update-user-data-button'));
    expect(button.getAttribute('disabled')).toBeFalsy('Update user button is disabled!');
    this.clickElementWithWait(button);

    browser.getCurrentUrl();
  }

  expandSecondUserOnTheList() {
    const expandedRow = element(by.id(`user-row-${UsersTestData.CREATED_MANAGER_ID}`));
    this.clickElementWithWait(expandedRow);
  }

  clickAddUserButton() {
    this.clickElementById('add-user-button');
  }

  clickCreateUserButton() {
    this.clickElementById('create-user-button');
  }

  fillNewUserData() {
    element(by.name('firstName')).sendKeys(UsersTestData.CREATED_MANAGER_FIRST_NAME);
    element(by.name('lastName')).sendKeys(UsersTestData.CREATED_MANAGER_LAST_NAME);
    element(by.name('phoneNumber')).sendKeys(UsersTestData.CREATED_MANAGER_PHONE_NO);
    element(by.name('email')).sendKeys(UsersTestData.CREATED_MANAGER_EMAIL);
  }

  fillNewOperatorData() {
    element(by.id('login-0')).sendKeys(UsersTestData.CREATED_MANAGER_LOGIN);
    element(by.id('password-0')).sendKeys(UsersTestData.CREATED_MANAGER_PASSWORD);
    element(by.id('active-0')).click();
    this.chooseFromSelectById('operatorType-0', 6);
  }

  async getNewPasswordFromConfirmationDialog() {
    const dialogSpanText = await element(by.css('.mat-dialog-content span')).getText();
    return dialogSpanText.replace('UWAGA: zamierzasz zmienić hasło operatora na ', '');
  }

}

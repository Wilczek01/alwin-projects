import {by, element} from 'protractor';

export class PasswordPage {

  private newPasswordInput() {
    return element(by.name('newPassword'));
  }

  private newPasswordRepeatedInput() {
    return element(by.name('newPasswordRepeated'));
  }

  changePasswordButton() {
    return element(by.id('change-password-button'));
  }

  changePasswordTo(newPassword: string, repeatedPassword: string) {
    this.newPasswordInput().sendKeys(newPassword);
    this.newPasswordRepeatedInput().sendKeys(repeatedPassword);
  }
}

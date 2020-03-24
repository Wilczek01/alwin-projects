import {browser, by, element} from 'protractor';

export class LoginPage {

  loginBox() {
    return element(by.css('.login-box'));
  }

  loginInput() {
    return element(by.name('username'));
  }

  passwordInput() {
    return element(by.name('password'));
  }

  loginButton() {
    return element(by.id('login-button'));
  }

  loginUser(login: string, password: string) {
    this.loginInput().sendKeys(login);
    this.passwordInput().sendKeys(password);
    this.loginButton().click();
  }

  loggedInUser(login: string, password: string) {
    browser.get('login');
    this.loginUser(login, password);
    this.expectUserToBeLoggedIn();
  }

  expectUserToBeLoggedIn() {
    this.expectNotToSeeLoginBox();
  }

  expectNotToSeeLoginBox() {
    expect(this.loginBox().isPresent()).toBe(false, 'user should be logged in');
  }

  expectToSeeLoginBox() {
    expect(this.loginBox().isPresent()).toBe(true, 'user should not be logged in');
  }
}

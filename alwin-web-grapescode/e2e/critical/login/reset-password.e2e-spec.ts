import {UsersPage} from '../../util/po/users.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {UsersAssert} from '../assertions/users.assert';
import {UsersTestData} from '../data/users.test-data';
import {PasswordPage} from '../../util/po/password.po';

class TestState {
  newPassword: string;
}

describe(' ', () => {
  const testState: TestState = new TestState();
  let userPage: UsersPage;
  let util: LoginUtilPage;
  let passwordPage: PasswordPage;
  let usersAssert;

  beforeAll(() => {
    userPage = new UsersPage();
    util = new LoginUtilPage();
    passwordPage = new PasswordPage();
    usersAssert = new UsersAssert(userPage);
  });

  it('admin should be able to log in and reset users password', () => {
    util.navigation.navigateToLogin();
    util.loginPage.loginUser(UsersTestData.META_OPERATOR_NAME, UsersTestData.META_OPERATOR_PASSWORD);
    util.loginPage.expectUserToBeLoggedIn();
    util.expectUrlToBe(util.navigation.DASHBOARD, 'not redirected to dashboard');

    util.navigation.navigateToUsers();
    util.expectUrlInQaModeToBe(util.navigation.USERS, 'not redirected to users');

    userPage.expandSecondUserOnTheList();
    userPage.clickEditExpandedUser();
    util.expectUrlToBe(`${util.navigation.USERS}/${UsersTestData.CREATED_MANAGER_ID}`, `not redirected to edit user ${UsersTestData.CREATED_MANAGER_ID}`);

    userPage.clickResetPassword(+UsersTestData.CREATED_MANAGER_ID);
    userPage.getNewPasswordFromConfirmationDialog().then(newPassword => {
      testState.newPassword = newPassword;
    });
    userPage.confirmResetPassword();

    util.logout();
  });

  it('user should be able to login with new password and sets his own', () => {
    util.navigation.navigateToLogin();
    util.loginPage.loginUser(UsersTestData.USER_WITH_CHANGED_PASSWORD_LOGIN, testState.newPassword);
    util.loginPage.expectUserToBeLoggedIn();
    util.expectUrlToBe(util.navigation.CHANGE_PASSWORD, 'not redirected to change password');

    passwordPage.changePasswordTo(UsersTestData.USER_WITH_CHANGED_PASSWORD_DEFAULT_PASSWORD, UsersTestData.USER_WITH_CHANGED_PASSWORD_DEFAULT_PASSWORD);
    passwordPage.changePasswordButton().click();
    util.loginPage.expectUserToBeLoggedIn();
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');

    util.navigation.navigateToLogin();
    util.loginPage.loginUser(UsersTestData.USER_WITH_CHANGED_PASSWORD_LOGIN, UsersTestData.USER_WITH_CHANGED_PASSWORD_DEFAULT_PASSWORD);
    util.loginPage.expectUserToBeLoggedIn();
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');
  });

});

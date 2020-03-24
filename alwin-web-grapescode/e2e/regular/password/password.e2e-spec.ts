import {Navigation} from '../../util/navigation.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {PasswordPage} from '../../util/po/password.po';

describe('change password page', () => {
  let util: LoginUtilPage;
  let navigation: Navigation;
  let page: PasswordPage;

  beforeEach(() => {
    util = new LoginUtilPage();
    navigation = new Navigation();
    navigation.navigateToLogin();
    page = new PasswordPage();
  });

  it('should display change password form if operator is forced to change it', () => {
    // when
    util.loggedInAdminForcedToChangePassword();

    // then
    util.expectUrlToBe(util.navigation.CHANGE_PASSWORD, 'not redirected to change password');
  });

  it('should change operator password and redirect to default page', () => {
    // given
    util.loggedInAdminForcedToChangePassword();

    // when
    page.changePasswordTo('test', 'test');

    // and
    page.changePasswordButton().click();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD, 'not redirected to change password');
  });

  it('should not change operator password', () => {
    // given
    util.loggedInAdminForcedToChangePassword();

    // when
    page.changePasswordTo('test', 'test2');

    // then
    expect(page.changePasswordButton().getAttribute('disabled')).toBe('true');
  });

  it('should redirect to change password form if operator is forced to change it', () => {
    // given
    util.loggedInAdminForcedToChangePassword();

    // when
    navigation.navigateToHolidays();

    // then
    util.expectUrlToBe(util.navigation.CHANGE_PASSWORD, 'not redirected to change password');
  });


});

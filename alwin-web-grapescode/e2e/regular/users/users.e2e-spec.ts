import {LoginUtilPage} from '../../util/login-util.po';
import {UsersPage} from '../../util/po/users.po';

describe('Users page', () => {

  let page: UsersPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new UsersPage();
    util = new LoginUtilPage();
  });

  it('should be accessible for admin', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToUsers();

    // then
    util.expectUrlInQaModeToBe(util.navigation.USERS, 'not redirected to users');
  });

  it('should not be accessible for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToUsers();

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to dashboard');
  });

  it('should not be accessible for phone debt collector to see details', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToUserDetails(1);

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to dashboard');
  });

  it('should reset operator password', () => {
    // given
    util.loggedInAdmin();

    // and
    util.navigation.navigateToUserDetails(1);

    // when
    page.clickResetPassword(1);

    // then
    page.confirmResetPassword();
  });

});

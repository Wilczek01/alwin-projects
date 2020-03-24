import {MyWorkPage} from '../../util/po/my-work.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('my work notifications page', () => {
  let page: MyWorkPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new MyWorkPage();
    util = new LoginUtilPage();
  });

  it('should present notifications with details', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToMyWork();

    // then
    page.openNotifications();
    page.expectUnreadNotification();
    page.expectReadNotification();
  });

  it('should refresh notifications after reading one of them', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToMyWork();
    page.openNotifications();
    page.markAsRead();
  });

  it('should not refresh notifications after canceling reading one of them', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToMyWork();
    page.openNotifications();
    page.cancelAsReading();
  });

  it('should present issue audit details', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToMyWork();

    // then
    page.openAuditDialog();
    page.expectAuditDetails();
  });
});

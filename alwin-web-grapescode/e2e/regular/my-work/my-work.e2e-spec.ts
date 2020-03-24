import {MyWorkPage} from '../../util/po/my-work.po';
import {MyWorkCustomerPage} from '../../util/po/my-work-customer.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {MyWorkPhoneCallPage} from '../../util/po/my-work-phone-call.po';

describe('my work page', () => {
  let page: MyWorkPage;
  let customerPage: MyWorkCustomerPage;
  let phoneCallPage: MyWorkPhoneCallPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new MyWorkPage();
    util = new LoginUtilPage();
    customerPage = new MyWorkCustomerPage();
    phoneCallPage = new MyWorkPhoneCallPage();
  });

  it('should not be accessible if logged in user not in phone debt collector role', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToMyWork();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD, 'not on dashboard URL');
  });

  it('should load dedicated issue for logged in user', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToMyWork();

    // then
    expect(page.issueDataCard().isPresent()).toBe(true);
    page.expectIssueHeader('Nowe', '09.01.2030');
    page.expectTotalPaymentsInIssueHeader('-3 000,00', '-80,00', '-6 142,76', '-120,00', '0,00', '20,00', '2017-12-15 08:45:11');
    customerPage.expectBasicCustomerData('(20.12.Z) Produkcja barwników i pigmentów', 'Maria Konopnicka');
    customerPage.expectAddresses(false);
    customerPage.expectContacts();
    phoneCallPage.expectEmptyPhoneCall();
  });

  it('should display no data if no dedicated issue was found for logged in user', () => {
    // given
    util.loggedInPhoneDebtCollectorWithoutWork();

    // when
    util.navigation.navigateToMyWork();

    // then
    page.expectNoIssueData();
  });

});

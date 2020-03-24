import {MyWorkCustomerPage} from '../../util/po/my-work-customer.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {MyWorkPhoneCallPage} from '../../util/po/my-work-phone-call.po';

describe('my work persons page', () => {
  let customerPage: MyWorkCustomerPage;
  let phoneCallPage: MyWorkPhoneCallPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    util = new LoginUtilPage();
    customerPage = new MyWorkCustomerPage();
    phoneCallPage = new MyWorkPhoneCallPage();
  });

  it('should display person details', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openPersonTab();

    // then
    customerPage.expectDefaultPersonDetails();
  });

  it('should display next person details', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openPersonTab();
    customerPage.clickPersonSecondRow();
    customerPage.expectSecondRowPersonDetails();
  });

  it('should start phone call', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    customerPage.openPersonTab();

    // when
    phoneCallPage.startPhoneCall();

    // then
    phoneCallPage.expectPhoneCall();
  });

  it('should display contacts for person', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openPersonTab();

    // then
    customerPage.expectDefaultPersonDetails();
    customerPage.expectContacts();
  });

  it('should add new contact for person', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openPersonTab();

    // then
    customerPage.createNewContact();
  });

  it('should update existing contact for person', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openPersonTab();

    // then
    customerPage.editExistingContact();
  });

  it('should send sms to person', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openPersonTab();

    // then
    customerPage.sendSms();
  });

});

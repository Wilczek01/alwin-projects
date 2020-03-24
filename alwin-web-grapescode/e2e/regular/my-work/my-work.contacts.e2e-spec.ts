import {MyWorkCustomerPage} from '../../util/po/my-work-customer.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('my work contacts page', () => {
  let customerPage: MyWorkCustomerPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    util = new LoginUtilPage();
    customerPage = new MyWorkCustomerPage();
  });

  it('should add new contact', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openContactTab();
    customerPage.createNewContact();
  });

  it('should update existing contact', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openContactTab();
    customerPage.editExistingContact();
  });

  it('should send sms to textable contact', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openContactTab();
    customerPage.sendSms();
  });

  it('should end call and then open failed attempt to contact action with phone number and comment', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openContactTab();
    customerPage.startCall();
    customerPage.writeCommentInCall();
    customerPage.endCall();
    customerPage.clickFailedActionButton();

    // then
    customerPage.expectFailedActionDialogVisible();
  });

});

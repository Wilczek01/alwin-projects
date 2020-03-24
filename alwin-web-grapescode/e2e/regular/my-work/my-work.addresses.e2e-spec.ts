import {MyWorkCustomerPage} from '../../util/po/my-work-customer.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('my work addresses page', () => {
  let customerPage: MyWorkCustomerPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    util = new LoginUtilPage();
    customerPage = new MyWorkCustomerPage();
  });

  it('should add new address', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openAddressTab();
    customerPage.createNewAddress();
  });

  it('should update existing address', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    customerPage.openAddressTab();
    customerPage.editExistingAddress();
  });

});

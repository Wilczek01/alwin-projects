import {LoginUtilPage} from '../../util/login-util.po';
import {ManageCompanyPage} from '../../util/po/manage-company.po';

describe('Error report', () => {

  let page: ManageCompanyPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new ManageCompanyPage();
    util = new LoginUtilPage();
  });

  it('should present snack bar with error report button when server error occurs', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // when (company name "error" results in server error response)
    page.findCompanyByName('error');

    // then
    page.expectSnackBarWithErrorReportButtonToBePresented();
  });

  it('should present error report dialog with errorId when server error occurs', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // and (company name "error" results in server error response)
    page.findCompanyByName('error');

    // when
    page.clickErrorReportButton();

    // then
    page.expectErrorReportDialogWithErrorIdToBePresented('acbcb5e4-89ee-4971-a29f-d95d9a9d2662');
  });

});

import {LoginUtilPage} from '../../util/login-util.po';
import {ManageCompanyPage} from '../../util/po/manage-company.po';

describe('create issue page', () => {

  let page: ManageCompanyPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new ManageCompanyPage();
    util = new LoginUtilPage();
  });

  it('should not be accessible if logged in user not in phone debt collector role', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToManagingCompanies();

    // then
    page.expectUrlToBe(util.navigation.DASHBOARD, 'not on dashboard URL');
  });

  it('should find companies for issue creation', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // when
    page.findCompanyById(10);

    // then
    page.expectNumberOfCompanySearchResult(1);

    // and
    page.expectCompanyWithIdFound(10);
  });

  it('should open create issue dialog', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // and
    page.findCompanyById(10);

    // when
    page.clickCreateIssueButtonOnCompanyList();

    // then
    page.expectToSeeCreateIssueDialog();
  });

  it('should create new issue', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // and
    page.findCompanyById(10);

    // and
    page.clickCreateIssueButtonOnCompanyList();

    // and
    page.chooseIssueTypePhoneDebtCollection1();

    // when
    page.clickCreateIssueButton();

    // then should redirect to issue page (new issue id is 100)
    util.expectUrlToBe('issue/100', 'not redirected to new issue page');
  });

  it('should present operators to assign for debt collection 1 issue type', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // and
    page.findCompanyById(10);

    // and
    page.clickCreateIssueButtonOnCompanyList();

    // when
    page.chooseIssueTypePhoneDebtCollection1();

    // then
    page.expectOperatorListPresented();
  });

  it('should not present operators to assign for direct debt collection issue type', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagingCompanies();

    // and
    page.findCompanyById(10);

    // and
    page.clickCreateIssueButtonOnCompanyList();

    // when
    page.chooseIssueTypeDirectDebtCollection();

    // then
    page.expectOperatorListNotPresented();
  });
});

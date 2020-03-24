import {UsersPage} from '../../util/po/users.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {UsersAssert} from '../assertions/users.assert';
import {UsersTestData} from '../data/users.test-data';
import {ManageCompanyPage} from '../../util/po/manage-company.po';
import {MyWorkPage} from '../../util/po/my-work.po';
import {MyWorkCustomerPage} from '../../util/po/my-work-customer.po';

describe(' ', () => {
  let page: ManageCompanyPage;
  let myWorkPage: MyWorkPage;
  let myWorkCustomerPage: MyWorkCustomerPage;
  let userPage: UsersPage;
  let util: LoginUtilPage;
  let usersAssert;

  beforeAll(() => {
    userPage = new UsersPage();
    util = new LoginUtilPage();
    usersAssert = new UsersAssert(userPage);
    page = new ManageCompanyPage();
    myWorkPage = new MyWorkPage();
    myWorkCustomerPage = new MyWorkCustomerPage();
  });

  it('manager should find company by id, create new issue and check schedules', () => {
    util.navigation.navigateToLogin();

    util.loginPage.loginUser(UsersTestData.CREATED_MANAGER_LOGIN, UsersTestData.CREATED_MANAGER_PASSWORD);

    util.loginPage.expectUserToBeLoggedIn();

    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');

    // TODO: dodać asercję na sprawdzenie strony z segmentami (dane, linki)

    util.navigation.navigateToManagingCompanies();

    util.expectUrlInQaModeToBe(util.navigation.MANAGED_COMPANIES, 'not redirected to manage companies');

    // TODO: dodać asercję na sprawdzenie formularza do wyszukiwania

    page.findCompanyById(5060);

    page.clickCreateIssueButtonOnCompanyList();

    page.expectToSeeCreateIssueDialog();

    page.chooseIssueTypePhoneDebtCollection1();

    page.expectOperatorListNotPresented();

    page.clickCreateIssueButton();

    util.expectUrlToBeWithTimeout('issue/20106', 'not redirected to new issue page', 10000);

    // TODO: dodać asercje na sprawdzenie stworzonego zlecenia

    myWorkCustomerPage.openContractTab();
    myWorkCustomerPage.clickContractSchedule(4173);
    myWorkCustomerPage.expectSchedule2474730();
  });
});

import {LoginUtilPage} from '../../../util/login-util.po';
import {UsersTestData} from '../../data/users.test-data';
import {MyWorkPage} from '../../../util/po/my-work.po';
import {MyWorkCustomerPage} from '../../../util/po/my-work-customer.po';
import {SchedulersConfiguration} from '../../../util/po/schedulers-configuration.po';
import {SchedulersConfigurationAssert} from '../../../regular/configuration/scheduler/schedulers-configuration.assert';

describe(' ', () => {
  let page: SchedulersConfiguration;
  let myWorkPage: MyWorkPage;
  let myWorkCustomerPage: MyWorkCustomerPage;
  let util: LoginUtilPage;
  let assert: SchedulersConfigurationAssert;

  beforeAll(() => {
    util = new LoginUtilPage();
    page = new SchedulersConfiguration();
    assert = new SchedulersConfigurationAssert(page);
    myWorkPage = new MyWorkPage();
    myWorkCustomerPage = new MyWorkCustomerPage();
  });

  it('admin should run creating issue scheduler and check created issues', () => {
    util.navigation.navigateToLogin();

    util.loginPage.loginUser(UsersTestData.META_OPERATOR_NAME, UsersTestData.META_OPERATOR_PASSWORD);

    util.loginPage.expectUserToBeLoggedIn();

    util.expectUrlToBe(util.navigation.DASHBOARD, 'not redirected to dashboard');

    // TODO: dodać asercję na sprawdzenie strony ze zleceniami (dane, filtrowanie )

    util.navigation.navigateToConfiguration();

    util.expectUrlInQaModeToBe(util.navigation.CONFIGURATION, 'not redirected to configuration');

    page.openSchedulerConfigurationTab();
    assert.expectSchedulersConfigurations();
    page.clickStartProcessButton('GENERATE_ISSUES');
    assert.expectConfirmationDialogVisible('Utworzenie nowych zleceń windykacyjnych\nCzy na pewno chcesz uruchomić?');
    page.confirmConfirmationDialog();

    // TODO: dokładniejsze sprawdzenie stworzonych zleceń
    // page.waitForFinishedCreatingIssueScheduler();
    // util.navigation.navigateToDashboard();
    // util.expectUrlInQaModeToBe(util.navigation.DASHBOARD, 'not redirected to dashboard');
    // assert.expectIssuesWasCreated();

  });
});

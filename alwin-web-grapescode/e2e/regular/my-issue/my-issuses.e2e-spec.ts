import {MyIssuesPage} from '../../util/po/my-issuses.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('my issues page', () => {

  let page: MyIssuesPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new MyIssuesPage();
    util = new LoginUtilPage();
  });

  it('should present current phone debt collector issues', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToMyIssues();

    // then
    page.expectNumberOfIssues(2);
    page.expectRowToBeMarkedAsPriority(0, true);
    page.expectRowToBeMarkedAsPriority(1, false);

  });

  it('should present issue details after click', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // and
    util.navigation.navigateToMyIssues();

    // when
    page.clickFirstIssueDetailsButton();

    // then
    util.expectNewTabWithUrl('/issue/19', 'issue details page not opened');
  });

});

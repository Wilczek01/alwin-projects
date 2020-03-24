import {LoginUtilPage} from '../../util/login-util.po';
import {MyWorkIssueTermiantionPage} from '../../util/po/my-work.issue-termination.po';

describe('my work issue termination page', () => {

  let page: MyWorkIssueTermiantionPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new MyWorkIssueTermiantionPage();
    util = new LoginUtilPage();
  });

  it('should present message for user not permitted to manage issue termination', () => {
    // given
    util.loggedInPhoneDebtCollectorWithoutPermissionToManageIssueTermination();

    // and
    util.navigation.navigateToMyWork();

    // when
    page.clickTerminateIssueIcon();
    page.fillIssueTerminationComment('czas to zakończyć raz na zawsze');
    page.clickAcceptIssueTerminationButton();

    // then

    page.exceptIssueTerminationNoPermissionMessage();
  });
});

import {SegmentsPage} from '../../util/po/segments.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('segments page', () => {

  let page: SegmentsPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new SegmentsPage();
    util = new LoginUtilPage();
  });

  it('should display wallets for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToSegments();

    // then
    page.expectWallets();
  });

  it('should display wallets for phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToSegments();

    // then
    page.expectWalletsForManager();
  });

  it('should open link with set filters on issue page', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToSegments();
    page.navigateToIssuesPage();

    // then
    util.expectUrlToBe(util.navigation.MANAGED_ISSUES_WITH_FILTERS, 'not redirected to managed issue page');
  });

  it('should open link with set tag filter on issue page', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToSegments();
    page.navigateToTagIssuesPage();

    // then
    util.expectUrlToBe(util.navigation.MANAGED_ISSUES_WITH_TAG_FILTER, 'not redirected to managed issue page');
  });

  it('should open link with set status filter on issue page', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToSegments();
    page.navigateToStateIssuesPage();

    // then
    util.expectUrlToBe(util.navigation.MANAGED_ISSUES_WITH_STATE_FILTER, 'not redirected to managed issue page');
  });

});

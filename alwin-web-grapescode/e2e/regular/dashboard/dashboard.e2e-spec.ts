import {LoginUtilPage} from '../../util/login-util.po';

describe('dashboard page', () => {
  let util: LoginUtilPage;

  beforeEach(() => {
    util = new LoginUtilPage();
  });

  it('should not be accessible if logged in user is phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToDashboard();

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not on segments URL');
  });

  it('should present dashboard placeholder for restructuringSpecialist', () => {
    // when
    util.loggedInRestructuringSpecialist();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD_PLACEHOLDER, 'dashboard-placeholder not presented');
  });

  it('should present dashboard placeholder for renunciationCoordinator', () => {
    // when
    util.loggedInRenunciationCoordinator();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD_PLACEHOLDER, 'dashboard-placeholder not presented');
  });

  it('should present dashboard placeholder for securitySpecialist', () => {
    // when
    util.loggedInSecuritySpecialist();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD_PLACEHOLDER, 'dashboard-placeholder not presented');
  });

  it('should present dashboard placeholder for analyst', () => {
    // when
    util.loggedInAnalyst();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD_PLACEHOLDER, 'dashboard-placeholder not presented');
  });

  it('should present dashboard placeholder for departmentManager', () => {
    // when
    util.loggedInDepartmentManager();

    // then
    util.expectUrlToBe(util.navigation.DASHBOARD_PLACEHOLDER, 'dashboard-placeholder not presented');
  });

  it('should present logged in user and his role description', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToDashboard();

    // then
    expect(util.getLoggedInUserAndRoleLabel()).toBe('Adam Mickiewicz | Administrator systemu');
  });
});

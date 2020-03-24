import {MyWorkActivitiesPage} from '../../util/po/my-work-activities.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('my work activities page', () => {
  let activitiesPage: MyWorkActivitiesPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    util = new LoginUtilPage();
    activitiesPage = new MyWorkActivitiesPage();
  });

  it('should load issue executed activities by default', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();

    // then
    activitiesPage.expectIssueExecutedActivities();
  });

  it('should load issue planned activities', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();
    activitiesPage.switchToPlannedActivities();

    // then
    activitiesPage.expectIssuePlannedActivities();
  });

  it('should add new planned activity', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();
    activitiesPage.createNewPlannedActivity();
  });

  it('should update planned existing activity', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();
    activitiesPage.switchToPlannedActivities();

    // then
    activitiesPage.editExistingPlannedActivity();
  });

  it('should add new activity', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();
    activitiesPage.createNewExecutedActivity();
  });

  it('should declaration date picker should have min value as previous working day', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();
    activitiesPage.prepareActivityDialogWithOneDeclaration();

    // then
    activitiesPage.expectLimitedDeclarationDate();
  });

  it('should switch to dialog with selected failed attempt to contact action', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    activitiesPage.openActivitiesTab();
    activitiesPage.prepareActivityDialog();
    activitiesPage.clickFailedActionButton();

    // then
    activitiesPage.expectFailedActionDialogVisible();
  });

});

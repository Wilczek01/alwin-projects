import {LoginUtilPage} from '../../util/login-util.po';
import {ManageIssuesAssert} from './manage-issues.assert';
import {ManageIssuesPage} from '../../util/po/manage-issues.po';

describe('Manage issues page', () => {

  let page: ManageIssuesPage;
  let assert: ManageIssuesAssert;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new ManageIssuesPage();
    assert = new ManageIssuesAssert(page);
    util = new LoginUtilPage();
  });

  it('should be accessible for phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // then
    util.expectUrlInQaModeToBe(util.navigation.MANAGED_ISSUES, 'not redirected to managed issues');
  });

  it('should not be accessible for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToManagedIssues();

    // then
    util.expectUrlToBe(util.navigation.SEGMENTS, 'not redirected to segments');
  });

  it('should display tags in managed issues table', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // then
    assert.expectIssuesWithTagsInTable();
  });

  it('should display issues including closed in managed issues table ', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssues();

    // when
    page.includeClosed();

    // then
    assert.expectIssuesIncludingClosed();
  });

  it('should display closed issues when admin is logged in', () => {
    // given
    util.loggedInAdmin();

    // when
    util.navigation.navigateToDashboard();

    // then
    assert.expectIssuesIncludingClosed();

    // and
    page.extendFiltersPanel();
    assert.expectClosedIssuesToggleNotPresented();

    // and
    page.clickIssueTypeFilterSelector();

    assert.expectFilterValuesSize(9);
    assert.expectFilterValue(0, 'Wszystkie');
    assert.expectFilterValue(1, 'Windykacja telefoniczna sekcja 1');
    assert.expectFilterValue(2, 'Windykacja telefoniczna sekcja 2');
    assert.expectFilterValue(3, 'Windykacja bezpośrednia');
    assert.expectFilterValue(4, 'Windykacja prawna - pozew o wydanie przedmiotu');
    assert.expectFilterValue(5, 'Transport przedmiotu');
    assert.expectFilterValue(6, 'Realizacja zabezpieczenia');
    assert.expectFilterValue(7, 'Windykacja prawna - pozew o zapłatę');
    assert.expectFilterValue(8, 'Restrukturyzacja');
  });

  it('should load issues for phone debt collector manager with priority marked', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // then
    assert.expectNumberOfIssues(4);
    assert.expectIssuePriorityIconExists(0);
    assert.expectIssuePriorityIconExists(1);
    assert.expectIssuePriorityIconExists(2);
    assert.expectIssuePriorityIconNotExists(3);
  });

  it('should show change priority dialog', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.openChangePriorityForIssue(1);

    // then
    assert.expectUpdatePriorityDialogVisible();
    assert.expectHighPrioritySelectedInUpdatePriorityDialog();
  });

  it('should ask before executing change priority for all', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.openUpdateForAllDialog();

    // and
    page.selectPriorityInUpdatePriorityDialog('NORMAL');

    // and
    page.clickAcceptButtonOnPriorityDialog();

    // then
    assert.expectSecondDialogVisibleWithContent('Zamierzasz ustawić priorytet Normalny dla 19 zleceń. Czy kontynuować?');
  });

  it('should ask before executing change priority for selected issues', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.selectIssueInTable(0);
    page.selectIssueInTable(1);

    // and
    page.openUpdateForSelectedDialog();

    // and
    page.selectPriorityInUpdatePriorityDialog('NORMAL');

    // and
    page.clickAcceptButtonOnPriorityDialog();

    // then
    assert.expectSecondDialogVisibleWithContent('Zamierzasz ustawić priorytet Normalny dla 2 zleceń. Czy kontynuować?');
  });

  it('should list only issues with high priority', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.expandFilters();

    // and
    page.clickIssuePriorityFilterSelector();

    // then
    assert.expectFilterValue(0, 'Wszystkie');
    assert.expectFilterValue(1, 'Normalny');
    assert.expectFilterValue(2, 'Wysoki');
  });

  it('should list issues segment filters', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.expandFilters();

    // and
    page.clickSegmentFilterSelector();

    // then
    assert.expectFilterValue(0, 'Wszystkie');
    assert.expectFilterValue(1, 'A');
    assert.expectFilterValue(2, 'B');
  });

  it('should list issues types filters', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.expandFilters();

    // and
    page.clickIssueTypeFilterSelector();

    // then
    assert.expectFilterValuesSize(2);
    assert.expectFilterValue(0, 'Wszystkie');
    assert.expectFilterValue(1, 'Windykacja telefoniczna sekcja 1');
  });

  it('should tag issue', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssues();

    // when
    page.openTagIssue(2);

    // then
    assert.expectIssueToBeTagged();
    assert.expectIssueToTag();
    assert.expectSameTagsNumberAfterAddingAlreadyAddedTag();
  });

  it('should display issues filtered by tags', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssues();

    // when
    page.filterIssuesByTags();

    // then
    assert.expectIssuesFilteredTagsInTable();
  });

  it('should display issues filtered by extCompanyIds', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssues();

    // when
    page.filterIssuesByExtCompanyIds();

    // then
    assert.expectIssuesFilteredExtCompanyIdsInTable();
  });

  it('should search button be disabled for invalid extCompanyIds value', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssues();

    // when
    page.expandFilterPanelAndPutValueIntoExtCompanyIdsInput('not so number');

    // then
    assert.expectSearchButtonDisabled();
  });

  it('should display issue filters for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // and
    util.navigation.navigateToDashboard();

    // when
    page.clickOnIssueFilters();

    // then
    assert.expectIssueFiltersForPhoneDebtCollector();
  });

  it('should not display operators issue filters for admin', () => {
    // given
    util.loggedInAdmin();

    // and
    util.navigation.navigateToDashboard();

    // when
    page.clickOnIssueFilters();

    // then
    assert.doNotExpectIssueFiltersForPhoneDebtCollectorManager();
  });

  it('should not display operators issue filters for phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssues();

    // when
    page.clickOnIssueFilters();

    // then
    assert.doNotExpectIssueFiltersForPhoneDebtCollectorManager();
  });

  it('should demand confirmation before assigning tags to all issues', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.openUpdateTagsForAllDialog();

    // then
    assert.expectIssuesToBeTagged();

    // and
    page.clickUpdateTags();

    // then
    assert.expectSecondDialogVisibleWithContent('Zamierzasz przypisać etykiety do 19 zleceń. Czy kontynuować?');
  });

  it('should demand confirmation before assigning tags to selected issues', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToManagedIssues();

    // and
    page.clickCheckbox(1);
    page.clickCheckbox(2);

    // and
    page.openUpdateTagsForSelectedDialog();

    // then
    assert.expectIssuesToBeTagged();

    // and
    page.clickUpdateTags();

    // and
    assert.expectSecondDialogVisibleWithContent('Zamierzasz przypisać etykiety do 2 zleceń. Czy kontynuować?');
  });

  it('should have selected default filters', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssuesWithFilters();

    // when
    page.extendFiltersPanel();

    // then
    page.expectDefaultFiltersSelected();
  });

  it('should have selected default tag filter', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssuesWithTagFilter();

    // when
    page.extendFiltersPanel();

    // then
    page.expectDefaultTagFilterSelected();
  });

  it('should have selected default issue state filter', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // and
    util.navigation.navigateToManagedIssuesWithStateFilter();

    // when
    page.extendFiltersPanel();

    // then
    page.expectDefaultIssueStateFilterSelected();
  });

});

import {browser} from 'protractor';

export class Navigation {

  SEGMENTS = 'segments';
  HOLIDAYS = 'holidays';
  USERS = 'users';
  CHANGE_PASSWORD = 'changePassword';
  DASHBOARD = 'dashboard';
  DASHBOARD_PLACEHOLDER = 'dashboard-placeholder';
  TAGS = 'tags';
  CONFIGURATION = 'configuration';
  MANAGED_ISSUES = 'issues/manage';
  MANAGED_ISSUES_WITH_FILTERS = `${this.MANAGED_ISSUES}?issueTypeId=1&segment=A&issueStates=NEW&issueStates=IN_PROGRESS`;
  MANAGED_ISSUES_WITH_TAG_FILTER = `${this.MANAGED_ISSUES}?tagId=2`;
  MANAGED_ISSUES_WITH_STATE_FILTER = `${this.MANAGED_ISSUES}?issueState=WAITING_FOR_TERMINATION`;
  MANAGED_COMPANIES = 'companies/manage';

  navigateToRoot() {
    return this.get('');
  }

  navigateToDashboard() {
    return this.get(this.DASHBOARD);
  }

  navigateToIssue(issueId: number) {
    return this.get(`issue/${issueId}`);
  }

  navigateToMyIssues() {
    return this.get('issues/my');
  }

  navigateToManagedIssuesWithFilters() {
    return this.get(this.MANAGED_ISSUES_WITH_FILTERS);
  }

  navigateToManagedIssuesWithTagFilter() {
    return this.get(this.MANAGED_ISSUES_WITH_TAG_FILTER);
  }

  navigateToManagedIssuesWithStateFilter() {
    return this.get(this.MANAGED_ISSUES_WITH_STATE_FILTER);
  }

  navigateToLogin() {
    return this.get('login');
  }

  navigateToMyWork() {
    return this.get('my-work');
  }

  navigateToManagingCompanies() {
    return this.get(this.MANAGED_COMPANIES);
  }

  navigateToHolidays() {
    return this.get(this.HOLIDAYS);
  }

  navigateToTags() {
    return this.get(this.TAGS);
  }

  navigateToConfiguration() {
    return this.get(this.CONFIGURATION);
  }

  navigateToUsers() {
    return this.get(this.USERS);
  }

  navigateToUser(userId: string) {
    return this.get(`${this.USERS}/${userId}`);
  }

  navigateToManagedIssues() {
    return this.get(this.MANAGED_ISSUES);
  }

  navigateToUserDetails(id: number) {
    return this.get(`${this.USERS}/${id}`);
  }

  navigateToManageCompany(extCompanyId: number) {
    return this.get(`companies/${extCompanyId}/manage`);
  }

  navigateToSegments() {
    return this.get(this.SEGMENTS);
  }

  currentUrl() {
    browser.getCurrentUrl();
  }

  /**
   * Otwiera stronę o podanym adresie przełączając ją automatycznie w tryb bez animacji
   *
   * @param {string} path - adres
   * @returns {wdpromise.Promise<any>}
   */
  private get(path: string) {
    return browser.get(path + '?qa=true');
  }
}

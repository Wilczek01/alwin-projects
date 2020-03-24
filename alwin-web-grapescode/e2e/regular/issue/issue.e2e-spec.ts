import {MyWorkPage} from '../../util/po/my-work.po';
import {MyWorkCustomerPage} from '../../util/po/my-work-customer.po';
import {LoginUtilPage} from '../../util/login-util.po';
import {MyWorkPhoneCallPage} from '../../util/po/my-work-phone-call.po';
import {MyWorkDebtsPage} from '../../util/po/my-work-debts.po';

describe('issue page', () => {
  const issueId20103 = 20103;
  const issueId20104 = 20104;
  let page: MyWorkPage;
  let debtsPage: MyWorkDebtsPage;
  let customerPage: MyWorkCustomerPage;
  let phoneCallPage: MyWorkPhoneCallPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    page = new MyWorkPage();
    util = new LoginUtilPage();
    debtsPage = new MyWorkDebtsPage();
    customerPage = new MyWorkCustomerPage();
    phoneCallPage = new MyWorkPhoneCallPage();
  });

  it('should be accessible for logged in user', () => {
    // given
    util.loggedInAnalyst();

    // when
    util.navigation.navigateToIssue(issueId20103);

    // then
    util.expectUrlInQaModeToBe('issue/' + issueId20103, 'not on issue URL');
  });

  it('should load issue page for phone debt collector', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // when
    util.navigation.navigateToIssue(issueId20103);

    // then
    expect(page.issueDataCard().isPresent()).toBe(true);
    page.expectIssueHeader('Realizowane', '29.12.2017');
    page.expectTotalPaymentsInIssueHeader('b/d', 'b/d', '-3 142,76', 'b/d', 'b/d', 'b/d', '2017-12-15 08:45:11');
    customerPage.expectBasicCustomerData('(b/d) b/d', 'brak');
    customerPage.expectAddresses(false);
    customerPage.expectContacts();
    phoneCallPage.expectEmptyPhoneCall();
  });

  it('should load issue page for phone debt collector manager', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToIssue(issueId20103);

    // then
    expect(page.issueDataCard().isPresent()).toBe(true);
    page.expectIssueHeader('Realizowane', '29.12.2017');
    page.expectTotalPaymentsInIssueHeader('b/d', 'b/d', '-3 142,76', 'b/d', 'b/d', 'b/d', '2017-12-15 08:45:11');
    customerPage.expectBasicCustomerData('(b/d) b/d', 'brak\nedit');
    customerPage.expectAddresses(false);
    customerPage.expectContacts();
    phoneCallPage.expectEmptyPhoneCall();
    customerPage.expectUpdateAccountManagerDialog();
  });

  it('should show b/d when debt balance not exists in response', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToIssue(issueId20104);

    // and
    debtsPage.openDebtsTab();
    debtsPage.expectIssueDebtsBalanceStartAndAdditionalNotExists();
  });

  it('should information about payments progress be visible', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToIssue(issueId20103);

    // then
    page.expectTotalPaymentsProgress();
  });

  it('should information about missing exchange rates be visible', () => {
    // given
    util.loggedInPhoneDebtCollectorManager();

    // when
    util.navigation.navigateToIssue(issueId20104);

    // then
    page.expectTotalPaymentsProgressMissing();
  });

  it('should present all contracts', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToIssue(issueId20104);
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();

    // when
    debtsPage.clickNotPaidOnly();

    // then
    debtsPage.expectContractsPresented();

    // and
    debtsPage.expectNotPaidOnlySwitchOff();
    debtsPage.expectOverdueOnlySwitchDisabled();
    debtsPage.expectOverdueOnlySwitchOff();
  });

  it('should present not paid contracts only', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToIssue(issueId20104);
    debtsPage.openDebtsTab();

    // when
    debtsPage.clickGroupByContract();

    // then
    debtsPage.expectNotPaidContractsPresented();

    // and
    debtsPage.expectNotPaidOnlySwitchOn();
    debtsPage.expectOverdueOnlySwitchEnabled();
    debtsPage.expectOverdueOnlySwitchOff();
  });

  it('should present not paid and overdue contracts only', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToIssue(issueId20104);
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();

    // when
    debtsPage.clickOverdueOnly();

    // then
    debtsPage.expectNotPaidAndOverdueContractsPresented();

    // and
    debtsPage.expectNotPaidOnlySwitchOn();
    debtsPage.expectOverdueOnlySwitchEnabled();
    debtsPage.expectOverdueOnlySwitchOn();
  });


});

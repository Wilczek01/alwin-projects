import {MyWorkDebtsPage} from '../../util/po/my-work-debts.po';
import {LoginUtilPage} from '../../util/login-util.po';

describe('my work debts page', () => {
  let debtsPage: MyWorkDebtsPage;
  let util: LoginUtilPage;

  beforeEach(() => {
    util = new LoginUtilPage();
    debtsPage = new MyWorkDebtsPage();
  });

  it('should present balance start and balance additional', () => {
    // given
    util.loggedInPhoneDebtCollector();

    // and
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();

    // then
    debtsPage.expectIssueDebtsBalanceStartAndAdditional();
  });

  it('should present debts contracts', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();

    // and
    debtsPage.clickGroupByContract();

    // then
    debtsPage.expectIssueDebtsSummary();
    debtsPage.expectContractsPresented();
  });

  it('should present debts contract invoices', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();
    debtsPage.clickNotPaidOnly();

    // when
    debtsPage.clickFirstContractFinancialSummaryPanel();

    // then
    debtsPage.expectContractInvoicesPresented();
  });

  it('should present debts contract invoice with positions, corrections and settlements', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();
    debtsPage.clickNotPaidOnly();

    // when
    debtsPage.clickFirstContractFinancialSummaryPanel();
    debtsPage.clickOnInvoiceNumber();

    // then
    debtsPage.expectInvoiceDetails();
  });

  it('should present settlements', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();
    debtsPage.clickNotPaidOnly();
    debtsPage.clickFirstContractFinancialSummaryPanel();

    // when
    debtsPage.clickFirstInvoiceButton();

    // then
    debtsPage.expectSettlementDialogOpened();
  });

  it('should present only overdue contracts', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    debtsPage.openDebtsTab();

    // when
    debtsPage.clickGroupByContract();

    // then
    debtsPage.expectOverdueContractsPresented();
  });

  it('should present only overdue contract invoices', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();

    // when
    debtsPage.clickFirstContractFinancialSummaryPanel();

    // then
    debtsPage.expectContractOnlyOverdueInvoicesPresented();
  });

  it('should present exclusion invoice button', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();
    debtsPage.openDebtsTab();
    debtsPage.clickGroupByContract();
    debtsPage.clickNotPaidOnly();

    // when
    debtsPage.clickFirstContractFinancialSummaryPanel();

    // then
    debtsPage.expectContractInvoicesPresented();
    debtsPage.clickInvoiceExclusionButton();
  });

  it('should present overdue issue invoice list', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();

    // then
    debtsPage.expectOverdueIssueInvoicesPresented();
  });

  it('should present all issue invoice list', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();
    debtsPage.clickOverdueOnly();

    // then
    debtsPage.expectIssueInvoicesPresented();
  });

  it('should present issue subject invoice list', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();

    // then
    debtsPage.expectIssueSubjectInvoicesPresented();
  });

  it('should present invoice list with small row height', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();

    // then
    debtsPage.expectIssueInvoiceListRowHeight(30);
  });

  it('should click debs report download button ', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // when
    debtsPage.openDebtsTab();

    // and
    debtsPage.clickDownloadIconButton();

  });

  it('should sum selected invoices balances', () => {
    // given
    util.loggedInPhoneDebtCollector();
    util.navigation.navigateToMyWork();

    // and
    debtsPage.openDebtsTab();

    // and
    expect(debtsPage.selectedInvoicesBalance().getText()).toBe('');

    // and
    debtsPage.selectInvoices();

    // then
    expect(debtsPage.selectedInvoicesBalance().getText()).toBe('Suma sald faktur: -248,24 (PLN) i -314,35 (EUR)');

    // and
    debtsPage.clickClearInvoicesSelectionButton();

    // and
    expect(debtsPage.selectedInvoicesBalance().getText()).toBe('');
  });
});

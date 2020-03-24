import {by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';
import {InvoicePage} from './my-work-debts-invoice.po';

export class MyWorkDebtsPage extends AbstractPage {

  openDebtsTab() {
    this.clickElementWithWait(element(by.id('mat-tab-label-0-1')));
  }

  contractFinancialSummaryPanel(index: number) {
    return element(by.id(`contract-financial-summary-panel-${index}`));
  }

  invoiceRow(index: number) {
    return element(by.id(`invoice-row-${index}`));
  }

  firstContractInvoicesPaginatorLabel() {
    return element.all(by.className('mat-paginator-range-label')).get(0);
  }

  issueInvoicesPaginatorLabel() {
    return element(by.className('mat-paginator-range-label'));
  }

  firstContractInvoicesCard() {
    return element.all(by.tagName('alwin-issue-invoices-card')).get(0);
  }

  recalculateBalancesButton() {
    return element(by.id('recalculate-balances'));
  }

  clickFirstInvoiceButton() {
    this.clickElementWithWait(element.all(by.id('invoice-settlements-000016/12/2015/TO')).get(0));
  }

  issueSubjectOnlySlider() {
    return element(by.id('issue-subject-only'));
  }

  overdueOnlySlider() {
    return element(by.id('overdue-only'));
  }

  notPaidSlider() {
    return element(by.id('not-paid-only'));
  }

  groupByContractSlider() {
    return element(by.id('group-by-contract'));
  }

  downloadIconButton() {
    return element(by.id('group-by-contract'));
  }

  selectedInvoicesBalance() {
    return element(by.id('selectedInvoicesSum'));
  }

  selectInvoices() {
    this.clickElementWithWait(element(by.id('select-invoice-10')));
    this.clickElementWithWait(element(by.id('select-invoice-11')));
    this.clickElementWithWait(element(by.id('select-invoice-13')));
    this.clickElementWithWait(element(by.id('select-invoice-14')));
  }

  clickIssueSubjectOnly() {
    this.clickElementWithWait(this.issueSubjectOnlySlider());
  }

  clickNotPaidOnly() {
    this.clickElementWithWait(this.notPaidSlider());
  }

  clickOverdueOnly() {
    this.clickElementWithWait(this.overdueOnlySlider());
  }

  clickGroupByContract() {
    this.clickElementWithWait(this.groupByContractSlider());
  }

  clickDownloadIconButton() {
    this.clickElementWithWait(this.downloadIconButton());
  }

  clickClearInvoicesSelectionButton() {
    this.clickElementWithWait(element(by.id('clear-invoices-selection-button')));
  }

  clickFirstContractFinancialSummaryPanel() {
    this.clickElementWithWait(this.contractFinancialSummaryPanel(0));
  }

  expectSettlementDialogOpened() {
    expect(element(by.id('settlements-table')).isPresent()).toBe(true);
  }

  expectIssueDebtsSummary() {
    // FIXME: do poprawy po poprawnym liczeniu kapitału, na razie ukryty
    // expect(element(by.id('issue-rpb')).getText()).toBe('1 234 567,89 PLN   i   7 654,32 EUR');
    expect(element(by.id('issue-current-balance')).getText()).toBe('-496,48 PLN i -214,35 EUR');
    this.expectIssueDebtsBalanceStartAndAdditional();
  }

  expectIssueDebtsBalanceStartAndAdditional() {
    expect(element(by.id('customer-balance-start')).getText()).toBe('-496,48 PLN i -90,23 EUR');
    expect(element(by.id('customer-balance-additional')).getText()).toBe('0,00 PLN i -124,12 EUR');
  }

  expectIssueDebtsBalanceStartAndAdditionalNotExists() {
    expect(element(by.id('customer-balance-start')).getText()).toBe('b/d PLN i b/d EUR');
    expect(element(by.id('customer-balance-additional')).getText()).toBe('b/d PLN i b/d EUR');
  }

  expectContractsPresented() {
    this.expectContract(0, 'issue_21_00002/15/1', '1471', '496,48', '0,00', '0,00',
      '124,12', '0,00', '0,00', '496,48', '124,12', 'nie');

    this.expectContract(1, 'issue_21_00001/15/1', '1319', '0,00', '0,00', '190,23',
      '0,00', '0,00', '100,00', '0,00', '90,23', 'tak');
  }

  expectNotPaidContractsPresented() {
    this.expectContract(0, 'issue_21_00002/15/1', '1471', '360,10', '0,00', '0,00',
      '70,34', '0,00', '0,00', '360,10', '70,34', 'nie');

    this.expectContract(1, 'issue_21_00001/15/1', '1319', '0,00', '0,00', '160,45',
      '0,00', '0,00', '100,00', '0,00', '60,45', 'tak');
  }

  expectNotPaidAndOverdueContractsPresented() {
    this.expectContract(0, 'issue_21_00002/15/1', '1471', '250,10', '0,00', '0,00',
      '40,34', '0,00', '0,00', '250,10', '40,34', 'nie');

    this.expectContract(1, 'issue_21_00001/15/1', '1319', '0,00', '0,00', '120,45',
      '0,00', '0,00', '100,00', '0,00', '20,45', 'tak');
  }

  expectContract(index: number, contractNo: string, dpd: string, requiredPaymentPLN: string, notRequiredPaymentPLN: string, requiredPaymentEUR: string,
                 notRequiredPaymentEUR: string, overpaymentPLN: string, overpaymentEUR: string, totalPLN: string, totalEUR: string, excluded: string) {
    const contract = this.contractFinancialSummaryPanel(index);
    expect(contract.isDisplayed()).toBeTruthy();
    expect(this.getTitle(contract)).toBe(contractNo);
    expect(this.getContractColumn(contract, 2)).toBe(dpd);
    expect(this.getContractColumn(contract, 3)).toBe(requiredPaymentPLN);
    expect(this.getContractColumn(contract, 4)).toBe(notRequiredPaymentPLN);
    expect(this.getContractColumn(contract, 5)).toBe(requiredPaymentEUR);
    expect(this.getContractColumn(contract, 6)).toBe(notRequiredPaymentEUR);
    expect(this.getContractColumn(contract, 7)).toBe(overpaymentPLN);
    expect(this.getContractColumn(contract, 8)).toBe(overpaymentEUR);
    expect(this.getContractColumn(contract, 8)).toBe(overpaymentEUR);
    expect(this.getContractColumn(contract, 8)).toBe(overpaymentEUR);
    expect(this.getContractColumn(contract, 9)).toBe(totalPLN);
    expect(this.getContractColumn(contract, 10)).toBe(totalEUR);
    expect(this.getContractColumn(contract, 11)).toBe(excluded);
  }

  private getTitle(contract) {
    return contract.element(by.css('mat-expansion-panel-header > span.mat-content > mat-panel-title')).getText();
  }

  getContractColumn(contract, index: number) {
    return contract.element(by.css(`mat-expansion-panel-header > span.mat-content > mat-panel-description:nth-child(${index})`)).getText();
  }

  expectOverdueContractsPresented() {
    expect(this.contractFinancialSummaryPanel(0).isDisplayed()).toBeTruthy();
    expect(this.contractFinancialSummaryPanel(1).isDisplayed()).toBeTruthy();
  }

  expectContractInvoicesPresented() {
    expect(this.firstContractInvoicesCard().isDisplayed()).toBeTruthy();
    new InvoicePage(2).expect(false, false, '000016/12/2015/TO', 'Oplata dodatkowa', 'PLN', '10.12.2015', '10.12.2015',
      '10.12.2015', '461,25', '0,00', '0,00', '0', 'tak', 'issue_21_00001/15/1');

    new InvoicePage(3).expect(false, false, '000006/11/2015/RL/PO/LW', 'Oplata okresowa', 'EUR', '01.12.2015', '10.11.2015',
      '13.11.2015', '490,73', '100,00', '10,00', '3', 'nie', 'issue_21_00001/15/1');

    new InvoicePage(10).expect(false, true, '000001/12/2020/RL/PO/LW', 'Oplata okresowa', 'EUR', '', '01.12.2020',
      '', '190,23', '-190,23', '90,32', '1319', 'nie', 'issue_21_00001/15/1');

    new InvoicePage(11).expect(true, true, '000001/10/2024/RL/PO/LW', 'Oplata okresowa', 'EUR', '', '02.10.2024',
      '', '124,12', '-124,12', '24,21', '-82', 'nie', 'issue_21_00002/15/1');

    new InvoicePage(12).expect(false, true, '000001/07/2020/RL/PO/LW', 'Oplata okresowa', 'PLN', '', '02.07.2020',
      '', '124,12', '-124,12', '24,20', '1471', 'nie', 'issue_21_00002/15/1');
  }

  expectContractOnlyOverdueInvoicesPresented() {
    expect(this.firstContractInvoicesPaginatorLabel().getText()).toBe('1 - 5 z 5');
  }

  clickInvoiceExclusionButton() {
    this.clickElementWithWait(element.all(by.id('exclude-invoice-3-button')).get(0));
  }

  clickOnInvoiceNumber() {
    element.all(by.id('invoice-settlements-000001/10/2024/RL/PO/LW')).get(0).click();
  }

  expectInvoiceDetails() {
    expect(element(by.id('dialog-title')).getText()).toBe('Dokument: 000001/10/2024/RL/PO/LW');

    expect(element(by.css('#invoice-entries-table > thead > tr:nth-child(1) > th')).getText()).toBe('Pozycje');
    this.expectInvoiceEntry(1, '1', 'Rata leasingowa nr 10 - Odsetki', '1', 'szt.', '1 752,31', '23', '403,03');
    this.expectInvoiceEntry(2, '2', 'Wysłanie wezwania do zapłaty', '2', 'szt.', '100,00', 'ZW', '0,00');
    this.expectInvoiceEntry(3, '3', 'Opłata za sprawdzenie w CRZ', '3', 'szt.', '150,00', 'b/d', '0,00');

    expect(element(by.css('#corrections-table > thead > tr:nth-child(1) > th')).getText()).toBe('Faktury korygujące');
    this.expectInvoiceCorrection(1, '000031/12/2015/KF', 'Oplata dodatkowa', 'PLN', '31.10.2015', '381,30',
      '257,18', '257,18');
    this.expectInvoiceCorrection(2, '000029/12/2015/KF', 'Oplata dodatkowa', 'PLN', '29.10.2015', '356,70',
      '-24,60', '232,58');
    this.expectInvoiceCorrection(3, '000030/12/2015/KF', 'Oplata dodatkowa', 'PLN', '30.10.2015', '0,00',
      '-356,70', '-124,12');

    expect(element(by.css('#settlements-table > thead > tr:nth-child(1) > th')).getText()).toBe('Rozrachunki');
    this.expectSettlement(1, '4', '309,69', '02.10.2018', 'MA', '672 Zakup 002418/16/1');
    this.expectSettlement(2, '5', '715,45', '11.10.2018', 'WN', '679 Inne fv - menager');
    this.expectSettlement(3, '1', '307,50', '12.10.2018', 'WN', '626 Rata 2 - 001980/15/1');
    this.expectSettlement(4, '3', '65,50', '14.10.2018', 'WN', '624 Wplata poczatkowa 000100/15/1');
    this.expectSettlement(5, '2', '5 197,49', '02.11.2018', 'MA', 'czynsze w PLN');
  }

  expectInvoiceEntry(index: number, positionNumber: string, paymentTitle: string, quantity: string, quantityUnit: string, netAmount: string, vatRate: string,
                     vatAmount: string) {
    expect(this.getInvoiceEntryColumn(index, 1)).toBe(positionNumber);
    expect(this.getInvoiceEntryColumn(index, 2)).toBe(paymentTitle);
    expect(this.getInvoiceEntryColumn(index, 3)).toBe(quantity);
    expect(this.getInvoiceEntryColumn(index, 4)).toBe(quantityUnit);
    expect(this.getInvoiceEntryColumn(index, 5)).toBe(netAmount);
    expect(this.getInvoiceEntryColumn(index, 6)).toBe(vatRate);
    expect(this.getInvoiceEntryColumn(index, 7)).toBe(vatAmount);
  }

  expectInvoiceCorrection(index: number, invoiceNumber: string, type: string, currency: string, dueDate: string, grossAmount: string,
                          previousCorrectionGrossAmountDiff: string, rootInvoiceGrossAmountDiff: string) {
    expect(this.getInvoiceCorrectionColumn(index, 1)).toBe(invoiceNumber);
    expect(this.getInvoiceCorrectionColumn(index, 2)).toBe(type);
    expect(this.getInvoiceCorrectionColumn(index, 3)).toBe(currency);
    expect(this.getInvoiceCorrectionColumn(index, 4)).toBe(dueDate);
    expect(this.getInvoiceCorrectionColumn(index, 5)).toBe(grossAmount);
    expect(this.getInvoiceCorrectionColumn(index, 6)).toBe(previousCorrectionGrossAmountDiff);
    expect(this.getInvoiceCorrectionColumn(index, 7)).toBe(rootInvoiceGrossAmountDiff);
  }

  getInvoiceCorrectionColumn(index: number, column: number) {
    return element(by.css(`#corrections-table > tbody > tr:nth-child(${index}) > td:nth-child(${column})`)).getText();
  }

  getInvoiceEntryColumn(index: number, column: number) {
    return element(by.css(`#invoice-entries-table > tbody > tr:nth-child(${index}) > td:nth-child(${column})`)).getText();
  }

  private expectSettlement(index: number, settlementId: string, amountSettled: string, setDate: string, side: string, title: string) {
    expect(this.getInvoiceSettlementColumn(index, 1)).toBe(settlementId);
    expect(this.getInvoiceSettlementColumn(index, 2)).toBe(amountSettled);
    expect(this.getInvoiceSettlementColumn(index, 3)).toBe(setDate);
    expect(this.getInvoiceSettlementColumn(index, 4)).toBe(side);
    expect(this.getInvoiceSettlementColumn(index, 5)).toBe(title);
  }

  getInvoiceSettlementColumn(index: number, column: number) {
    return element(by.css(`#settlements-table > tbody > tr:nth-child(${index}) > td:nth-child(${column})`)).getText();
  }

  expectOverdueOnlySwitchOn() {
    expect(this.overdueOnlySlider().getAttribute('class')).toContain('mat-checked', 'Overdue only slider not on');
  }

  expectOverdueOnlySwitchOff() {
    expect(this.overdueOnlySlider().getAttribute('class')).not.toContain('mat-checked', 'Overdue only slider on');
  }

  expectOverdueOnlySwitchDisabled() {
    expect(this.overdueOnlySlider().getAttribute('class')).toContain('mat-disabled', 'Overdue only slider enabled');
  }

  expectOverdueOnlySwitchEnabled() {
    expect(this.overdueOnlySlider().getAttribute('class')).not.toContain('mat-disabled', 'Overdue only slider disabled');
  }

  expectNotPaidOnlySwitchOn() {
    expect(this.notPaidSlider().getAttribute('class')).toContain('mat-checked', 'Not paid only slider not on');
  }

  expectNotPaidOnlySwitchOff() {
    expect(this.notPaidSlider().getAttribute('class')).not.toContain('mat-checked', 'Not paid only slider on');
  }

  expectIssueInvoicesPresented() {
    expect(this.issueInvoicesPaginatorLabel().getText()).toBe('1 - 5 z 5');
  }

  expectIssueSubjectInvoicesPresented() {
    expect(this.issueInvoicesPaginatorLabel().getText()).toBe('1 - 5 z 5');
  }

  expectOverdueIssueInvoicesPresented() {
    expect(this.issueInvoicesPaginatorLabel().getText()).toBe('1 - 5 z 5');
  }

  expectIssueInvoiceListRowHeight(rowHeight: number) {
    this.invoiceRow(11).getSize().then(size => {
      expect(size.height).toBe(rowHeight);
    });
  }
}

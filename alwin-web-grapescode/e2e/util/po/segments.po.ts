import {by, element} from 'protractor';

export class SegmentsPage {

  expectWallets() {
    const alwinSegments = element(by.tagName('alwin-segments'));
    expect(alwinSegments.isPresent).toBeTruthy();

    this.expectUser('Portfele dla zleceń : Nowe, Realizowane', 0);
    this.expectWallet0('account_balance_wallet\nPortfel:\nS1 , segment A');
    this.expectWallet1('account_balance_wallet\nPortfel:\nS2 , segment A');
    this.expectWallet2('account_balance_wallet\nPortfel:\nS1 , segment B');
    this.expectWallet3('account_balance_wallet\nPortfel:\nS2 , segment B');

    this.expectWaitingForTerminationIssuesWallet1('Czekające na zamknięcie');

    this.expectTagWallet0('bookmark\nKoszyk 1');
    this.expectTagWallet1('bookmark\nKoszyk 2');
  }

  expectWalletsForManager() {
    const alwinSegments = element(by.tagName('alwin-segments'));
    expect(alwinSegments.isPresent).toBeTruthy();

    this.expectUser('Portfele dla zleceń : Nowe, Realizowane', 0);
    this.expectWallet0('account_balance_wallet\nPortfel:\nS1 , segment A\nmode_edit');
    this.expectWallet1('account_balance_wallet\nPortfel:\nS2 , segment A\nmode_edit');
    this.expectWallet2('account_balance_wallet\nPortfel:\nS1 , segment B\nmode_edit');
    this.expectWallet3('account_balance_wallet\nPortfel:\nS2 , segment B\nmode_edit');

    this.expectWaitingForTerminationIssuesWallet1('Czekające na zamknięcie\nmode_edit');

    this.expectTagWallet0('bookmark\nKoszyk 1\nmode_edit');
    this.expectTagWallet1('bookmark\nKoszyk 2\nmode_edit');
  }

  private expectUser(issueTypesText: string, id: number) {
    expect(element(by.id(`issue-states-${id}`)).getText()).toBe(issueTypesText);
  }

  expectWallet0(segment: string) {
    this.expectWallet(0, segment, '1-14', '4', '4', '-679 285,34', '0,00', '0,00');
  }

  expectWallet1(segment: string) {
    this.expectWallet(1, segment, '14-31', '1', '1', '554 642,67', '0,00', '0,00');
  }

  expectWallet2(segment: string) {
    this.expectWallet(2, segment, '1-9', '1', '1', '554 642,67', '0,00', '0,00');
  }

  expectWallet3(segment: string) {
    this.expectWallet(3, segment, '9-21', '0', '0', '0,00', '0,00', '0,00');
  }

  expectWallet(id: number, segment: string, dpd: string, currentIssueQueueCount: string, companyCount: string, currentBalancePLN: string,
               currentBalanceEUR: string, involvement: string) {
    const walletId = `wallet-${id}`;
    expect(this.findFirstValue(walletId).getText()).toBe(segment);
    expect(this.findNextValue(walletId, 2).getText()).toBe(dpd);
    this.expectAlwinWallet(walletId, currentIssueQueueCount, companyCount, currentBalancePLN, currentBalanceEUR, involvement);
  }

  private expectAlwinWallet(walletId: string, currentIssueQueueCount: string, companyCount: string, currentBalancePLN: string, currentBalanceEUR: string, involvement: string) {
    expect(this.findNextWalletValue(walletId, 1).getText()).toBe(currentIssueQueueCount);
    expect(this.findNextWalletValue(walletId, 2).getText()).toBe(companyCount);
    expect(this.findNextWalletValue(walletId, 3).getText()).toBe(currentBalancePLN);
    expect(this.findNextWalletValue(walletId, 4).getText()).toBe(currentBalanceEUR);
    expect(this.findNextWalletValue(walletId, 5).getText()).toBe(involvement);
  }

  expectTagWallet0(tag: string) {
    this.expectTagWallet(0, tag, '2', '2', '12 345,67', '0,00', '0,00');
  }

  expectTagWallet1(tag: string) {
    this.expectTagWallet(1, tag, '1', '1', '12 345,67', '234 642,67', '0,00');
  }

  expectTagWallet(id: number, tag: string, currentIssueQueueCount: string, companyCount: string, involvement: string, currentBalancePLN: string,
                  currentBalanceEUR: string) {
    const tagWalletId = `tag-wallet-${id}`;
    expect(this.findFirstValue(tagWalletId).getText()).toBe(tag);
    this.expectAlwinWallet(tagWalletId, currentIssueQueueCount, companyCount, currentBalancePLN, currentBalanceEUR, involvement);
  }

  findFirstValue(id: string) {
    return element(by.css(`#${id} > mat-list > mat-list-item:nth-child(1) > div`));
  }

  findNextValue(id: string, position: number) {
    return this.findValue(id, position, 4);
  }

  findValue(id: string, position: number, div: number) {
    return element(by.css(`#${id} > mat-list > mat-list-item:nth-child(${position}) > div > div:nth-child(${div})`));
  }

  findNextWalletValue(id: string, position: number) {
    return this.findWalletValue(id, position, 4);
  }

  findWalletValue(id: string, position: number, div: number) {
    return element(by.css(`#${id} > mat-list > alwin-wallet > mat-list-item:nth-child(${position}) > div > div:nth-child(${div})`));
  }

  navigateToIssuesPage() {
    element(by.id('navigate-to-issues-0')).click();
  }

  navigateToTagIssuesPage() {
    element(by.id('navigate-to-tag-issues-1')).click();
  }

  navigateToStateIssuesPage() {
    element(by.id('navigate-to-waiting-issues')).click();
  }

  expectWaitingForTerminationIssuesWallet1(status: string) {
    this.expectWaitingForTerminationIssuesWallet(status, '1', '1', '12 345,67', '234 642,67', '0,00');
  }


  expectWaitingForTerminationIssuesWallet(status: string, currentIssueQueueCount: string, companyCount: string, involvement: string, currentBalancePLN: string,
                                          currentBalanceEUR: string) {
    const waitingWalletId = 'waiting-for-termination-wallet';
    expect(this.findFirstValue(waitingWalletId).getText()).toBe(status);
    this.expectAlwinWallet(waitingWalletId, currentIssueQueueCount, companyCount, currentBalancePLN, currentBalanceEUR, involvement);
  }
}

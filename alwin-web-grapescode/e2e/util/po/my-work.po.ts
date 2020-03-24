import {browser, by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';

export class MyWorkPage extends AbstractPage {

  issueDataCard() {
    return element(by.id('issue-data-card'));
  }

  issueNoDataSection() {
    return element(by.id('no-issue-data-card'));
  }

  expectNoIssueData() {
    expect(this.issueDataCard().isPresent()).toBe(false);
    expect(this.issueNoDataSection().isPresent()).toBe(true);
    expect(this.issueNoDataSection().getText()).toBe('Nie znaleziono zlecenia');
  }

  expectIssueHeader(issueState: string, endDate: string) {
    const issueHeader = element(by.tagName('alwin-issue-header'));
    expect(issueHeader.isPresent()).toBe(true);
    expect(element(by.className('to-left issue-header issue-number')).getText()).toBe('Zlecenie\n20103');
    const headerElements = element.all(by.className('to-left issue-header'));
    expect(headerElements.get(1).getText()).toBe(`Stan\n${issueState}`);
    expect(headerElements.get(2).getText()).toBe('Etykiety');
    expect(headerElements.get(3).getText()).toBe('Nr klienta\n253');
    expect(headerElements.get(4).getText()).toBe('Klient\nADH253');
    expect(headerElements.get(5).getText()).toBe('Typ\nphone');
    expect(headerElements.get(6).getText()).toBe('DPD\n10 - 20');
    expect(headerElements.get(7).getText()).toBe(`Czas trwania\n15.12.2017\n${endDate}`);
    expect(headerElements.get(8).getText()).toBe('Priorytet\nWysoki');
    expect(headerElements.get(9).getText()).toBe(`Spłacono\n70 352,92 PLN / 100 000,23 PLN (70%)`);
    const issueProgress = element(by.id('issue-progress'));
    expect(issueProgress.isPresent()).toBe(true);
  }

  expectTotalPaymentsInIssueHeader(balanceStartPln: string, balanceStartEur: string,
                                   currentBalancePln: string, currentBalanceEur: string,
                                   paymentsPln: string, paymentsEur: string, balanceUpdateDate: string) {
    element.all(by.className('issue-clickable-header')).get(0).click();
    const totalPaymentsMoreDetailRows = element.all(by.className('total-payments-more-details-row'));
    expect(totalPaymentsMoreDetailRows.get(0).getText()).toBe(`Saldo początkowe:\n${balanceStartPln} PLN\n${balanceStartEur} EUR`);
    expect(totalPaymentsMoreDetailRows.get(1).getText()).toBe(`Saldo bieżące:\n${currentBalancePln} PLN\n${currentBalanceEur} EUR`);
    expect(totalPaymentsMoreDetailRows.get(2).getText()).toBe(`Suma wpłat:\n${paymentsPln} PLN\n${paymentsEur} EUR`);
    expect(totalPaymentsMoreDetailRows.get(3).getText()).toBe(`Data aktualizacji sald:\n${balanceUpdateDate}`);
    element.all(by.className('total-payments-more-details-row')).get(0).click();
  }

  openNotifications() {
    browser.waitForAngular();
    this.clickElementById('show-notifications-button');
  }

  markAsRead() {
    browser.waitForAngular();
    this.clickElementById('read-notification-button-1');
    browser.waitForAngular();
    this.clickElementById('confirmation-dialog-confirm-button');
  }

  cancelAsReading() {
    browser.waitForAngular();
    this.clickElementById('read-notification-button-1');
    browser.waitForAngular();
    this.clickElementById('confirmation-dialog-cancel-button');
  }

  expectUnreadNotification() {
    browser.waitForAngular();

    expect(element(by.css(`span[title="notification-message-1"]`)).getText()).toBe('Powiadomienie nieodczytane nr 1');
    expect(element(by.css(`span[title="notification-details-1"]`)).getText()).toBe('Czesław Miłosz, utworzono: 29.11.2017');

    const excessPaymentNotifications = element.all(by.css(`span[title="notification-message-"]`));
    const excessPaymentNotificationDetails = element.all(by.css(`span[title="notification-details-"]`));
    expect(excessPaymentNotifications.get(0).getText()).toBe('U klienta stwierdzono nadpłatę w wysokości 1291.50 powstałą w wyniku wpłaty z dnia 2018-07-04 (WN 97).');
    expect(excessPaymentNotificationDetails.get(0).getText()).toBe('System, utworzono: 15.07.2017');
    expect(excessPaymentNotifications.get(1).getText()).toBe('U klienta stwierdzono nadpłatę w wysokości 5290.24 powstałą w wyniku wpłaty z dnia 2018-07-05 (WN 141).');
    expect(excessPaymentNotificationDetails.get(1).getText()).toBe('System, utworzono: 15.07.2017');
    expect(excessPaymentNotifications.get(2).getText()).toBe('U klienta stwierdzono nadpłatę w wysokości 8045.10 powstałą w wyniku wpłaty z dnia 2018-07-06 (WN 169).');
    expect(excessPaymentNotificationDetails.get(2).getText()).toBe('System, utworzono: 15.07.2017');
    expect(excessPaymentNotifications.get(3).getText()).toBe('U klienta stwierdzono nadpłatę w wysokości 3500.50 powstałą w wyniku wpłaty z dnia 2018-07-06 (PRZEKSIĘGOWANIE ODSZKODOWANIA).');
    expect(excessPaymentNotificationDetails.get(3).getText()).toBe('System, utworzono: 15.07.2017');
  }

  expectReadNotification() {
    browser.waitForAngular();
    expect(element(by.css(`span[title="notification-message-3"]`)).getText()).toBe('Powiadomienie odczytane nr 1');
    expect(element(by.css(`span[title="notification-details-3"]`)).getText()).toBe('Czesław Miłosz, utworzono: 19.11.2017, przeczytano: 30.11.2017.');
  }

  openAuditDialog() {
    browser.waitForAngular();
    this.clickElementById('show-audit-button');
  }

  expectAuditDetails() {
    browser.waitForAngular();
    expect(element(by.id('fieldName-0')).getText()).toBe('Data ostaniej akutalizacji sald');
    expect(element(by.id('oldValue-0')).getText()).toBe('b/d');
    expect(element(by.id('newValue-0')).getText()).toBe('2018-04-25 08:45:06');
    expect(element(by.id('operatorLogin-0')).getText()).toBe('jdlugosz');
    expect(element(by.id('changeDate-0')).getText()).toBe('2018-04-25 08:45:06');
    expect(element(by.id('changeType-0')).getText()).toBe('zmiana wartości');
  }

  expectTotalPaymentsProgress() {
    browser.waitForAngular();
    expect(element(by.className('issue-total-payments-progress-pln')).getText()).toBe('70 352,92 PLN / 100 000,23 PLN (70%)');
    expect(element(by.id('issue-total-payment-progress')).getAttribute('aria-valuenow')).toBe('70');

  }

  expectTotalPaymentsProgressMissing() {
    expect(element(by.className('issue-total-payments-progress-pln')).getText()).toBe('Proszę sprawdzić kursy walut');
  }

}

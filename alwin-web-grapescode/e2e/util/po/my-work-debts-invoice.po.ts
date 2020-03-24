import {by, element} from 'protractor';
import {AbstractPage} from '../abstract.po';

export class InvoicePage extends AbstractPage {

  invoice;

  constructor(id: number) {
    super();
    this.invoice = element.all(by.id(`invoice-row-${id}`)).get(0);
  }

  expectDisplayed() {
    expect(this.invoice.isDisplayed()).toBeTruthy();
  }

  expectCorrections(column: number, corrections: boolean) {
    const ribbon = this.invoice.element(by.css(`mat-cell:nth-child(${column}) > div > div`));
    expect(ribbon.isPresent()).toBe(corrections);
  }

  expectIssueSubject(column: number, issueSubject: boolean) {
    const ribbon = this.invoice.element(by.css(`mat-cell:nth-child(${column}) > mat-icon`));
    expect(ribbon.isPresent()).toBe(issueSubject);
  }

  expectInvoiceNumber(column: number, invoiceNumber: string) {
    const span = this.invoice.element(by.css(`mat-cell:nth-child(${column}) > div > button > span`));
    expect(span.getText()).toBe(invoiceNumber);
  }

  expectContractNumber(column: number, contractNumber: string) {
    expect(this.getInvoiceColumn(column)).toBe(contractNumber);
  }

  expectType(column: number, type: string) {
    expect(this.getInvoiceColumn(column)).toBe(type);
  }

  expectCurrency(column: number, currency: string) {
    expect(this.getInvoiceColumn(column)).toBe(currency);
  }

  expectIssueDate(column: number, issueDate: string) {
    expect(this.getInvoiceColumn(column)).toBe(issueDate);
  }

  expectDueDate(column: number, dueDate: string) {
    expect(this.getInvoiceColumn(column)).toBe(dueDate);
  }

  expectLastPaymentDate(column: number, lastPaymentDate: string) {
    expect(this.getInvoiceColumn(column)).toBe(lastPaymentDate);
  }

  expectCurrentBalance(column: number, currentBalance: string) {
    expect(this.getInvoiceColumn(column)).toBe(currentBalance);
  }

  expectPaid(column: number, paid: string) {
    expect(this.getInvoiceColumn(column)).toBe(paid);
  }

  expectGrossAmount(column: number, grossAmount: string) {
    expect(this.getInvoiceColumn(column)).toBe(grossAmount);
  }

  expectDpd(column: number, dpd: string) {
    expect(this.getInvoiceColumn(column)).toBe(dpd);
  }

  expectExcluded(column: number, excluded: string) {
    expect(this.getInvoiceColumn(column)).toBe(excluded);
  }

  getInvoiceColumn(index: number) {
    return this.invoice.element(by.css(`mat-cell:nth-child(${index})`)).getText();
  }

  expect(corrections: boolean, issueSubject: boolean, invoiceNumber: string, type: string, currency: string, issueDate: string, dueDate: string,
         lastPaymentDate: string, currentBalance: string, paid: string, grossAmount: string, dpd: string, excluded: string, contractNumber: string) {
    this.expectDisplayed();
    let column = 2;
    this.expectCorrections(column, corrections);
    this.expectIssueSubject(++column, issueSubject);
    this.expectInvoiceNumber(++column, invoiceNumber);
    this.expectContractNumber(++column, contractNumber);
    this.expectType(++column, type);
    this.expectCurrency(++column, currency);
    this.expectIssueDate(++column, issueDate);
    this.expectDueDate(++column, dueDate);
    this.expectLastPaymentDate(++column, lastPaymentDate);
    this.expectCurrentBalance(++column, currentBalance);
    this.expectPaid(++column, paid);
    this.expectGrossAmount(++column, grossAmount);
    this.expectDpd(++column, dpd);
    this.expectExcluded(++column, excluded);
  }

}

export class IssueInvoicesWithTotalBalance<T> {

  issues: T[];
  balance: number;
  totalValues: number;

  constructor(invoices: T[], balance: number, totalValues: number) {
    this.issues = invoices;
    this.balance = balance;
    this.totalValues = totalValues;
  }
}

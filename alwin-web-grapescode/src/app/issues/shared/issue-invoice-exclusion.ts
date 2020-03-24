export class IssueInvoiceExclusion {
  issueId: number;
  invoiceId: number;
  excluded: boolean;

  constructor(issueId: number, invoiceId: number, excluded: boolean) {
    this.issueId = issueId;
    this.invoiceId = invoiceId;
    this.excluded = excluded;
  }
}

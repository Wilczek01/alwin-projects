/**
 * Model faktury w windykacji formalnej
 */
export class FormalDebtCollectionInvoice {
  id: number;
  invoiceNumber: string;
  contractNumber: string;
  issueDate: Date;
  dueDate: Date;
  currency: string;
  netAmount: number;
  grossAmount: number;
  currentBalance: number;
}

import {InvoiceCorrection} from './invoice-correction';

export class Invoice {
  id: number;
  invoiceId: string;
  contractNumber: string;
  currency: string;
  currentBalance: number;
  paid: number;
  dueDate: Date;
  grossAmount: number;
  lastPaymentDate: Date;
  netAmount: number;
  number: string;
  typeId: {
    id: number,
    label: string
  };
  typeLabel: string;
  excluded: boolean;
  dpd: number;
  correction: boolean;
  corrections: InvoiceCorrection[];
  correctionOrder: number;
  issueSubject: boolean;
  issueDate: Date;
}

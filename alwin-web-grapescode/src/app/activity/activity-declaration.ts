/**
 * Deklaracja spłaty dla czynności windykacyjnej
 */
export class ActivityDeclaration {
  id: number;
  declarationDate: Date;
  declaredPaymentDate: Date;
  declaredPaymentAmountPLN: number;
  declaredPaymentAmountEUR: number;
  cashPaidPLN: number;
  cashPaidEUR: number;
  monitored: boolean;
  currentBalancePLN: number;
  currentBalanceEUR: number;
}

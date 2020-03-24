/**
 * Stan wezwania do zapłaty
 */
export enum DemandForPaymentStateConst {
  NEW = 'NEW',
  WAITING = 'WAITING',
  FAILED = 'FAILED',
  ISSUED = 'ISSUED',
  CANCELED = 'CANCELED',
  REJECTED = 'REJECTED'
}

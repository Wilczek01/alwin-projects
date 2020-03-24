/**
 * Typ przechowujący możliwe warianty przypominania o czynności terenowej zaległej lub do wykonania dziś
 */
export enum IssueReminderMarkConst {
  NONE = 'NONE',
  /**
   * Czynność do wykonania dziś
   */
  NORMAL = 'NORMAL',
  /**
   * Czynność zaległa
   */
  HIGH = 'HIGH'
}

export class RoleType {
  static ADMIN = 'ADMIN';
  static PHONE_DEBT_COLLECTOR = 'PHONE_DEBT_COLLECTOR';
  static PHONE_DEBT_COLLECTOR_1 = 'PHONE_DEBT_COLLECTOR_1';
  static PHONE_DEBT_COLLECTOR_2 = 'PHONE_DEBT_COLLECTOR_2';
  static FIELD_DEBT_COLLECTOR = 'FIELD_DEBT_COLLECTOR';
  static RESTRUCTURING_SPECIALIST = 'RESTRUCTURING_SPECIALIST';
  static RENUNCIATION_COORDINATOR = 'RENUNCIATION_COORDINATOR';
  static SECURITY_SPECIALIST = 'SECURITY_SPECIALIST';
  static PHONE_DEBT_COLLECTOR_MANAGER = 'PHONE_DEBT_COLLECTOR_MANAGER';
  static DIRECT_DEBT_COLLECTION_MANAGER = 'DIRECT_DEBT_COLLECTION_MANAGER';
  static ANALYST = 'ANALYST';
  static DEPARTMENT_MANAGER = 'DEPARTMENT_MANAGER';

  /**
   * Sprawdza czy podana rola to manager lub administrator
   * @param {string} role - rola
   * @returns {boolean} true, jeżeli podana rola to manager lub administrator, false w przeciwnym razie
   */
  static isAdminOrManager(role: string): boolean {
    return role === RoleType.ADMIN || role === RoleType.DIRECT_DEBT_COLLECTION_MANAGER || role === RoleType.PHONE_DEBT_COLLECTOR_MANAGER;
  }
}

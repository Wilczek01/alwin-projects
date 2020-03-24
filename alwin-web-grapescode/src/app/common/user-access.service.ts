import {Injectable} from '@angular/core';
import {RoleType} from '../issues/shared/role-type';

/**
 * Serwis do zarządzania dostępem użytkownika
 */
@Injectable()
export class UserAccessService {

  /**
   * Sprawdza czy podana rola użytkownika jest taka sama jak rola zalogowanego użytkownika na podstawie istniejących w pamięci danych
   * @param {string} role - sprawdzana rola
   * @returns {boolean} - true jeżeli podana rola jest taka sama jak rola zalogowanego użytkownika, false w przeciwnym wypadku
   */
  hasRole(role: string) {
    const currentUser = localStorage.getItem('currentUser');
    if (!!currentUser) {
      const user = JSON.parse(currentUser);
      return role.localeCompare(user.role) === 0;
    }
    return false;
  }

  /**
   * Sprawdza czy użytkownik jest managerem windykacji telefonicznej
   * @returns {boolean} - true jeżeli użytkownik jest managerem windykacji telefonicznej, false w przeciwnym wypadku
   */
  isPhoneDebtCollectorManager() {
    return this.hasRole(RoleType.PHONE_DEBT_COLLECTOR_MANAGER);
  }
}

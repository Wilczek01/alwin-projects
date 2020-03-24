import {RoleType} from '../../issues/shared/role-type';
import {Router} from '@angular/router';
import {
  AlwinLink,
  CONFIGURATION,
  DASHBOARD,
  DASHBOARD_PLACEHOLDER,
  DEMANDS_FOR_PAYMENT,
  FIELD_DEBT_ISSUES,
  HOLIDAYS, LOCATIONS,
  MANAGED_COMPANIES,
  MANAGED_ISSUES,
  MY_ISSUES,
  MY_WORK,
  SEGMENTS,
  TAGS, TERMINATIONS,
  USERS,
} from './alwin-link';

/**
 * Klasa odpowiedzialna za nawigację dla zalogowanego użytkownika
 */
export class NavigationUtil {

  /**
   * Mapa ze stroną home page trzymana pod kluczem typu operatora
   */
  private static roleToDefaultPage: Map<String, String> = new Map(
    [
      [RoleType.ADMIN, DASHBOARD.path],
      [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, SEGMENTS.path],
      [RoleType.PHONE_DEBT_COLLECTOR, SEGMENTS.path],
      [RoleType.PHONE_DEBT_COLLECTOR_1, SEGMENTS.path],
      [RoleType.PHONE_DEBT_COLLECTOR_2, SEGMENTS.path],
      [RoleType.DIRECT_DEBT_COLLECTION_MANAGER, MANAGED_ISSUES.path],
      [RoleType.FIELD_DEBT_COLLECTOR, FIELD_DEBT_ISSUES.path],
      [RoleType.RESTRUCTURING_SPECIALIST, DASHBOARD_PLACEHOLDER.path],
      [RoleType.RENUNCIATION_COORDINATOR, DASHBOARD_PLACEHOLDER.path],
      [RoleType.SECURITY_SPECIALIST, DASHBOARD_PLACEHOLDER.path],
      [RoleType.ANALYST, DASHBOARD_PLACEHOLDER.path],
      [RoleType.DEPARTMENT_MANAGER, DASHBOARD_PLACEHOLDER.path]
    ]
  );

  /**
   * Mapa z pozycjami w menu trzymana pod kluczem typu operatora
   * W pozycjach celowo pominięty jest home page, który wyświetla się jako ikona a nie zwykły link
   */
  private static ROLE_MENU: Map<String, Array<AlwinLink>> = new Map(
    [
      [RoleType.ADMIN, [USERS, HOLIDAYS, CONFIGURATION, LOCATIONS, DEMANDS_FOR_PAYMENT]],
      [RoleType.PHONE_DEBT_COLLECTOR_MANAGER, [MANAGED_ISSUES, MANAGED_COMPANIES, DEMANDS_FOR_PAYMENT, TERMINATIONS, HOLIDAYS, TAGS]],
      [RoleType.PHONE_DEBT_COLLECTOR, [MY_WORK, DASHBOARD, MY_ISSUES, DEMANDS_FOR_PAYMENT]],
      [RoleType.PHONE_DEBT_COLLECTOR_1, [MY_WORK, DASHBOARD, MY_ISSUES, DEMANDS_FOR_PAYMENT]],
      [RoleType.PHONE_DEBT_COLLECTOR_2, [MY_WORK, DASHBOARD, MY_ISSUES, DEMANDS_FOR_PAYMENT]],
      [RoleType.DIRECT_DEBT_COLLECTION_MANAGER, [MANAGED_COMPANIES, HOLIDAYS, TAGS, LOCATIONS, DEMANDS_FOR_PAYMENT]],
      [RoleType.FIELD_DEBT_COLLECTOR, [DEMANDS_FOR_PAYMENT]],
      [RoleType.RESTRUCTURING_SPECIALIST, [DEMANDS_FOR_PAYMENT]],
      [RoleType.RENUNCIATION_COORDINATOR, [DEMANDS_FOR_PAYMENT]],
      [RoleType.SECURITY_SPECIALIST, [DEMANDS_FOR_PAYMENT]],
      [RoleType.ANALYST, [DEMANDS_FOR_PAYMENT]],
      [RoleType.DEPARTMENT_MANAGER, [DEMANDS_FOR_PAYMENT]]
    ]
  );

  /**
   * Przekierowuje na domyślną stronę zalogowanego operatora
   * @param {Router} router - aktualna ścieżka
   * @param {String} role - rola zalogowanego operatora
   * @returns {boolean} - true jeżeli operator posiada domyślną ściężkę, false w przeciwnym razie
   */
  static defaultRedirectPage(router: Router, role: String) {
    const defaultPage = this.roleToDefaultPage.get(role);
    if (defaultPage != null) {
      router.navigate([defaultPage]);
      return true;
    }
    router.navigate(['/login']);
    return false;
  }

  /**
   * Zwraca stronę domową dla podanej roli operatora
   * @param {string} roleType - rola operatora
   * @returns {string} strona domowa dla operatora
   */
  static getHomePage(roleType: string): string {
    return NavigationUtil.roleToDefaultPage.get(roleType).substr(1);
  }

  /**
   * Pobiera listę pozycji w menu dla podanej roli operatora
   * @param {string} roleType - rola operatora
   * @returns {Array<AlwinLink>} pozycje w menu
   */
  static getMenu(roleType: string): Array<AlwinLink> {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    return NavigationUtil.ROLE_MENU.get(roleType).filter(menu => menu.isAllowed(currentUser.permission));
  }
}

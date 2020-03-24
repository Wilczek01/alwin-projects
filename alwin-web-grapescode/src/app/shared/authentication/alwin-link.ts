/**
 * Klasa reprezentująca odnośnik w menu aplikacji przeglądarkowej
 */
import {Permission} from '../../operator/permission';

export class AlwinLink {

  label: string;
  path: string;
  private readonly isAllowedFn: (permission: Permission) => boolean;

  constructor(label: string, path: string, isAllowedFn: (permission: Permission) => boolean) {
    this.label = label;
    this.path = path;
    this.isAllowedFn = isAllowedFn;
  }

  isAllowed(permission: Permission): boolean {
    return this.isAllowedFn(permission);
  }
}

const ACTIVE_BY_ROLE = (permission: Permission): boolean => true;

const ACTIVE_BY_DEMAND_PERMISSION = (permission: Permission): boolean => permission != null && permission.allowedToManageDemandsForPayment;


export const DASHBOARD = new AlwinLink('Zlecenia', '/dashboard', ACTIVE_BY_ROLE);
export const SEGMENTS = new AlwinLink('Segmenty', '/segments', ACTIVE_BY_ROLE);
export const MANAGED_ISSUES = new AlwinLink('Zlecenia', '/issues/manage', ACTIVE_BY_ROLE);
export const MANAGED_COMPANIES = new AlwinLink('Klienci', '/companies/manage', ACTIVE_BY_ROLE);
export const MY_ISSUES = new AlwinLink('Ostatnie', '/issues/my', ACTIVE_BY_ROLE);
export const DASHBOARD_PLACEHOLDER = new AlwinLink('', '/dashboard-placeholder', ACTIVE_BY_ROLE);
export const USERS = new AlwinLink('Użytkownicy', '/users', ACTIVE_BY_ROLE);
export const HOLIDAYS = new AlwinLink('Dni wolne', '/holidays', ACTIVE_BY_ROLE);
export const CONFIGURATION = new AlwinLink('Konfiguracja', '/configuration', ACTIVE_BY_ROLE);
export const TAGS = new AlwinLink('Etykiety', '/tags', ACTIVE_BY_ROLE);
export const MY_WORK = new AlwinLink('Pobierz', '/my-work', ACTIVE_BY_ROLE);
export const DEMANDS_FOR_PAYMENT = new AlwinLink('Wezwania', '/demands', ACTIVE_BY_DEMAND_PERMISSION);
export const TERMINATIONS = new AlwinLink('Wypowiedzenia', '/terminations', ACTIVE_BY_ROLE);
export const FIELD_DEBT_ISSUES = new AlwinLink('Zlecenia', '/issues/field-debt', ACTIVE_BY_ROLE);
export const LOCATIONS = new AlwinLink('Lokalizacja', '/locations', ACTIVE_BY_ROLE);

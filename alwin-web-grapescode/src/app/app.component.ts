import {AfterViewChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {RoleType} from './issues/shared/role-type';
import {UserAccessService} from './common/user-access.service';
import {routes} from './alwin-routing.module';
import {OperatorType} from './operator/operator-type';
import {environment} from '../environments/environment';
import {HandlingErrorUtils} from './issues/shared/utils/handling-error-utils';
import {VersionService} from './version/version.service';
import {MessageService} from './shared/message.service';
import {NavigationUtil} from './shared/authentication/navigation-util';
import {MenuService} from './shared/authentication/menu.service';
import {AlwinLink} from './shared/authentication/alwin-link';

@Component({
  selector: 'alwin-app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewChecked {
  role = RoleType;
  types: OperatorType[];
  restVersion: string;
  appVersion: string = environment.VERSION;
  menu: Array<AlwinLink> = [];

  constructor(private router: Router,
              private userAccessService: UserAccessService,
              private versionService: VersionService,
              private messageService: MessageService,
              private menuService: MenuService,
              private cdRef: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };

    this.router.events.subscribe((evt) => {
      if (evt instanceof NavigationEnd) {
        this.router.navigated = false;
        window.scrollTo(0, 0);
      }
    });
    this.loadVersion();
    this.loadAppMenu();
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }

  /**
   * Ładuje menu aplikacji dla zalogowanego użytkownika
   */
  private loadAppMenu() {
    this.menuService.menu$.subscribe(menu => {
      this.menu = menu;
    });
    if (this.menu.length === 0 && this.isLoggedIn()) {
      this.menuService.reloadMenu(JSON.parse(localStorage.getItem('currentUser')).role);
    }
  }

  /**
   * Sprawdza czy podana ścieżka jest aktualnie otwarta
   * @param {string} path - ścieżka
   * @returns {boolean} true jeżeli ścieżka jest otwarta, false w przeciwnym razie
   */
  isActive(path: string): boolean {
    return this.router.url === path;
  }

  /**
   * Pobiera wersję aplikacji backendowej wystawiającej serwisy rest
   */
  private loadVersion() {
    this.versionService.getVersion().subscribe(
      version => {
        this.restVersion = version.version;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
      }
    );
  }

  /**
   * Sprawdza czy użytkownik jest zalogowany weryfikując czy są zapisane w pamięci dane użytkownika
   * @returns {boolean} - true jeżeli w pamięci znajdują się dane użytkownika, false w przeciwnym wypadku
   */
  isLoggedIn() {
    return !!localStorage.getItem('currentUser');
  }

  /**
   * Sprawdza czy zalogowany użytkownik ma choć jedną z podanych ról
   * @param {string} roles - sprawdzane role
   * @returns {boolean} - true jeżeli zalogowany użytkownik ma choć jedną z podanych ról, false w przeciwnym wypadku
   */
  hasAtLeastOneRole(roles: string[]) {
    let hasRole = false;
    if (roles === null) {
      return hasRole;
    }
    roles.forEach(
      role => {
        hasRole = hasRole || this.userAccessService.hasRole(role);
      }
    );
    return hasRole;
  }

  /**
   * Sprawdza czy użytkownik ma prawo wyświetlić stronę
   * @param {string} path - ścieżka do strony
   * @returns {boolean} - true jeżeli zalogowany użytkownik ma prawo oglądać stronę, false w przeciwnym wypadku
   */
  canActivate(path: string): boolean {
    const routePath = routes.find(route => route.path === path);
    if (routePath == null || routePath.data == null) {
      return false;
    }
    return this.hasAtLeastOneRole(routePath.data.roles);
  }

  /**
   * Pobiera imię i nazwisko użytkownika
   * @returns {string} - imię i nazwisko użytkownika
   */
  getCurrentUserNameDetail(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser == null) {
      return '';
    }
    return currentUser.username;
  }

  /**
   * Pobiera nazwę roli użytkownika
   * @returns {string} - nazwa roli użytkownika
   */
  getCurrentUserRole(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser === null) {
      return '';
    }
    return currentUser.roleLabel;
  }

  /**
   * Określa czy powinno ukryć się górną belke z menu
   * @returns {boolean} - true, jeżeli należy ukryć menu, false w przeciwnym razie
   */
  hideTabs(): boolean {
    return !this.isLoggedIn() || this.router.routerState.snapshot.url === '/changePassword';
  }

  /**
   * Pobiera stronę domową dla zalogowanego operatora
   * @returns {string} - adres strony domowej zalogowanego operatora
   */
  getHomePage(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser == null) {
      return '';
    }
    return NavigationUtil.getHomePage(currentUser.role as string);
  }
}

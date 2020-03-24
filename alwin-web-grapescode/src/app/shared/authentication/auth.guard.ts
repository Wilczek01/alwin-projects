import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {NavigationUtil} from './navigation-util';

/**
 * Ochrona przed wejściem na stronę jeżeli operator nie jest do tego uprawniony
 */
@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {
  }

  /**
   * Sprawdza czy operator ma uprawienia do otworzenia podanej ścieżki
   *
   * @param {ActivatedRouteSnapshot} route - aktualna ścieżka
   * @param {RouterStateSnapshot} state - stan ścieżki
   * @returns {boolean} true jeżeli użytkownik ma dostęp do podanej ścieżki, false w przeciwnym razie
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
      const user = JSON.parse(currentUser);
      if (user.forceUpdatePassword) {
        this.router.navigate(['/changePassword']);
        return false;
      }
      const roles = route.data['roles'] as Array<string>;
      if (roles == null || roles.indexOf(user.role) !== -1) {
        return true;
      } else {
        return NavigationUtil.defaultRedirectPage(this.router, user.role);
      }
    }
    this.router.navigate(['/login']);
    return false;
  }

}

import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

/**
 * Ochrona ścieżki odpowiedzialna za weryfikację czy użytkownik powinien zmienić hasło
 */
@Injectable()
export class ChangePasswordGuard implements CanActivate {

  constructor(private router: Router) {
  }

  /**
   * Sprawdza czy użytkownik powinien zmienić hasło, jeżeli nie to zostaje przekierowany na stronę główną
   *
   * @param {ActivatedRouteSnapshot} route - aktualna ściezka
   * @param {RouterStateSnapshot} state - stan ściezki
   * @returns {boolean} true jeżeli operator powinien zmienić hasło, false w przeciwnym wypadku
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
      const user = JSON.parse(currentUser);
      if (user.forceUpdatePassword) {
        return true;
      }
    }
    this.router.navigate(['/']);
    return false;
  }

}

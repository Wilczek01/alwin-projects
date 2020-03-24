import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {HttpClient} from '@angular/common/http';
import {LoginResponse} from './login-response';
import {EnvironmentSpecificService} from '../environment-specific.service';
import {EnvSpecific} from '../env-specific';
import {MenuService} from './menu.service';
import { map } from 'rxjs/operators';
/**
 * Serwis uwierzytelniajacy operatora
 */
@Injectable()
export class AuthenticationService {
  public token: string;
  alwinBaseApiUrl: string;

  constructor(private http: HttpClient, envSpecificSvc: EnvironmentSpecificService, private menuService: MenuService) {
    envSpecificSvc.subscribe(this, this.setLink);
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
  }

  /**
   * Ustawia podstawową cześć ścieżki do serwisów backednowych
   * @param caller
   * @param {EnvSpecific} es
   */
  setLink(caller: any, es: EnvSpecific) {
    const thisCaller = caller as AuthenticationService;
    thisCaller.alwinBaseApiUrl = es.url;
  }

  /**
   * Loguje operatora
   * @param {string} username - login operatora
   * @param {string} password - hasło operatora
   * @returns {Observable<boolean>} - true jeżeli udało się zalogować operatora, false w przeciwnym razie
   */
  login(username: string, password: string): Observable<boolean> {
    return this.http.post<LoginResponse>(this.alwinBaseApiUrl + 'users/login', {
      username,
      password
    }, {observe: 'response'}).pipe(
      map(response => {
          const headers = response.headers;
          const authToken = headers.get('Authorization');
          if (authToken) {
            this.token = authToken;
            localStorage.setItem('currentUser', JSON.stringify({
              username: response.body.username,
              role: response.body.role.typeName,
              roleLabel: response.body.role.typeLabel,
              forceUpdatePassword: response.body.forceUpdatePassword,
              permission: response.body.permission,
              token: authToken
            }));
            this.menuService.reloadMenu(response.body.role.typeName);
            return true;
          } else {
            this.menuService.clearMenu();
            return false;
          }
        }
      ));
  }

  /**
   * Wylogowuje użytkownika
   */
  logout(): void {
    this.menuService.clearMenu();
    this.token = null;
    localStorage.removeItem('currentUser');
  }

}

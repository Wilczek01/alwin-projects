import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {AlwinLink} from './alwin-link';
import {NavigationUtil} from './navigation-util';

/**
 * Serwis do obslugi zmiany wyświetlanego menu
 */
@Injectable()
export class MenuService {

  private menu = new Subject<Array<AlwinLink>>();

  menu$ = this.menu.asObservable();

  /**
   * Ładuje menu dla operatora o podanej roli
   * @param {string} role
   */
  reloadMenu(role: string) {
    this.menu.next(NavigationUtil.getMenu(role));
  }

  /**
   * Czyści załadowane menu
   */
  clearMenu() {
    this.menu.next([]);
  }
}

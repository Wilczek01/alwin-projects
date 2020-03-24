import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {AllWallets} from './all-wallets';

/**
 * Serwisu dostępu do usług REST dla portfeli
 */
@Injectable()
export class WalletService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobieranie wszystkich portfeli prezentowanych na stronie segmentów
   * Portfele z otwartych zleceń dla zleceń typu:
   * -> 'Windykacja telefoniczna sekcja 1'
   * -> 'Windykacja telefoniczna sekcja 2
   * Portfel dla zleceń czekających na zamknięcie
   * Portfele dla etykiet przypisanych do otwartych zleceń
   *
   * @returns {Observable<AllWallets>} wszystkie portfele prezentowane na stronie segmentów
   */
  getAllWallets(): Observable<AllWallets> {
    return this.http.get<AllWallets>('wallets/all');
  }

}

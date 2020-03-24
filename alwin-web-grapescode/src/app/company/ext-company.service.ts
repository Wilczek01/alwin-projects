import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Page} from 'app/shared/pagination/page';
import {ExtCompany} from './ext-company';
import {Observable} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {Balance} from './balance';

/**
 * Serwisu dostępu do usług REST dla firm (dane z AIDA)
 */
@Injectable()
export class ExtCompanyService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera stronicowaną listę firm
   * @param {string} firstResult - pierwszy element na stronie
   * @param {string} maxResult - maksymalna ilość firm na stronie
   * @param {HttpParams} searchParams - parametry filtrowania
   * @returns {Observable<Page<ExtCompany>>} - strona z listą firm
   */
  public findCompanies(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<ExtCompany>> {
    let params = new HttpParams();
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResult);

    if (searchParams != null) {
      searchParams.keys().forEach(key => {
        searchParams.getAll(key).forEach(param => {
          params = params.append(key, param);
        });
      });
    }

    return this.http.getWithQuery<Page<ExtCompany>>('aidaCompanies', params);
  }

  /**
   * Pobiera aktualne saldo dla firmy
   * @param {number} extCompanyId - identyfikator firmy dla której sprawdzamy saldo
   * @returns {Observable<Balance>} - Opakowanwa wartość liczbowa reprezentująca saldo firmy
   */
  getCurrentCompanyBalance(extCompanyId: number): Observable<Balance> {
    return this.http.get<Balance>(`balances/company/${extCompanyId}`);
  }

  /**
   * Pobranie danych firmy
   * @param extCompanyId - identyfikator firmy w AIDA
   */
  findCompany(extCompanyId: string): Observable<ExtCompany> {
    return this.http.get<ExtCompany>(`aidaCompanies/${extCompanyId}`);
  }
}

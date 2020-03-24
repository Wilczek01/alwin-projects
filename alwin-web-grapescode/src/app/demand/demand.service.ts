import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Page} from 'app/shared/pagination/page';
import {Observable} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {DemandForPayment} from './model/demand-for-payment';
import {ProcessDemandsForPayment} from './model/process-demands-for-payment';
import {CreateDemandForPayment} from './model/create-demand-for-payment';
import {DemandCancelling} from './model/demand-cancelling';

/**
 * Serwisu dostępu do usług REST dla wezwań do zapłaty
 */
@Injectable()
export class DemandService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera stronicowaną listę wezwań do zapłaty
   * @param {string} firstResult - pierwszy element na stronie
   * @param {string} maxResult - maksymalna ilość wezwań do zapłaty na stronie
   * @param {HttpParams} searchParams - parametry filtrowania
   * @returns {Observable<Page<DemandForPayment>>} - strona z listą wezwań do zapłaty
   */
  findDemandsForPayment(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<DemandForPayment>> {
    let params = new HttpParams();
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResult);

    params = this.appendSearchParams(searchParams, params);

    return this.http.getWithQuery<Page<DemandForPayment>>('demands', params);
  }

  /**
   * Pobiera stronę z całą listę wezwań do zapłaty
   * @param {HttpParams} searchParams - parametry filtrowania
   * @returns {Observable<Page<DemandForPayment>>} - strona z całą listą wezwań do zapłaty
   */
  findAllDemandsForPayment(searchParams: HttpParams): Observable<Page<DemandForPayment>> {
    return this.http.getWithQuery<Page<DemandForPayment>>('demands', searchParams);
  }

  /**
   * Przekazuje do wysłania i usunięcia wybrane sugestie wezwań do zapłaty
   * @param demandsToProcess - wybrane sugestie wezwań do zapłaty
   */
  processDemands(demandsToProcess: ProcessDemandsForPayment) {
    return this.http.postObserve('demands/process', demandsToProcess);
  }

  /**
   * Tworzy ręcznie sugestię wezwania do zapłaty
   * @param demand
   */
  createDemandManually(demand: CreateDemandForPayment) {
    return this.http.postObserve('demands', demand);
  }

  /**
   * Anulowanie wezwania windykacyjnego
   * @param {number} demandId - Identyfikator wezwania
   * @param {DemandCancelling} demandCancelling - Parametry wezwania do anulowania
   * @returns {any} odpowiedź z poprawnym kodem http 204 lub błędem walidacji
   */
  demandCancelation(demandId: number, demandCancelation: DemandCancelling): Observable<any> {
    return this.http.postObserve(`demands/cancel/${demandId}`, demandCancelation);
  }

  /**
   * Pobranie listy wezwań w formacie text/csv na potrzeby eksportu do pliku csv
   * @param {HttpParams} searchParams - parametry wyszukiwania
   * @returns {Observable<any>} - pozycje faktury w formacie csv
   */
  getCSVDemands(searchParams: HttpParams): any {
    let params = new HttpParams();
    params = this.appendSearchParams(searchParams, params);
    return this.http.getWithQueryWithTextResponseType<any>('demands/report', params, 'text');
  }

  private appendSearchParams(searchParams: HttpParams, params: HttpParams) {
    if (searchParams != null) {
      searchParams.keys().forEach(key => {
        searchParams.getAll(key).forEach(param => {
          params = params.append(key, param);
        });
      });
    }
    return params;
  }

}

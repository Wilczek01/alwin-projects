import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {Operator} from '../../operator/operator';
import {OperatorType} from '../../operator/operator-type';
import {HttpParams} from '@angular/common/http';
import {ChangePasswordRequest} from '../../operator/change-password-request';
import {Page} from '../../shared/pagination/page';

/**
 * Serwis dostępu do usług REST zarządzających operatorami
 */
@Injectable()
export class OperatorService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera listę operatorów podwładnych dla zalogowanego operatora
   *
   * @returns {Observable<Operator[]>} - lista operatorów
   */
  findFieldOperators(firstResult: string, maxResults: string): Observable<Page<Operator>> {
    let params = new HttpParams();
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResults);
    return this.http.getWithQuery<Page<Operator>>('operators/fields', params);
  }

  /**
   * Pobiera listę operatorów podwładnych dla zalogowanego operatora
   *
   * @returns {Observable<Operator[]>} - lista operatorów
   */
  getManagedOperators(): Observable<Operator[]> {
    return this.http.get<Operator[]>('operators/managed/');
  }

  /**
   * Pobiera listę operatorów podwładnych dla zalogowanego operatora
   * Typ zwracanych operatorów pasuje do przekazanego typu zlecenia
   *
   * @returns {Observable<Operator[]>} - lista operatorów
   */
  getManagedOperatorsByIssueType(issueTypeId: number): Observable<Operator[]> {
    const params = new HttpParams().append('issueTypeId', String(issueTypeId));
    return this.http.getWithQuery<Operator[]>('operators/managed/', params);
  }

  /**
   * Pobiera listę operatorów podwładnych dla zalogowanego operatora
   *
   * @returns {Observable<Operator[]>} - lista operatorów
   */
  getManagedOperatorsForIssues(issues: number[], searchParams: HttpParams): Observable<Operator[]> {
    return this.http.postWithQuery<Operator[]>('operators/managed/issues', {ids: issues}, searchParams);
  }

  /**
   * Pobiera listę opiekunów klientów
   *
   * @returns {Observable<Operator[]>} - lista opiekunów klientów
   */
  getAccountManagers(): Observable<Operator[]> {
    return this.http.get<Operator[]>('operators/accountManagers');
  }

  /**
   * Pobiera listę operatorów przypisanych do czynności jakiegokolwiek zlecenia dla podanego klienta
   *
   * @param {number} companyId - identyfikator klienta
   * @returns {Observable<Operator[]>} - lista operatorów
   */
  getCompanyActivitiesOperators(companyId: number): Observable<Operator[]> {
    return this.http.get<Operator[]>(`operators/company/${companyId}/activities`);
  }

  /**
   * Usuwa przypisanego operatora dla wybranych lub wszystkich zleceń, które spełniają przekazane filtry
   *
   * @param {number[]} issues - identyfikatory wybranych zleceń
   * @param {HttpParams} searchParams - parametry filtrowania zleceń
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź z poprawnym kodem http aktualizacji 202
   */
  unassignIssues(issues: number[], searchParams: HttpParams) {
    return this.http.patchWithQuery('operators/unassignment', {ids: issues}, searchParams);
  }

  /**
   * Przypisuje do operatora wybrane lub wszystkie zlecenia, które spełniają przekazane filtry
   *
   * @param {number} operatorId - identyfikator wybranego operatora
   * @param {number[]} issues - identyfikatory wybranych zleceń
   * @param {HttpParams} searchParams - parametry filtrowania zleceń
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź z poprawnym kodem http aktualizacji 202
   */
  assignIssues(operatorId: number, issues: number[], searchParams: HttpParams) {
    return this.http.patchWithQuery(`operators/${operatorId}/assignment`, {ids: issues}, searchParams);
  }

  /**
   * Pobiera listę możliwych typów operatorów
   *
   * @returns {Observable<Object>} - lista typów
   */
  getOperatorTypes() {
    return this.http.get<OperatorType[]>('operators/types');
  }

  /**
   * Resetuje hasło na podane
   *
   * @param {number} operatorId - identyfikator operatora
   * @param {string} newPassword - nowe hasło
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź z poprawnym kodem http aktualizacji 202
   */
  resetPassword(operatorId: number, newPassword: string) {
    return this.http.patch(`operators/password/${operatorId}`, new ChangePasswordRequest(newPassword));
  }

  /**
   * Zmienia hasło zalogowanego użytkownika na podane, jeżeli został do tego zmuszony
   *
   * @param {string} newPassword - nowe hasło
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź z poprawnym kodem http aktualizacji 202
   */
  changePassword(newPassword: string) {
    return this.http.patch('operators/password/change/', new ChangePasswordRequest(newPassword));
  }

}

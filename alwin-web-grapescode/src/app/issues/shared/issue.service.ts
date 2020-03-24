import {Injectable} from '@angular/core';
import {HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Issue} from './issue';
import {Page} from '../../shared/pagination/page';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {IssueType} from './issue-type';
import {CreateIssueRequest} from './create-issue-request';
import {KeyLabel} from '../../shared/key-label';
import {FindActiveIssueResponse} from './find-active-issue-response';
import {IssueInvoiceExclusion} from './issue-invoice-exclusion';
import {AuditLogEntry} from './audit-log-entry';
import {IssueTypeConfiguration} from './issue-type-configuration';
import {BalanceStartAndAdditionalCurrency} from '../../company/balance-start-and-additional-currency';
import {OperatedIssue} from './operated-issue';
import {IssueForFieldDebtCollector} from './issue-for-field-debt-collector';
import { map } from 'rxjs/operators';

@Injectable()
export class IssueService {

  constructor(private http: AlwinHttpService) {
  }

  getIssues(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<Issue>> {
    return this.getSelectedIssues('issues', firstResult, maxResult, searchParams);
  }

  getFieldDebtCollectorIssues(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<IssueForFieldDebtCollector>> {
    return this.http.getWithQuery<Page<IssueForFieldDebtCollector>>('issues/fieldDebt', this.buildParams(firstResult, maxResult, searchParams));
  }

  getMyNotClosedIssues(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<Issue>> {
    return this.getSelectedIssues('issues/my', firstResult, maxResult, searchParams);
  }

  getManagedIssues(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<Issue>> {
    return this.getSelectedIssues('issues/managed', firstResult, maxResult, searchParams);
  }

  getCompanyIssues(firstResult: string, maxResult: string, companyId: string, excludedIssueId: string, searchParams: HttpParams): Observable<Page<Issue>> {
    if (searchParams != null) {
      searchParams = searchParams.append('excludedIssueId', excludedIssueId);
    } else {
      searchParams = new HttpParams();
      searchParams = searchParams.append('excludedIssueId', excludedIssueId);
    }
    return this.getSelectedIssues(`issues/company/${companyId}`, firstResult, maxResult, searchParams);
  }

  private getSelectedIssues(path: string, firstResult: string, maxResult: string, searchParams: HttpParams) {
    return this.http.getWithQuery<Page<Issue>>(path, this.buildParams(firstResult, maxResult, searchParams));
  }

  private buildParams(firstResult: string, maxResult: string, searchParams: HttpParams) {
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
    return params;
  }

  /**
   * Pobieranie zlecenia z aktualizacją salda po identyfikatorze z informacją czy może być edytowane przez zalogowanego operatora
   *
   * @param {number} issueId - identyfikator zlecenia
   * @returns {Observable<OperatedIssue>} zlecenie z informację o możliwości edycji
   */
  getIssueById(issueId: number): Observable<OperatedIssue> {
    return this.http.patch<OperatedIssue>(`issues/${issueId}`, null).pipe(
      map(response => {
        if (response.status === 200) {
          return response.body;
        }
        return null;
      })
    );
  }

  getMyWork(): Observable<Issue> {
    return this.http.postObserve<Issue>('issues/work', null).pipe(
      map(response => {
        if (response.status === 204) {
          return null;
        } else {
          return response.body;
        }
      })
    );
  }

  /**
   * Pobiera wszystkie typy zleceń windykacyjnych
   *
   * @returns {Observable<IssueType[]>} lista typów zleceń windykacyjnych
   */
  getAllIssueTypes(): Observable<IssueType[]> {
    return this.http.get<IssueType[]>('issues/types');
  }

  /**
   * Pobiera wszystkie typy zleceń windykacyjnych dla których zalogowany użytkownik ma uprawnienia
   *
   * @returns {Observable<IssueType[]>} lista typów zleceń windykacyjnych
   */
  getMyIssueTypes(): Observable<IssueType[]> {
    return this.http.get<IssueType[]>('issues/types/my');
  }

  getIssuePriorities(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('issues/priorities');
  }

  updateIssuesPriority(priorityKey: string, issues: number[], searchParams: HttpParams) {
    return this.http.patchWithQuery(`issues/priority/${priorityKey}`, {ids: issues}, searchParams);
  }

  getIssueStates(onlyActive: boolean): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>(`issues/states?onlyActive=${onlyActive}`);
  }

  createIssue(extCompanyId: number, issueTypeId: number, expirationDate: Date, assigneeId: number): any {
    const createIssueRequest = new CreateIssueRequest();
    createIssueRequest.issueTypeId = issueTypeId;
    createIssueRequest.extCompanyId = extCompanyId;
    createIssueRequest.expirationDate = expirationDate;
    createIssueRequest.assigneeId = assigneeId;
    return this.http.post('issues', createIssueRequest);
  }

  getCompanyActiveIssueId(extCompanyId: number): Observable<FindActiveIssueResponse> {
    return this.http.get(`issues/active/company/${extCompanyId}`);
  }

  /**
   * Aktualizacja salda zlecenia oraz sald faktur
   * @param {number} issueId - identyfikator zlecenia
   * @returns {Observable<HttpResponse<Object>>}
   */
  updateBalances(issueId: number) {
    return this.http.patch(`balances/issue/${issueId}`, null);
  }

  /**
   * Wykluczanie i przywracanie faktury w obsłudze zlecenia
   * @param issueInvoiceExclusion - dane do wykluczania faktury
   * @returns odpowiedź z poprawnym kodem http aktualizacji 202
   */
  updateIssueInvoiceExclusion(issueInvoiceExclusion: IssueInvoiceExclusion): any {
    return this.http.patch('issues/exclusion', issueInvoiceExclusion);
  }

  /**
   * Przypisuje podaną listę etykiet do zlecenia o podanym identyfikatorze
   *
   * @param {number} issueId - identyfikator zlecenia
   * @param {number[]} tagIds - identyfikatory etykiet
   * @returns {Observable<HttpResponse<Object>>}
   */
  updateIssueTags(issueId: number, tagIds: number[]) {
    return this.http.patch(`issues/${issueId}/tags`, {ids: tagIds});
  }

  /**
   * Przypisuje podaną listę etykiet do zleceń o podanych identyfikatorach, lub do wszystkich zleceń o podanych filtrach, jeżeli wartość updateAll wynosi true
   *
   * @param {number[]} issueIds - identyfikatory zleceń
   * @param {number[]} tagIds - identyfikatory etykiet do przypisania
   * @param {boolean} updateAll - czy przypisać do wszystkich odfiltrowanych zleceń
   * @param {HttpParams} searchParams - kryteria filtrowania
   * @returns {Observable<HttpResponse<Object>>} odpowiedź z poprawnym kodem http aktualizacji 202
   */
  updateIssuesTags(issueIds: number[], tagIds: number[], updateAll: boolean, searchParams: HttpParams) {
    if (updateAll) {
      return this.http.patchWithQuery('issues/tags', {idsToAssign: tagIds}, searchParams);
    }

    return this.http.patch('issues/tags', {ids: issueIds, idsToAssign: tagIds});
  }

  getIssueAuditLogEntries(issueId: number): Observable<AuditLogEntry[]> {
    return this.http.get<AuditLogEntry[]>(`audit/issue/${issueId}`);
  }

  getIssueTypeConfiguration(): Observable<IssueTypeConfiguration[]> {
    return this.http.get<IssueTypeConfiguration[]>('issues/configurations/');
  }

  /**
   * Aktualizuje istniejącą konfigurację zlecenia
   * @param {IssueTypeConfiguration} configuration - zaktualizowane szczegóły konfiguracji
   * @param {number} configurationId - identyfikator konfiguracji
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateIssueTypeConfiguration(configuration: IssueTypeConfiguration, configurationId: number) {
    return this.http.patch(`issues/configuration/${configurationId}`, configuration);
  }

  /**
   * Pobranie salda startowego oraz dodatkowego
   * @param {number} issueId - identyfikator zlecenia
   */
  getIssueStartAndAdditionalBalance(issueId: number): Observable<BalanceStartAndAdditionalCurrency> {
    return this.http.get<BalanceStartAndAdditionalCurrency>(`balances/issue/${issueId}/overall`);
  }
}

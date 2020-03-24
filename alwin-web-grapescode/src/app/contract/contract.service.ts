import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Observable} from 'rxjs';
import {ContractWithSubjectsAndSchedules} from './contract-with-subjects-and-schedules';
import {ContractFinancialSummary} from './contract-financial-summary';
import {HttpParams} from '@angular/common/http';
import {SimpleContract} from './simple-contract';
import {ContractWithExclusions} from './contract-with-exclusions';
import {ExclusionMapper} from '../company/exclusion.mapper';
import {ContractSubject} from './contract-subject';
import {ContractWithFormalDebtCollectionExclusions} from './contract-with-formal-debt-collection-exclusions';
import { map } from 'rxjs/operators';
/**
 * Serwisu dostępu do usług REST dla umów
 */
@Injectable()
export class ContractService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobranie umów firmy wraz z przedmiotami oraz harmonogramami
   * @param {number} extCompanyId - identyfikator firmy w systemie zewnętrznym
   * @returns {Observable<ContractWithSubjectsAndSchedules[]>} lista konraktów wraz z przedmiotami i harmonogramami
   */
  getContractsWithSubjectsAndSchedules(extCompanyId: number): Observable<ContractWithSubjectsAndSchedules[]> {
    return this.http.get<ContractWithSubjectsAndSchedules[]>(`contracts/company/${extCompanyId}`);
  }

  /**
   * Pobranie zestawień finansowych umów
   * @param {number} issueId - identyfikator zlecenia
   * @param {boolean} notPaidOnly - czy pobierać tyklko nieopłacone umowy
   * @param {boolean} overdueOnly - czy pobierać tylko umowy z przekroczonym terminem płatności
   * @returns {Observable<ContractFinancialSummary[]>} lista zestawień finansowych per umowa
   */
  getContractFinancialSummaries(issueId: number, overdueOnly: boolean, notPaidOnly: boolean): Observable<ContractFinancialSummary[]> {
    let params: HttpParams = new HttpParams();
    if (overdueOnly) {
      params = params.append('overdueOnly', 'true');
    }
    if (notPaidOnly) {
      params = params.append('notPaidOnly', 'true');
    }
    return this.http.getWithQuery<ContractFinancialSummary[]>(`contracts/issue/${issueId}/summary`, params);
  }

  /**
   * Pobranie umów firmy z blokadami w widykacji nieformalnej
   * @param {string} extCompanyId - identyfikator firmy w systemie zewnętrznym
   * @returns {Observable<SimpleContract[]>} lista umów
   */
  getSimpleContracts(extCompanyId: string): Observable<ContractWithExclusions[]> {
    return this.http.get<ContractWithExclusions[]>(`contracts/simple/company/${extCompanyId}`).pipe(
      map(response => ExclusionMapper.mapContractWithExclusions(response)
    ));
  }

  /**
   * Pobranie umów firmy z blokadami w widykacji formalnej
   * @param {string} extCompanyId - identyfikator firmy w systemie zewnętrznym
   * @returns {Observable<SimpleContract[]>} lista umów
   */
  getSimpleFormalDebtCollectionContracts(extCompanyId: string): Observable<ContractWithFormalDebtCollectionExclusions[]> {
    return this.http.get<ContractWithFormalDebtCollectionExclusions[]>(`contracts/simple/company/${extCompanyId}/formalDebtCollection`).pipe(
      map(response => ExclusionMapper.mapContractWithFormalDebtCollectionExclusions(response))
    );
  }

  /**
   * Pobranie przedmiotów dla umów po numerze umowy
   * @param {string} contractNo - numer umowy
   * @returns {Observable<ContractSubject[]>} lista przedmiotów
   */
  getContractSubjects(contractNo: string): Observable<ContractSubject[]> {
    let params: HttpParams = new HttpParams();
    params = params.append('contractNo', contractNo);
    return this.http.getWithQuery<ContractSubject[]>('contracts/subjects/', params);
  }
}

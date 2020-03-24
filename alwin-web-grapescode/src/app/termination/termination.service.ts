import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Observable, Subject} from 'rxjs';
import {Termination} from './model/termination';
import {ProcessTerminations} from './model/process-terminations';
import {Page} from '../shared/pagination/page';
import {HttpParams} from '@angular/common/http';
import {NewTerminationContract} from './model/new-termination-contract';
import {Person} from '../customer/shared/person';
import {ActivateContractTermination} from './model/activate-contract-termination';

/**
 * Serwisu dostępu do usług REST dla wypowiedzeń
 */
@Injectable()
export class TerminationService {

  private terminationContract = new Subject<NewTerminationContract>();
  terminationContract$ = this.terminationContract.asObservable();

  private refreshSelection = new Subject<NewTerminationContract>();
  refreshSelection$ = this.refreshSelection.asObservable();

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Przekazuje umowę do wypowiedzenia do dopasowania do grupy
   * @param terminationContract - umowa do wypowiedzenia
   */
  applyContractToTerminationGroup(terminationContract: NewTerminationContract) {
    this.terminationContract.next(terminationContract);
  }

  /**
   * Uruchamia wymuszenie odświeżenia checkboxów z zaznaczeniem wszystkich wypowiedzeń
   */
  refreshTerminationsSelection() {
    this.refreshSelection.next();
  }

  /**
   * Pobiera listę sugestii wypowiedzeń
   * @returns {Observable<Termination[]>} - sugestie wypowiedzeń do zatwierdzenia przez operatora
   */
  findContractTerminationsToOperate(): Observable<Termination[]> {
    return this.http.get<Termination[]>('contractTerminations/open');
  }

  /**
   * Przekazuje do wysłania, odroczenia i odrzucenia wybrane sugestie wypowiedzeń
   * @param terminationsToProcess - wybrane sugestie wypowiedzeń
   */
  processTerminations(terminationsToProcess: ProcessTerminations) {
    return this.http.postObserve(`contractTerminations/process`, terminationsToProcess);
  }

  /**
   * Pobiera stronicowaną listę wypowiedzeń według podanych filtrowania
   * @param {string} firstResult - pierwszy element na stronie
   * @param {string} maxResult - maksymalna ilość wypowiedzeń na stronie
   * @param {HttpParams} searchParams - parametry filtrowania
   * @returns {Observable<Page<Termination>>} - strona z listą wypowiedzeń
   */
  findTerminations(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<Termination>> {
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
    return this.http.getWithQuery<Page<Termination>>('contractTerminations', params);
  }

  /**
   * Oznaczanie umowy jako aktywowaną
   * @param {number} contractTerminationId - Identyfikator wypowiedzenia umowy
   * @param {Person} activateContractTermination - Parametry aktywacji umowy dla wypowiedzenia
   * @returns {any} odpowiedź z poprawnym kodem http 204 lub błędy walidacji
   */
  activateContract(contractTerminationId: number, activateContractTermination: ActivateContractTermination): Observable<any> {
    return this.http.postObserve(`contractTerminations/${contractTerminationId}/activate`, activateContractTermination);
  }

}

import {Injectable} from '@angular/core';
import {CustomerOutOfService} from './manage/model/customer-out-of-service';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {ContractOutOfService} from './manage/model/contract-out-of-service';
import {ExclusionMapper} from './exclusion.mapper';
import {ContractOutOfServiceInput} from './manage/model/contract-out-of-service-input';
import {CustomerOutOfServiceInput} from './manage/model/customer-out-of-service-input';
import {HttpParams} from '@angular/common/http';
import {KeyLabel} from '../shared/key-label';
import {FormalDebtCollectionCustomerOutOfService} from './manage/model/formal-debt-collection-customer-out-of-service';
import {FormalDebtCollectionCustomerOutOfServiceInput} from './manage/model/formal-debt-collection-customer-out-of-service-input';
import {FormalDebtCollectionContractOutOfServiceInput} from './manage/model/formal-debt-collection-contract-out-of-service-input';
import { map } from 'rxjs/operators';
/**
 * Serwisu dostępu do usług REST dla klientów
 */
@Injectable()
export class CompanyService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera aktualne blokady w windykacji nieformalnej dla klienta o podanym identyfikatorze z zewnętrznego systemu
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @returns {Observable<CustomerOutOfService[]>} - lista blokad klienta
   */
  getCustomersOutOfService(extCompanyId: string): Observable<CustomerOutOfService[]> {
    return this.http.get<CustomerOutOfService[]>(`customers/blocked/${extCompanyId}`).pipe(
      map(response => ExclusionMapper.mapCustomerOutOfService(response)
    ));
  }

  /**
   * Pobiera aktualne blokady w windykacji formalnej dla klienta o podanym identyfikatorze z zewnętrznego systemu
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @returns {Observable<CustomerOutOfService[]>} - lista blokad klienta
   */
  getFormalDebtCollectionCustomerOutOfService(extCompanyId: string): Observable<FormalDebtCollectionCustomerOutOfService[]> {
    return this.http.get<FormalDebtCollectionCustomerOutOfService[]>(`customers/blocked/${extCompanyId}/formalDebtCollection`).pipe(
      map(response => ExclusionMapper.mapFormalDebtCollectionCustomerOutOfService(response)
    ));
  }

  /**
   * Usuwa wybraną blokadę klienta w windykacji nieformalnej
   * @param {number} exclusionId  - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteCustomersOutOfService(exclusionId: number) {
    return this.http.delete(`customers/block/${exclusionId}`);
  }

  /**
   * Usuwa wybraną blokadę klienta w windykacji formalnej
   * @param {number} exclusionId  - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteFormalDebtCollectionCustomersOutOfService(exclusionId: number) {
    return this.http.delete(`customers/block/${exclusionId}/formalDebtCollection`);
  }

  /**
   * Usuwa wybraną blokadę umowy klienta w widnykacji nieformalnej
   * @param {string} extCompanyId  - identyfikator klienta z zewnętrznego systemu
   * @param {number} exclusionId  - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteContractOutOfService(extCompanyId: string, exclusionId: number) {
    return this.http.delete(`customers/block/${extCompanyId}/contract/${exclusionId}`);
  }

  /**
   * Usuwa wybraną blokadę umowy klienta w windykacji formalnej
   * @param {string} extCompanyId  - identyfikator klienta z zewnętrznego systemu
   * @param {number} exclusionId  - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteFormalDebtCollectionContractOutOfService(extCompanyId: string, exclusionId: number) {
    return this.http.delete(`customers/block/${extCompanyId}/contract/${exclusionId}/formalDebtCollection`);
  }

  /**
   * Tworzy blokadę dla klienta o podanym identyfikatorze w windykacji nieformalnej
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @param {CustomerOutOfServiceInput} exclusion - szczegóły blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem zapisu 201
   */
  addCustomerOutOfService(extCompanyId: string, exclusion: CustomerOutOfServiceInput) {
    return this.http.postObserve(`customers/block/${extCompanyId}`, exclusion);
  }

  /**
   * Tworzy blokadę dla klienta o podanym identyfikatorze w windykacji formalnej
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @param {CustomerOutOfServiceInput} exclusion - szczegóły blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem zapisu 201
   */
  addFormalDebtCollectionCustomerOutOfService(extCompanyId: string, exclusion: FormalDebtCollectionCustomerOutOfServiceInput) {
    return this.http.postObserve(`customers/block/${extCompanyId}/formalDebtCollection`, exclusion);
  }

  /**
   * Tworzy blokadę dla umowy klienta w windykacji nieformalnej
   * @param {ContractOutOfServiceInput} exclusion - szczegóły blokady
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @param {string} contractNo - numer umowy
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem zapisu 201
   */
  addContractOutOfService(exclusion: ContractOutOfServiceInput, extCompanyId: string, contractNo: string) {
    let params = new HttpParams();
    params = params.append('contractNo', contractNo);
    return this.http.postWithQueryObserve(`customers/block/${extCompanyId}/contract`, exclusion, params);
  }

  /**
   * Tworzy blokadę dla umowy klienta w windykacji formalnej
   * @param {ContractOutOfServiceInput} exclusion - szczegóły blokady
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @param {string} contractNo - numer umowy
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem zapisu 201
   */
  addFormalDebtCollectionContractOutOfService(exclusion: FormalDebtCollectionContractOutOfServiceInput, extCompanyId: string, contractNo: string) {
    let params = new HttpParams();
    params = params.append('contractNo', contractNo);
    return this.http.postWithQueryObserve(`customers/block/${extCompanyId}/contract/formalDebtCollection`, exclusion, params);
  }

  /**
   * Aktualizuje istniejącą blokadę dla klienta o podanym identyfikatorze w windykacji nieformalnej
   * @param {CustomerOutOfService} exclusion - zaktualizowane szczegóły blokady
   * @param {number} exclusionId - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateCustomerOutOfService(exclusion: CustomerOutOfServiceInput, exclusionId: number) {
    return this.http.patch(`customers/block/${exclusionId}`, exclusion);
  }

  /**
   * Aktualizuje istniejącą blokadę dla klienta o podanym identyfikatorze w windykacji formalnej
   * @param {CustomerOutOfService} exclusion - zaktualizowane szczegóły blokady
   * @param {number} exclusionId - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateFormalDebtCollectionCustomerOutOfService(exclusion: CustomerOutOfServiceInput, exclusionId: number) {
    return this.http.patch(`customers/block/${exclusionId}/formalDebtCollection`, exclusion);
  }

  /**
   * Aktualizuje istniejącą blokadę dla umowy klienta w windykacji nieformalnej
   * @param {ContractOutOfService} exclusion - zaktualizowane szczegóły blokady
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @param {number} exclusionId - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateContractOutOfService(exclusion: ContractOutOfServiceInput, extCompanyId: string, exclusionId: number) {
    return this.http.patch(`customers/block/${extCompanyId}/contract/${exclusionId}`, exclusion);
  }

  /**
   * Aktualizuje istniejącą blokadę dla umowy klienta w windykacji formalnej
   * @param {ContractOutOfService} exclusion - zaktualizowane szczegóły blokady
   * @param {string} extCompanyId - identyfikator klienta z zewnętrznego systemu
   * @param {number} exclusionId - identyfikator blokady
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateFormalDebtCollectionContractOutOfService(exclusion: FormalDebtCollectionContractOutOfServiceInput, extCompanyId: string, exclusionId: number) {
    return this.http.patch(`customers/block/${extCompanyId}/contract/${exclusionId}/formalDebtCollection`, exclusion);
  }

  /**
   * Modyfikacja opiekuna klienta.
   * Usuwanie następuje, gdy identyifikator opiekuna jest pusty
   *
   * @param {number} customerId - identyfikator klienta
   * @param {number} accountManagerId - identyfikator opiekuna
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateAccountManagers(customerId: number, accountManagerId: number) {
    if (accountManagerId == null) {
      return this.http.patch(`customers/${customerId}/accountManager`, null);
    } else {
      return this.http.patch(`customers/${customerId}/accountManager?accountManagerId=${accountManagerId}`, null);
    }
  }

  /**
   * Pobiera segmenty klientów
   * @returns {Observable<KeyLabel[]>} - lista segmentów kientów
   */
  getSegments(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('customers/segments');
  }

  /**
   * Pobiera typy przyczyn zablokowania/odrzucenia
   * @returns {Observable<KeyLabel[]>} - lista typów przyczyn zablokowania/odrzucenia
   */
  getReasonTypes(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('customers/reasonTypes');
  }
}

import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {ContactPerson} from '../customer/shared/contact-person';
import {Person} from '../customer/shared/person';
import {KeyLabel} from '../shared/key-label';
import {Observable} from 'rxjs';

/**
 * Serwisu dostępu do usług REST dla osób uprawnionych
 */
@Injectable()
export class PersonService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Oznacznie lub odznaczenie osoby uprawnionej firmy jako osoba do kontaktu
   * @param {number} personId - identyfikator osoby
   * @param {number} companyId - identyfikator firmy
   * @param {boolean} isContactPerson - czy osoba do kontaktu
   * @returns {any} odpowiedź z poprawnym kodem http aktualizacji 202
   */
  updateContactPerson(personId: number, companyId: number, isContactPerson: boolean): any {
    return this.http.patch(`persons/${personId}/company/${companyId}`, new ContactPerson(isContactPerson));
  }

  /**
   * Dodanie osoby uprawnionej firmy
   * @param {number} companyId - identyfikator firmy
   * @param {Person} person - dane osoby
   * @returns {any} odpowiedź z poprawnym kodem http utworzenia 201 lub błędy walidacji
   */
  createPerson(companyId: number, person: Person): any {
    return this.http.postObserve(`persons/company/${companyId}`, person);
  }

  /**
   * Pobieranie statusów cywilnych
   * @returns {Observable<KeyLabel[]>} statusy cywilne
   */
  getMaritalStatuses(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('persons/maritalStatuses');
  }

  /**
   * Pobieranie typów dokumentu tożsamości
   * @returns {Observable<KeyLabel[]>} typy dokumentu tożsamości
   */
  getDocumentTypes(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('persons/documentTypes');
  }

  /**
   * Pobieranie osób uprawnionych dla firmy
   * @param {number} companyId - identyfikator firmy
   * @returns {Observable<KeyLabel[]>} osoby uprawione
   */
  getCompanyPersons(companyId: number): Observable<Person[]> {
    return this.http.get<Person[]>(`persons/company/${companyId}`);
  }
}

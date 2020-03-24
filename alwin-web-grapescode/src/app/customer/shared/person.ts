import {KeyLabel} from '../../shared/key-label';

/**
 * Osoba
 */
export class Person {
  id: number;
  personId: number;
  pesel: string;
  firstName: string;
  lastName: string;
  representative: boolean;
  address: string;
  maritalStatus: KeyLabel;
  idDocCountry: string;
  idDocNumber: string;
  idDocSignedBy: string;
  idDocSignDate: Date;
  politician: boolean;
  jobFunction: string;
  birthDate: Date;
  realBeneficiary: boolean;
  idDocType: KeyLabel;
  country: string;
  contactPerson: boolean;

  static unknownPerson(): Person {
    const person = new Person();
    person.firstName = 'Inny';
    person.lastName = '';
    return person;
  }
}

/**
 * Osoba używana podczas tworzenia czynności windykacyjnej, jeśli nie wiadomo z kim się rozmawia
 * @type {Person}
 */
export const UNKNOWN_PERSON = Person.unknownPerson();

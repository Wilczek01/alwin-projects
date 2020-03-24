import {ExtCompany} from '../company/ext-company';

/**
 * Przedmiot umowy
 */
export class ContractSubject {
  subjectId: number;
  contractId: number;
  index: string;
  name: string;
  description: string;
  startValue: number;
  vatRate: string;
  yearOfProduction: number;
  serialNumber: string;
  registrationNumber: string;
  subjectSupplier: ExtCompany;
}

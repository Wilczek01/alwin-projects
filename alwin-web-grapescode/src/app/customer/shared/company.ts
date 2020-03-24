import {Person} from './person';
import {Address} from './address';
import {ContactDetail} from './contact-detail';

/**
 * Firma klienta
 */
export class Company {
  id: number;
  extCompanyId: number;
  pkdCode: string;
  pkdName: string;
  shortName: string;
  companyName: string;
  nip: string;
  regon: string;
  krs: string;
  legalForm: string;
  recipientName: string;
  rating: string;
  ratingDate: Date;
  externalDBAgreement: boolean;
  externalDBAgreementDate: Date;
  persons: Array<Person>;
  addresses: Array<Address>;
  contactDetails: Array<ContactDetail>;
}

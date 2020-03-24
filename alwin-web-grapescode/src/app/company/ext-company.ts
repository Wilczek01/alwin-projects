import {LegalForm} from './ext-company-legal-form';

/**
 * Firma
 */
export class ExtCompany {
  id: number;
  nip: string;
  regon: string;
  krs: string;
  companyName: string;
  legalForm: LegalForm;
}

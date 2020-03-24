import {KeyLabel} from '../../shared/key-label';

export class Address {
  id: number;
  city: string;
  remark: string;
  country: string;
  streetName: string;
  postalCode: string;
  flatNumber: string;
  houseNumber: string;
  addressType: KeyLabel;
  streetPrefix: string;
  importedFromAida: boolean;
}

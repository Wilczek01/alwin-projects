import {KeyLabel} from '../../shared/key-label';
import {ContactType} from './contact-type';

export class ContactDetail {
  id: number;
  remark: string;
  contact: string;
  contactType: ContactType;
  state: KeyLabel;
  sendAutomaticSms: boolean;
}

import {Person} from '../../../customer/shared/person';
import {ContactDetail} from '../../../customer/shared/contact-detail';

/**
 * Dane to rozpoczęcia rozmowy telefonicznej
 */
export class PhoneCall {
  selectedPerson: Person;
  availablePersons: Person[];
  contactDetail: ContactDetail;
}

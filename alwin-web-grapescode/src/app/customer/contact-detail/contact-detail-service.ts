import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {ContactDetail} from '../shared/contact-detail';
import {Customer} from '../shared/customer';
import {KeyLabel} from '../../shared/key-label';
import {ContactType} from 'app/customer/shared/contact-type';

@Injectable()
export class ContactDetailService {

  constructor(private http: AlwinHttpService) {
  }

  getCustomerContactDetails(customer: Customer): Observable<ContactDetail[]> {
    if (customer.company != null) {
      return this.getCompanyContactDetails(customer.company.id);
    } else {
      return this.getPersonContactDetails(customer.person.id);
    }
  }

  getCompanyContactDetails(companyId: number): Observable<ContactDetail[]> {
    return this.http.get<ContactDetail[]>(`contactDetails/company/${companyId}`);
  }

  getPersonContactDetails(personId: number): Observable<ContactDetail[]> {
    return this.http.get<ContactDetail[]>(`contactDetails/person/${personId}`);
  }

  getContactDetailsTypes(): Observable<ContactType[]> {
    return this.http.get<ContactType[]>('contactDetails/types');
  }

  getContactDetailsStates(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('contactDetails/states');
  }

  getSuggestedPhoneNumbers(companyId: number): Observable<string[]> {
    return this.http.get<string[]>(`contactDetails/suggestedPhoneNumbers/${companyId}`);
  }

  addContactDetailForCustomer(contactDetail: ContactDetail, companyId: number, personId: number): any {
    if (companyId != null) {
      return this.addContactDetailForCompany(contactDetail, companyId);
    }
    return this.addContactDetailForPerson(contactDetail, personId);
  }

  addContactDetailForCompany(contactDetail: ContactDetail, companyId: number): any {
    return this.http.postObserve(`contactDetails/company/${companyId}`, contactDetail);
  }

  addContactDetailForPerson(contactDetail: ContactDetail, personId: number): any {
    return this.http.postObserve(`contactDetails/person/${personId}`, contactDetail);
  }

  updateContactDetail(contactDetail: ContactDetail): any {
    return this.http.patch(`contactDetails/${contactDetail.id}`, contactDetail);
  }

}

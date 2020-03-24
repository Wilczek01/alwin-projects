import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {Address} from '../shared/address';
import {Customer} from '../shared/customer';
import {KeyLabel} from '../../shared/key-label';

@Injectable()
export class AddressService {

  constructor(private http: AlwinHttpService) {
  }

  getCustomerAddresses(customer: Customer): Observable<Address[]> {
    if (customer.company != null) {
      return this.getCompanyAddresses(customer.company.id);
    } else {
      return this.getPersonAddresses(customer.person.id);
    }
  }

  getCompanyAddresses(companyId: number): Observable<Address[]> {
    return this.http.get<Address[]>(`addresses/company/${companyId}`);
  }

  getPersonAddresses(personId: number): Observable<Address[]> {
    return this.http.get<Address[]>(`addresses/person/${personId}`);
  }

  getAddressesTypes(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('addresses/types');
  }

  addAddressesForCustomer(address: Address, customer: Customer): any {
    if (customer.company != null) {
      return this.addAddressForCompany(address, customer.company.id);
    } else {
      return this.addAddressForPerson(address, customer.person.id);
    }
  }

  addAddressForCompany(address: Address, companyId: number): any {
    return this.http.postObserve(`addresses/company/${companyId}`, address);
  }

  addAddressForPerson(address: Address, personId: number): any {
    return this.http.postObserve(`addresses/person/${personId}`, address);
  }

  updateAddress(address: Address): any {
    return this.http.patch(`addresses/${address.id}`, address);
  }
}

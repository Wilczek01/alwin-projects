import {Company} from './company';
import {Person} from './person';
import {OperatorUser} from '../../operator/operator-user';

/**
 * Klient
 */
export class Customer {
  id: number;
  company: Company;
  person: Person;
  accountManager: OperatorUser;
}

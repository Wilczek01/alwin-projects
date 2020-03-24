import {Operator} from '../operator/operator';

export class FullUser {
  id: number;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  operators: Operator[];
}

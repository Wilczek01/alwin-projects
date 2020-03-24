import {User} from '../user/user';
import {OperatorType} from './operator-type';
import {Permission} from './permission';
import {ParentOperator} from './parent-operator';

export class Operator {
  id: number;
  active: boolean;
  permission: Permission;
  parentOperator: ParentOperator;
  login: string;
  password: string;
  user: User;
  type: OperatorType;
}

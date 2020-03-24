import {OperatorType} from '../../operator/operator-type';
import {Permission} from '../../operator/permission';

/**
 * Odpowiedź dla żądania zalogowania użytkownika
 */
export class LoginResponse {
  username: string;
  role: OperatorType;
  forceUpdatePassword: boolean;
  permission: Permission;
}

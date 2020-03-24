import {Operator} from '../operator/operator';

/**
 * Powiadomienie do zlecenia
 */
export class Notification {
  id: number;
  issueId: number;
  creationDate: Date;
  readDate: Date;
  message: string;
  sender: Operator;
  readConfirmationRequired: boolean;
}

import {KeyLabel} from '../../shared/key-label';
import {OperatorUser} from '../../operator/operator-user';

export class IssueTermination {
  id: number;
  issueId: number;
  requestCause: string;
  requestOperator: OperatorUser;
  excludedFromStats: boolean;
  exclusionFromStatsCause: string;
  state: KeyLabel;
  managerOperator: OperatorUser;
  comment: string;
  created: Date;
  updated: Date;
}

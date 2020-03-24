import {OperatorType} from '../../operator/operator-type';
import {IssueType} from './issue-type';
import {KeyLabel} from '../../shared/key-label';

/**
 * Konfiguracja zlecenia windykacyjnego
 */
export class IssueTypeConfiguration {
  id: number;
  issueType: IssueType;
  duration: number;
  segment: KeyLabel;
  createAutomatically: boolean;
  dpdStart: number;
  minBalanceStart: number;
  operatorTypes: OperatorType[];
}



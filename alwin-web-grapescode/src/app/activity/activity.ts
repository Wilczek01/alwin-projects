import {ActivityDetail} from './activity-detail';
import {ActivityType} from './activity-type';
import {ActivityState} from './activity-state';
import {ActivityDeclaration} from './activity-declaration';
import {Operator} from '../operator/operator';
import {IssueTypeWithDefaultActivities} from './issue-type-with-default-activities';

/**
 * Czynność windykacyjna
 */
export class Activity {
  id: number;
  issueId: number;
  operator: Operator;
  activityType: ActivityType;
  activityDate: Date;
  plannedDate: Date;
  creationDate: Date;
  state: ActivityState;
  charge: number;
  invoiceId: string;
  activityDetails: ActivityDetail[];
  declarations: ActivityDeclaration[];
}

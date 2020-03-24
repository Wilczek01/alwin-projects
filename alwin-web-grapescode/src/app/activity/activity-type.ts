import {ActivityTypeDetailProperty} from './activity-type-detail-property';

/**
 * Typ czynności windykacyjnej
 */
export class ActivityType {
  id: number;
  key: string;
  name: string;
  canBePlanned: boolean;
  mayBeAutomated: boolean;
  specific: boolean;
  mayHaveDeclaration: boolean;
  chargeMin: number;
  chargeMax: number;
  customerContact: number;
  activityTypeDetailProperties: ActivityTypeDetailProperty[];
}

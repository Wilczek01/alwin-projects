/**
 * Typy czynności windykacyjnej zgrupowane po statusie (planowana/wykonana)
 */
import {ActivityType} from './activity-type';

export class ActivityTypeByState {
  planned: ActivityType[];
  executed: ActivityType[];
}

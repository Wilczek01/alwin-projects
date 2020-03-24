import {ActivityDetailProperty} from './activity-detail-property';

/**
 * Właściwość szczegółu czynności windykacyjnej
 */
export class ActivityTypeDetailProperty {
  id: number;
  activityDetailProperty: ActivityDetailProperty;
  state: string;
  required: boolean;


  constructor(id?: number, activityDetailProperty?: ActivityDetailProperty, state?: string, required?: boolean) {
    this.id = id;
    this.activityDetailProperty = activityDetailProperty;
    this.state = state;
    this.required = required;
  }
}

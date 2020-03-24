import {ActivityDetailProperty} from './activity-detail-property';

/**
 * Szczegół dla czynności windykacyjnej
 */
export class ActivityDetail {
  id: number;
  detailProperty: ActivityDetailProperty;
  value: any;
  required: boolean;
}

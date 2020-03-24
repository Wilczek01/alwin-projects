import {ActivityDetailProperty} from '../../../activity/activity-detail-property';

export class UpdateActivityType {
  id: number;
  idPlanned: number;
  selected: boolean;
  selectedPlanned: boolean;
  required = false;
  requiredPlanned = false;
  activityDetailProperty: ActivityDetailProperty;
}

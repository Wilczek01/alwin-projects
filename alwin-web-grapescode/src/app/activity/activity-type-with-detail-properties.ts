import {ActivityType} from './activity-type';
import {ActivityDetailProperty} from './activity-detail-property';

/**
 * Typ czynności windykacyjnej z oddzielnym polem na cechy zawierające dane słownikowe do aktualizacji
 */
export class ActivityTypeWithDetailProperties extends ActivityType {
  detailProperties: ActivityDetailProperty[];
}

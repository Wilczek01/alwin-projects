import {Activity} from './activity';
import {ActivityUtils} from './activity.utils';
import {Page} from '../shared/pagination/page';

/**
 * Klasa mapująca daty w obiektach czynności windykacyjnych ustawiając im odpowiedni typ
 */
export class ActivityMapper {
  static mapActivities(activities: Activity[]) {
    if (activities != null) {
      activities.forEach(activity => {
        this.mapActivity(activity);
      });
    }
    return activities;
  }

  static mapActivity(activity: Activity) {
    if (!activity) {
      return;
    }
    activity.plannedDate = activity.plannedDate != null ? new Date(activity.plannedDate) : null;
    activity.activityDate = activity.activityDate != null ? new Date(activity.activityDate) : null;
    activity.creationDate = new Date(activity.creationDate);
    this.mapActivityDeclarations(activity);
    this.mapActivityDetails(activity);
    return activity;
  }

  static mapActivitiesPage(page: Page<Activity>) {
    page.values = ActivityMapper.mapActivities(page.values);
    return page;
  }

  private static mapActivityDeclarations(activity) {
    if (activity.declarations != null) {
      activity.declarations.forEach(value => {
        value.declarationDate = new Date(value.declarationDate);
        value.declaredPaymentDate = new Date(value.declaredPaymentDate);
      });
    }
  }

  private static mapActivityDetails(activity) {
    if (activity.activityDetails != null) {
      activity.activityDetails.forEach(detail => {
        if (ActivityUtils.isTypeOfDate(detail)) {
          detail.value = new Date(detail.value);
        }
        if (ActivityUtils.isTypeOfBoolean(detail)) {
          detail.value = detail.value === 'true';
        }
      });
    }
  }
}

import {Pipe, PipeTransform} from '@angular/core';
import {ActivityDetail} from '../../../../activity/activity-detail';
import {ActivityDetailPropertyKey} from '../../../../activity/activity-detail-property-key';

@Pipe({
  name: 'activityComment'
})
export class ActivityCommentPipe implements PipeTransform {

  /**
   * Zwraca komentarz ze szczegółów czynności
   *
   * @param {ActivityDetail[]} activityDetails - szczegóły czynności windykacyjnej
   * @param {any[]} args - argumenty z danymi
   * @returns {string} - komentarz lub 'b/d'
   */
  transform(activityDetails: ActivityDetail[], args: any[]): string {
    if (activityDetails == null) {
      return 'b/d';
    }

    const commentDetail = activityDetails.find(activityDetail => activityDetail.detailProperty.key === ActivityDetailPropertyKey.REMARK);
    if (commentDetail == null) {
      return 'b/d';
    }

    return commentDetail.value;
  }
}

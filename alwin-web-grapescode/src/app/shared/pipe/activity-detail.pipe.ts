import {Pipe, PipeTransform} from '@angular/core';
import {ActivityDetail} from '../../activity/activity-detail';
import {ActivityDetailPropertyType} from '../../activity/activity-detail-property-type';
import {DatePipe} from '@angular/common';

@Pipe({
  name: 'activityDetail'
})
export class ActivityDetailPipe implements PipeTransform {

  constructor(private datePipe: DatePipe) {
  }

  transform(detail: ActivityDetail, args: string[]): string {
    if (detail == null || detail.value == null) {
      return 'b/d';
    }
    if (detail.detailProperty.type === ActivityDetailPropertyType.BOOLEAN) {
      return detail.value ? 'Tak' : 'Nie';
    }

    if (detail.detailProperty.type === ActivityDetailPropertyType.DATE) {
      return this.datePipe.transform(detail.value, 'dd.MM.yyyy');
    }

    return detail.value;
  }

}

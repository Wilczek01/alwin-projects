import {Pipe, PipeTransform} from '@angular/core';
import {ActivityTypeDetailProperty} from '../../activity/activity-type-detail-property';

/**
 * Filtr dla kolekcji typów czynności windykacyjnych rozdzielający listę po stanie. Kolekcja wynikowa sortowana jest alfabetycznie po nazwie cechy
 */
@Pipe({
  name: 'filterActivityTypeDetailPropertiesByState',
  pure: false
})
export class FilterActivityTypeDetailPropertiesByStatePipe implements PipeTransform {


  transform(items: ActivityTypeDetailProperty[], state: string): any {
    if (!items || !state) {
      return items;
    }

    return items.filter(prop => prop.state === state).sort((prop1, prop2) => {
      if (prop1.activityDetailProperty.name > prop2.activityDetailProperty.name) {
        return 1;
      }

      if (prop1.activityDetailProperty.name < prop2.activityDetailProperty.name) {
        return -1;
      }

      return 0;
    });
  }

}

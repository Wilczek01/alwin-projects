import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'demandManualCreatedMessages'
})
export class DemandManualCreationMessagesPipe implements PipeTransform {

  transform(value: string[], args: string[]): string {
    if (value === null) {
      return '';
    }

    return value.join('<br/>');
  }

}

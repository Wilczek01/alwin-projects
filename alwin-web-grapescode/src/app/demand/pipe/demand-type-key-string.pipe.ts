import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'demandTypeKeyString'
})
export class DemandTypeKeyStringPipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value === 'FIRST') {
      return 'Monit';
    }

    if (value === 'SECOND') {
      return 'Wezwanie';
    }
    return 'b/d';
  }

}

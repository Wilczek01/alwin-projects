import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'nullable'
})
export class NullablePipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    const defaultValue = args && args.length > 0 ? args[0] : 'b/d';
    return value != null ? value : defaultValue;
  }

}

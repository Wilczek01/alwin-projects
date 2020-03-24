import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'concatenateText'
})
export class ConcatenatePipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (args == null) {
      return value != null ? value : 'b/d';
    }

    let result = value != null ? value : '';

    for (const text of args) {
      if (text != null) {
        result = `${result} ${text}`;
      }
    }

    return result.length > 0 ? result.trim() : 'b/d';
  }

}

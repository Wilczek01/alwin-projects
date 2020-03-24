import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'booleanToString'
})
export class BooleanToStringPipe implements PipeTransform {

  static readonly trueString = 'tak';
  static readonly falseString = 'nie';
  static readonly noDataString = 'nie';

  transform(value: boolean, args: string[]): string {
    if (value == null) {
      return BooleanToStringPipe.noDataString;
    }
    return value ? BooleanToStringPipe.trueString : BooleanToStringPipe.falseString;
  }
}

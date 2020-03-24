import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'choice'
})
export class ChoicePipe implements PipeTransform {

  static convertToBoolean(input: string): boolean | undefined {
    try {
      return JSON.parse(input);
    } catch (e) {
      return null;
    }
  }

  transform(value: string, args: string[]): string {
    const result: Boolean = ChoicePipe.convertToBoolean(value);

    return result == null ? 'b/d' : (result ? 'Tak' : 'Nie');
  }

}

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'choiceWithIcons'
})
export class ChoiceWithIconsPipe implements PipeTransform {

  static convertToBoolean(input: string): boolean | undefined {
    try {
      return JSON.parse(input);
    } catch (e) {
      return null;
    }
  }

  transform(value: string, args: string[]): string {
    const result: Boolean = ChoiceWithIconsPipe.convertToBoolean(value);
    return result == null ? 'radio_button_unchecked' : (result ? 'check_circle' : 'radio_button_unchecked');
  }

}

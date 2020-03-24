import {Pipe, PipeTransform} from '@angular/core';
import {DecimalPipe} from '@angular/common';

@Pipe({
  name: 'phoneCallLength'
})
export class PhoneCallLengthPipe implements PipeTransform {

  decimalPipe = new DecimalPipe('en');
  NUMBER_FORMAT = '2.0-0';

  transform(value: number, args: string[]): string {
    const seconds = value % 60;
    const secondsTxt = this.decimalPipe.transform(seconds, this.NUMBER_FORMAT);

    const minutes = Math.floor(value / 60 % 60);
    const minutesTxt = this.decimalPipe.transform(minutes, this.NUMBER_FORMAT);

    const hours = Math.floor(value / 60 / 60);
    if (hours > 0) {
      const hoursTxt = this.decimalPipe.transform(hours, this.NUMBER_FORMAT);
      return `${hoursTxt}:${minutesTxt}:${secondsTxt}`;
    }
    return `${minutesTxt}:${secondsTxt}`;
  }
}

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'contractDpd'
})
export class ContractDpdPipe implements PipeTransform {

  transform(value: number, args: string[]): string {
    if (value == null) {
      return '---';
    }
    return `${value}`;
  }
}

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'balanceInvoiceNo'
})
export class BalanceInvoiceNoPipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value == null) {
      return 'b/d';
    }

    return value.length > 14 ? value.substring(0, 14) : value;
  }

}

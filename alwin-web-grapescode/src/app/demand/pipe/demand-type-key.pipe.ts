import {Pipe, PipeTransform} from '@angular/core';
import {DemandForPaymentType} from '../model/demand-for-payment-type';

@Pipe({
  name: 'demandTypeKey'
})
export class DemandTypeKeyPipe implements PipeTransform {

  transform(value: DemandForPaymentType, args: string[]): string {
    if (value == null) {
      return 'b/d';
    }
    if (value.key === 'FIRST') {
      return 'Monit';
    }

    if (value.key === 'SECOND') {
      return 'Wezwanie';
    }
    return 'b/d';
  }

}

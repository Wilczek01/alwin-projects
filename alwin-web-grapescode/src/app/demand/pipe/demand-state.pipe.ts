import {Pipe, PipeTransform} from '@angular/core';
import {DemandForPaymentStateConst} from '../model/demand-for-payment-state-const';

@Pipe({
  name: 'demandState'
})
export class DemandStatePipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value === DemandForPaymentStateConst.NEW) {
      return 'Nowe';
    }

    if (value === DemandForPaymentStateConst.WAITING) {
      return 'Procesowane';
    }

    if (value === DemandForPaymentStateConst.FAILED) {
      return 'Nowe (nieudane wysłanie)';
    }

    if (value === DemandForPaymentStateConst.ISSUED) {
      return 'Wezwane';
    }

    return 'b/d';
  }

}

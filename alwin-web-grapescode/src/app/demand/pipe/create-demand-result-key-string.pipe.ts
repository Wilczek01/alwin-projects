import {Pipe, PipeTransform} from '@angular/core';
import {CreateDemandForPaymentResultConst} from '../model/create-demand-for-payment-result-const';

@Pipe({
  name: 'createDemandResultKeyString'
})
export class CreateDemandResultKeyStringPipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value === null) {
      return 'b/d';
    }

    switch (value) {
      case CreateDemandForPaymentResultConst.SUCCESSFUL:
        return 'Utworzono nowe wezwanie';
      case CreateDemandForPaymentResultConst.FAILED:
        return 'Wystąpił błąd systemu w trakcie tworzenia wezwania';
      case CreateDemandForPaymentResultConst.NO_DUE_INVOICES_FOR_CONTRACT:
        return 'Brak dłużnych dokumentów dla umowy';
      case CreateDemandForPaymentResultConst.CONTRACT_ALREADY_IN_TERMINATION_STAGE:
        return 'Umowa jest już na etapie wypowiedzeń';
      case CreateDemandForPaymentResultConst.CONTRACT_NOT_FOUND:
        return 'Nie znaleziono umowy';
    }
    return 'b/d';
  }

}

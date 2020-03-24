import {Pipe, PipeTransform} from '@angular/core';
import {TerminationContractStateConst} from '../model/termination-contract-state-const';

@Pipe({
  name: 'terminationState'
})
export class TerminationStatePipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value === TerminationContractStateConst.NEW) {
      return 'Nowe';
    }

    if (value === TerminationContractStateConst.WAITING) {
      return 'Procesowane';
    }

    if (value === TerminationContractStateConst.FAILED) {
      return 'Nowe (nieudane wysłanie)';
    }

    if (value === TerminationContractStateConst.POSTPONED) {
      return 'Odroczone';
    }

    if (value === TerminationContractStateConst.REJECTED) {
      return 'Odrzucone';
    }

    if (value === TerminationContractStateConst.ISSUED) {
      return 'Wezwane';
    }

    return 'b/d';
  }

}

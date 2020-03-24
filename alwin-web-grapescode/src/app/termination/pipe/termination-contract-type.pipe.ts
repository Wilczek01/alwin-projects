import {Pipe, PipeTransform} from '@angular/core';
import {TerminationContractTypeConst} from '../model/termination-contract-type-const';

@Pipe({
  name: 'terminationContractType'
})
export class TerminationContractTypePipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value == null) {
      return 'b/d';
    }
    if (value === TerminationContractTypeConst.CONDITIONAL) {
      return 'Warunkowe';
    }

    if (value === TerminationContractTypeConst.IMMEDIATE) {
      return 'Natychmiastowe';
    }
    return 'b/d';
  }

}

import {Pipe, PipeTransform} from '@angular/core';

/**
 * Pipe to translacji typu operacji na rozrachunku na odpowiednią etykietę
 */
@Pipe({
  name: 'settlementOperation'
})
export class SettlementOperationTypePipe implements PipeTransform {

  transform(value: string, args: string[]): string {
    if (value === 'CHARGE') {
      return 'Obciążenie';
    } else if (value === 'CREDIT') {
      return 'Uznanie';
    }
    return 'Nieznany';
  }
}

import {Component, Input} from '@angular/core';
import {Instalment} from '../../../contract/instalment';

/**
 * Komponent odpowiedzialny za wyświetlanie rat harmonogramu spłat
 */
@Component({
  selector: 'alwin-customer-contract-payment-schedule-installments',
  templateUrl: './customer-contract-payment-schedule-installments.component.html'
})
export class CustomerContractPaymentScheduleInstallmentsComponent {

  @Input()
  instalments: Instalment[];

}

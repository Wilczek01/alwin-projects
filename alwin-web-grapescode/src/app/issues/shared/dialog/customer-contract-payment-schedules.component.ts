import {Component, Input} from '@angular/core';
import {PaymentScheduleWithInstalments} from '../../../contract/payment-schedule-with-instalments';

/**
 * Komponent odpowiedzialny za wyświetlanie harmonogramów umowy
 */
@Component({
  selector: 'alwin-customer-contract-payment-schedules',
  styleUrls: ['./customer-contract-payment-schedules.component.css'],
  templateUrl: './customer-contract-payment-schedules.component.html'
})
export class CustomerContractPaymentSchedulesComponent {

  @Input()
  paymentSchedules: PaymentScheduleWithInstalments[];

}

import {Component, Input} from '@angular/core';
import {Customer} from '../../../customer/shared/customer';

/**
 * Komponent dla widoku podstawowych danych klienta
 */
@Component({
  selector: 'alwin-customer-data-tabs',
  templateUrl: './customer-data-tabs.component.html',
  styleUrls: ['./customer-data-tabs.component.css']
})
export class CustomerDataTabsComponent {
  @Input()
  customer: Customer;
  @Input()
  issueId: number;
  @Input()
  readonly: boolean;

}

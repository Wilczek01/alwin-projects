import {Component, Input} from '@angular/core';
import {Customer} from './shared/customer';


@Component({
  selector: 'alwin-person-list',
  templateUrl: './person-list.component.html'
})
export class PersonListComponent {

  @Input()
  customer: Customer;

}

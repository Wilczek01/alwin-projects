import {Component, Input} from '@angular/core';
import {ContractSubject} from '../../../contract/contract-subject';

/**
 * Komponent odpowiedzialny za wyświetlanie przedmiotów umowy
 */
@Component({
  selector: 'alwin-customer-contract-subjects',
  templateUrl: './customer-contract-subjects.component.html'
})
export class CustomerContractSubjectsComponent {

  @Input()
  subjects: ContractSubject[];

}

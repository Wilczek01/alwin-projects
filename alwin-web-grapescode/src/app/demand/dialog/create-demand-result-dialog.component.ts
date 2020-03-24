import {Component} from '@angular/core';
import {ManualPrepareDemandForPaymentResult} from '../model/manual-prepare-demand-for-payment-result';

@Component({
  selector: 'alwin-create-demand-result-dialog',
  templateUrl: './create-demand-result-dialog.component.html',
  styleUrls: ['./create-demand-result-dialog.component.css']
})
export class CreateDemandResultDialogComponent {

  messages: ManualPrepareDemandForPaymentResult[];

}

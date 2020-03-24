import {Component} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {CreateDemandForPayment} from '../model/create-demand-for-payment';
import {DemandForPaymentTypeConst} from '../model/demand-for-payment-type-const';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {DemandService} from '../demand.service';
import {MessageService} from '../../shared/message.service';
import {CreateDemandResultDialogComponent} from './create-demand-result-dialog.component';
import {ManualPrepareDemandForPaymentResult} from '../model/manual-prepare-demand-for-payment-result';

@Component({
  selector: 'alwin-create-demand-dialog',
  templateUrl: './create-demand-dialog.component.html',
  styleUrls: ['./create-demand-dialog.component.css']
})
export class CreateDemandDialogComponent {

  request = new CreateDemandForPayment();
  contractNumbersString: string;
  loading: boolean;
  TYPE = DemandForPaymentTypeConst;

  constructor(private dialogRef: MatDialogRef<CreateDemandDialogComponent>, private dialog: MatDialog, private demandService: DemandService, private messageService: MessageService) {
  }

  createNewDemand() {
    this.loading = true;
    this.request.contractNumbers = this.contractNumbersString.split(',');
    this.request.contractNumbers = this.request.contractNumbers.map(val => val.trim());

    this.demandService.createDemandManually(this.request).subscribe(
      response => {
        if (response.status === 201) {
          this.loading = false;
          const dialogRef = this.dialog.open(CreateDemandResultDialogComponent);
          dialogRef.componentInstance.messages = response.body as ManualPrepareDemandForPaymentResult[];
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się uruchomić procesowania wezwań');
        }
      },
      err => {
        this.loading = false;
        HandlingErrorUtils.handleError(this.messageService, err);
      });
  }

}

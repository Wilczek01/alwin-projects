import {Component, Inject} from '@angular/core';
import {TerminationContract} from '../model/termination-contract';
import {ActivateContractTermination} from '../model/activate-contract-termination';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {TerminationService} from '../termination.service';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';

/**
 * Komponent z widokiem listy linków do wypowiedzeń
 */
@Component({
  selector: 'alwin-activate-contract-dialog',
  styleUrls: ['./activate-contract-dialog.component.css'],
  templateUrl: './activate-contract-dialog.component.html'
})
export class ActivateContractDialogComponent {

  requestInProgress: boolean;
  minActivationDate: Date;
  activation: ActivateContractTermination;

  constructor(private terminationService: TerminationService,
              public dialogRef: MatDialogRef<ActivateContractDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public terminationContract: TerminationContract,
              private messageService: MessageService) {
    this.minActivationDate = new Date(this.terminationContract.terminationDate);
    this.activation = new ActivateContractTermination();
    this.activation.activationDate = new Date();
    this.activation.resumptionCost = this.terminationContract.resumptionCost;
  }

  activate() {
    this.requestInProgress = true;
    this.terminationService.activateContract(this.terminationContract.contractTerminationId, this.activation)
      .subscribe(() => {
        this.requestInProgress = false;
        this.dialogRef.close(true);
      }, err => {
        HandlingErrorUtils.handleError(this.messageService, err);
      });
  }

}

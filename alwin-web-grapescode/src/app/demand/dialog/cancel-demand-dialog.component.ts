import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {DemandService} from '../demand.service';
import {DemandCancelling} from '../model/demand-cancelling';
import {KeyLabel} from '../../shared/key-label';
import {CompanyService} from '../../company/company.service';

/**
 * Komponent z dialogiem do anulowania wezwań
 */
@Component({
  selector: 'alwin-cancel-demand-dialog',
  styleUrls: ['./cancel-demand-dialog.component.css'],
  templateUrl: './cancel-demand-dialog.component.html'
})
export class CancelDemandDialogComponent implements OnInit {

  demandId: number;
  requestInProgress: boolean;
  demandCancelling: DemandCancelling;
  reasonTypes: KeyLabel[];

  constructor(private demandService: DemandService,
              public dialogRef: MatDialogRef<CancelDemandDialogComponent>,
              private companyService: CompanyService,
              private messageService: MessageService) {

    this.demandCancelling = new DemandCancelling();
    this.demandCancelling.stateChangeDate = new Date();
  }

  ngOnInit(): void {
    this.loadReasonTypes();
  }

  private loadReasonTypes() {
    this.requestInProgress = true;
    this.companyService.getReasonTypes().subscribe(
      reasonTypes => {
        if (reasonTypes != null) {
          this.reasonTypes = reasonTypes;
        }
        this.requestInProgress = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.requestInProgress = false;
      }
    );
  }

  cancel() {
    this.requestInProgress = true;
    this.demandService.demandCancelation(this.demandId, this.demandCancelling)
      .subscribe(() => {
        this.requestInProgress = false;
        this.dialogRef.close(true);
      }, err => {
        this.requestInProgress = false;
        HandlingErrorUtils.handleError(this.messageService, err);
      });
  }

}

import {Component} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {MessageService} from '../../shared/message.service';
import {IssueTerminationService} from '../shared/issue-termination.service';
import {IssueTerminationRequest} from '../shared/terminate-issue-request';
import {IssueTermination} from '../shared/issue-termination';
import {HandlingErrorUtils} from '../shared/utils/handling-error-utils';

/**
 * Przedterminowe kończenie zlecenia windykacyjnego
 * Obsługa żądań o przedtermionowe zakończenie zlecenia
 */
@Component({
  selector: 'alwin-assign-operator-dialog',
  styleUrls: ['./issue-termination-dialog.component.css'],
  templateUrl: './issue-termination-dialog.component.html',
})
export class IssueTerminationDialogComponent {

  loading = false;
  issueId: number;
  issueTerminationRequest: IssueTerminationRequest;
  issueTermination: IssueTermination;
  errorMessage: string;
  responseMessage: string;
  dialogTitle: string;

  constructor(private dialog: MatDialog,
              public dialogRef: MatDialogRef<IssueTerminationDialogComponent>,
              private issueTerminationService: IssueTerminationService,
              private messageService: MessageService) {
  }

  findTerminationRequest() {
    this.loading = true;
    this.issueTerminationService.findTerminationRequest(this.issueId).subscribe(
      response => {
        this.issueTermination = response;
        this.dialogTitle = 'Żądanie zakończenia zlecenia';
        this.loading = false;
      },
      err => {
        if (err.status === 404) {
          this.issueTerminationRequest = new IssueTerminationRequest();
          this.dialogTitle = 'Przedterminowe zakończenie zlecenia';
        } else {
          HandlingErrorUtils.handleError(this.messageService, err);
        }
        this.loading = false;
      }
    );
  }

  terminateIssue() {
    this.loading = true;
    this.issueTerminationService.terminateIssue(this.issueId, this.issueTerminationRequest).subscribe(
      response => {
        this.responseMessage = response;
        this.messageService.showMessage(response);
        this.loading = false;
      },
      err => {
        this.handleErrorMessage(err);
      }
    );
  }

  acceptIssueTermination() {
    this.loading = true;
    this.issueTerminationService.acceptIssueTermination(this.issueTermination.id, this.issueTermination).subscribe(
      response => {
        this.handleResponse(response, 'Żądanie zostało zaakceptowane', 'Nie udało się zaakceptować żądania');
      },
      err => {
        this.handleErrorMessage(err);
      }
    );
  }

  rejectIssueTermination() {
    this.loading = true;
    this.issueTerminationService.rejectIssueTermination(this.issueTermination.id, this.issueTermination).subscribe(
      response => {
        this.handleResponse(response, 'Żądanie zostało odrzucone', 'Nie udało się odrzucić żądania');
      },
      err => {
        this.handleErrorMessage(err);
      }
    );
  }

  private handleResponse(response, okMessage, errMessage) {
    if (response.status === 200) {
      this.messageService.showMessage(okMessage);
      this.loading = false;
      this.closeDialog();
    } else {
      this.messageService.showMessage(errMessage);
      this.loading = false;
    }
  }

  private handleErrorMessage(err) {
    if (err.status === 400) {
      this.errorMessage = err.error.message;
      this.messageService.showMessage(this.errorMessage);
    } else {
      HandlingErrorUtils.handleError(this.messageService, err);
    }
    this.loading = false;
  }

  closeDialog() {
    this.dialogRef.close(true);
  }
}

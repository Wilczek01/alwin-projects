import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import {Injectable} from '@angular/core';
import {ReportErrorDialogComponent} from '../error/report-error-dialog.component';
import {ErrorResponse} from '../error/error-response';

@Injectable()
export class MessageService {

  constructor(private snackBar: MatSnackBar, private reportErrorDialog: MatDialog) {
  }

  showMessage(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

  showMessageErrorResponse(errorResponse: ErrorResponse) {
    this.showMessageErrorResponseCustomMessage(null, errorResponse);
  }

  showMessageErrorResponseCustomMessage(message: string, errorResponse: ErrorResponse) {
    const errorMessage = message != null ? message : (errorResponse.message) ? errorResponse.message : '';
    if (errorResponse && errorResponse.reportingRequired) {
      this.snackBar.open(errorMessage, 'Zgłoś', null).afterDismissed().subscribe(null, null, () => {
        const dialogRef = this.reportErrorDialog.open(ReportErrorDialogComponent);
        dialogRef.componentInstance.errorId = errorResponse.errorId;
      });
    } else {
      this.showMessage(errorMessage);
    }
  }
}

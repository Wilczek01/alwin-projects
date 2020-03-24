import {Component, Input} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'report-error-dialog',
  styleUrls: ['./report-error-dialog.component.css'],
  templateUrl: './report-error-dialog.component.html'
})
export class ReportErrorDialogComponent {

  @Input()
  errorId: string;

  errorDate = Date.now();

  constructor(public dialogRef: MatDialogRef<ReportErrorDialogComponent>) {
  }

  close() {
    this.dialogRef.close();
  }
}

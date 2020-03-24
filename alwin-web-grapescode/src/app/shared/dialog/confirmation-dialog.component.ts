import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'alwin-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
})
export class ConfirmationDialogComponent {
  message: string;
  warning: string;
  title: string;

  constructor(public dialogRef: MatDialogRef<ConfirmationDialogComponent>) {
  }
}

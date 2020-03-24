import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'alwin-reject-confirmation-dialog',
  templateUrl: './reject-confirmation-dialog.component.html',
  styleUrls: ['./reject-confirmation-dialog.component.css']
})
export class RejectConfirmationDialogComponent implements OnInit {

  rejectReason: string;

  constructor(public dialogRef: MatDialogRef<RejectConfirmationDialogComponent>) {
  }

  ngOnInit() {
  }

  accept() {
    this.dialogRef.close(true);
  }
}

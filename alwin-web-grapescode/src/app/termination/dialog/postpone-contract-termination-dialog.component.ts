import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

/**
 * Komponent odpowiedzialny za aktualizację czynności windykacyjnej w formie dialogu
 */
@Component({
  selector: 'alwin-postpone-contract-termination-dialog',
  styleUrls: ['./postpone-contract-termination-dialog.component.css'],
  templateUrl: './postpone-contract-termination-dialog.component.html'
})
export class PostponeContractTerminationDialogComponent implements OnInit {

  terminationDate: Date;
  remark: string;

  constructor(public dialogRef: MatDialogRef<PostponeContractTerminationDialogComponent>) {
  }

  ngOnInit() {
  }

  accept() {
    this.dialogRef.close(true);
  }

}

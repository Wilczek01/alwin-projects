import {Component, Input} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {IssueTerminationDialogComponent} from '../../termination/issue-termination-dialog.component';

@Component({
  selector: 'alwin-issue-termination-header',
  templateUrl: './issue-termination-header.component.html'
})
export class TerminationHeaderComponent {

  @Input()
  issueId: number;

  constructor(private dialog: MatDialog) {
  }

  /**
   * Otwiera modal do przedterminowego zakończenia zlecenia
   *
   * @param {number} issueId - identyfikator zlecenia
   */
  terminateIssue(issueId: number) {
    const dialogRef = this.dialog.open(IssueTerminationDialogComponent);
    dialogRef.componentInstance.issueId = issueId;
    dialogRef.componentInstance.findTerminationRequest();
  }

}

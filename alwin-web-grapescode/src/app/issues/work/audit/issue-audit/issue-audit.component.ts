import {Component, Input} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {IssueAuditDialogComponent} from '../../../audit/issue-audit-dialog.component';

@Component({
  selector: 'alwin-issue-audit',
  templateUrl: './issue-audit.component.html'
})
export class IssueAuditComponent {

  @Input()
  issueId: number;

  constructor(private dialog: MatDialog) {
  }

  /**
   * Otwiera modal do wyświetlania historii zlecenia
   *
   * @param {number} issueId - identyfikator zlecenia
   */
  getIssueAudit(issueId: number) {
    const dialogRef = this.dialog.open(IssueAuditDialogComponent);
    dialogRef.componentInstance.issueId = issueId;
  }
}

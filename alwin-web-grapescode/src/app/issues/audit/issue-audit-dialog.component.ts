import {Component, OnInit} from '@angular/core';
import {HandlingErrorUtils} from '../shared/utils/handling-error-utils';
import {AuditLogEntry} from '../shared/audit-log-entry';
import {IssueService} from '../shared/issue.service';
import {MessageService} from '../../shared/message.service';
import {IssueAuditDictionary} from './issue-audit-dictionary';

@Component({
  selector: 'alwin-issue-audit-dialog',
  templateUrl: './issue-audit-dialog.component.html'
})
export class IssueAuditDialogComponent implements OnInit {
  issueId: number;
  auditLogEntries: AuditLogEntry[] = [];
  loadingAuditLogEntries: boolean;

  constructor(private issueService: IssueService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loadAuditLogEntries();
  }

  /**
   * Pobiera historię zmiana dla konkretnego zlecenia
   */
  loadAuditLogEntries() {
    this.loadingAuditLogEntries = true;
    this.issueService.getIssueAuditLogEntries(this.issueId).subscribe(
      auditLogEntries => {
        if (auditLogEntries != null) {
          this.auditLogEntries = auditLogEntries;
        }
        this.loadingAuditLogEntries = false;
      },
      err => {
        if (err.status === 404) {
          this.messageService.showMessage('Brak poprawnych danych historycznych dla zlecenia');
        } else {
          HandlingErrorUtils.handleError(this.messageService, err);
        }
        this.loadingAuditLogEntries = false;
      });
  }

  getFieldName(objectName: string, fieldName: string): string {
    return IssueAuditDictionary.getFieldName(objectName, fieldName);
  }

  getChangeType(rootName: string): string {
    return IssueAuditDictionary.getChangeType(rootName);
  }

  getTranslatedLogEntry(logEntryValue: string) {
    return IssueAuditDictionary.getLogEntryValue(logEntryValue);
  }
}

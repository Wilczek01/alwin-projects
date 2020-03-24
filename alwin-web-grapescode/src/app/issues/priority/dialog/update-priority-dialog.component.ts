import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {HttpParams} from '@angular/common/http';
import {KeyLabel} from '../../../shared/key-label';
import {IssueService} from '../../shared/issue.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';

@Component({
  selector: 'alwin-update-priority-dialog',
  styleUrls: ['./update-priority-dialog.component.css'],
  templateUrl: './update-priority-dialog.component.html',
})
export class UpdatePriorityDialogComponent implements OnInit {

  loading = false;
  updateAll: boolean;
  issues: number[];
  currentPriority: KeyLabel;
  priorities: KeyLabel[];
  all: number;
  params: HttpParams;

  constructor(private dialog: MatDialog,
              private issueService: IssueService,
              public dialogRef: MatDialogRef<UpdatePriorityDialogComponent>,
              private messageService: MessageService) {
  }

  ngOnInit() {
    if (this.issues != null && this.issues.length === 1 && this.currentPriority != null) {
      const matchingPriority = this.priorities.filter(priority => priority.key === this.currentPriority.key);
      if (matchingPriority.length !== 0) {
        this.currentPriority = matchingPriority[0];
      }
    }

    if (this.updateAll) {
      let params = new HttpParams();
      // przepisywanie z powodu duplikowania parametrów
      this.params.keys().forEach(key => {
        this.params.getAll(key).forEach(value => params = params.append(key, value));
      });
      this.params = params.append('updateAll', 'true');
    }
  }

  changePriority() {
    if (this.updateAll || this.issues.length > 1) {
      const issuesToUpdate = this.updateAll ? this.all : this.issues.length;
      const confirmationDialogRef = this.dialog.open(ConfirmationDialogComponent);
      confirmationDialogRef.componentInstance.message = `Zamierzasz ustawić priorytet ${this.currentPriority.label} dla ${issuesToUpdate} zleceń. Czy kontynuować?`;
      confirmationDialogRef.componentInstance.title = 'Aktualizacja priorytetu zlecenia';
      confirmationDialogRef.afterClosed().subscribe(confirmed => {
        if (confirmed) {
          const params = this.updateAll ? this.params : null;
          this.loading = true;
          this.issueService.updateIssuesPriority(this.currentPriority.key, this.issues, params).subscribe(
            response => this.handleResponse(response, issuesToUpdate),
            err => HandlingErrorUtils.handleError(this.messageService, err)
          );
        }
      });
    } else {
      this.loading = true;
      this.issueService.updateIssuesPriority(this.currentPriority.key, this.issues, null).subscribe(
        response => this.handleResponse(response, 1),
        err => HandlingErrorUtils.handleError(this.messageService, err)
      );
    }
  }

  private handleResponse(response, issuesToUpdate: number) {
    if (response.status === 202) {
      this.messageService.showMessage(`Zlecenia (${issuesToUpdate}) zostały zaktualizowane`);
      this.loading = false;
      this.dialogRef.close(true);
      return;
    }
    this.messageService.showMessage(`Nie udało się zaktualizować zleceń (${issuesToUpdate})`);
    this.loading = false;
  }

  currentPriorityUnknown(): boolean {
    return this.currentPriority != null && this.currentPriority.key == null;
  }

}

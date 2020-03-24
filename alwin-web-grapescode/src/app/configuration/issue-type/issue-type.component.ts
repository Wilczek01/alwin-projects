import {Component, OnInit} from '@angular/core';
import {IssueService} from '../../issues/shared/issue.service';
import {MessageService} from '../../shared/message.service';
import {IssueTypeConfiguration} from '../../issues/shared/issue-type-configuration';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {UpdateIssueTypeConfigurationDialogComponent} from './dialog/update-issue-type-configuration-dialog.component';
import { MatDialog } from '@angular/material/dialog';

/**
 * Komponent dla widoku obsługi konfiguracji zleceń windykacyjnych
 */
@Component({
  selector: 'alwin-issue-type',
  styleUrls: ['issue-type.component.css'],
  templateUrl: './issue-type.component.html'
})
export class IssueTypeComponent implements OnInit {

  loading = false;
  issueTypeConfiguration: IssueTypeConfiguration[] = [];

  constructor(private issueService: IssueService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadIssueTypeConfiguration();
  }

  /**
   * Pobieranie wszystkich konfiguracji zlecenia windykacyjnego
   */
  private loadIssueTypeConfiguration() {
    this.loading = true;
    this.issueService.getIssueTypeConfiguration().subscribe(
      issueTypeConfiguration => {
        if (issueTypeConfiguration == null) {
          this.messageService.showMessage('Nie znaleziono statusów zleceń');
          this.loading = false;
          return;
        }
        this.issueTypeConfiguration = [];
        this.issueTypeConfiguration = issueTypeConfiguration;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Otwiera okno umożliwiające edycję konfiguracji zlecenia
   * @param {IssueTypeConfiguration} configuration - wybrana konfiguracja
   */
  editConfiguration(configuration: IssueTypeConfiguration) {
    const dialogRef = this.dialog.open(UpdateIssueTypeConfigurationDialogComponent);
    dialogRef.componentInstance.configuration = Object.assign({}, configuration);
    dialogRef.afterClosed().subscribe(saved => {
        if (saved) {
          this.loadIssueTypeConfiguration();
        }
      }
    );
  }

}

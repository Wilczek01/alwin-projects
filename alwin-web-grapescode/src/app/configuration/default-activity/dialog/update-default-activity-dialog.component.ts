import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {MessageService} from '../../../shared/message.service';
import {OperatorService} from '../../../issues/shared/operator.service';
import {DefaultIssueActivity} from '../../../activity/default-issue-activity';
import {ActivityService} from '../../../activity/activity.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';

/**
 * Komponent dla wiodku aktualizacji domyślnej czynności zlecenia windykacyjnego
 */
@Component({
  selector: 'alwin-update-default-activity-dialog',
  styleUrls: ['./update-default-activity-dialog.component.css'],
  templateUrl: './update-default-activity-dialog.component.html',
})
export class UpdateDefaultActivityDialogComponent {
  loading: boolean;
  defaultIssueActivity: DefaultIssueActivity;
  defaultActivityNameLabel: string;

  selected = [];

  constructor(public dialogRef: MatDialogRef<UpdateDefaultActivityDialogComponent>,
              private operatorService: OperatorService,
              private messageService: MessageService,
              private activityService: ActivityService) {
  }

  /**
   * Aktualizuje istniejącą konfigurację zlecenia
   */
  updateDefaultActivity() {
    this.loading = true;
    this.activityService.updateDefaultActivity(this.defaultIssueActivity, this.defaultIssueActivity.id).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Konfiguracja została zaktualizowana');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zaktualizować konfiguracji');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }
}

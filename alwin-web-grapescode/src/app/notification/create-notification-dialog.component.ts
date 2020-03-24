import {Component} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {MessageService} from '../shared/message.service';
import {Notification} from './notification';
import {NotificationService} from './notification.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';

/**
 * Komponent dla widoku tworzenia nowego powiadomienia dla zlecenia
 */
@Component({
  selector: 'alwin-create-notification-dialog',
  styleUrls: ['./create-notification-dialog.component.css'],
  templateUrl: './create-notification-dialog.component.html',
})
export class CreateNotificationDialogComponent {
  issueId: number;
  loading: boolean;
  notification = new Notification();

  constructor(public dialogRef: MatDialogRef<CreateNotificationDialogComponent>,
              private notificationService: NotificationService,
              private messageService: MessageService) {
  }

  /**
   * Tworzy powiadomienie dla wybranego zlecenia
   */
  createNotification() {
    this.loading = true;
    this.notification.issueId = this.issueId;
    this.notificationService.createNotification(this.notification).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Powiadomienie zostało wysłane');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.messageService.showMessage('Nie udało się wysłać powiadomienia');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }
}

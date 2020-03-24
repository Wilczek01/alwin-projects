import {Component, Input, OnInit} from '@angular/core';
import {NotificationService} from '../../../notification/notification.service';
import {Notification} from '../../../notification/notification';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {CreateNotificationDialogComponent} from '../../../notification/create-notification-dialog.component';
import {UserAccessService} from '../../../common/user-access.service';
import {RoleType} from '../../shared/role-type';

@Component({
  selector: 'alwin-notification-header',
  templateUrl: './notification-header.component.html',
  styleUrls: ['./notification-header.component.css']
})
export class NotificationHeaderComponent implements OnInit {

  @Input()
  issueId: number;
  @Input()
  readonly: boolean;

  loadingNotifications: boolean;
  allUnreadNotificationsCount = 0;
  allNotificationsCount = 0;
  notifications: Notification[];
  role = RoleType;

  constructor(private dialog: MatDialog, private notificationService: NotificationService,
              private messageService: MessageService, private userAccessService: UserAccessService) {
  }

  ngOnInit(): void {
    this.loadNotifications(this.issueId);
  }

  /**
   * Pobiera powiadomienia dla zlecenia o podanym identyfikatorze wraz z ilością nieprzeczytanych oraz wszystkich powiadomień
   *
   * @param {number} issueId - identyfikator zlecenia
   */
  loadNotifications(issueId: number) {
    this.loadingNotifications = true;
    this.notificationService.getAllNotificationsWithCounters(issueId).subscribe(
      notificationsWithCounts => {
        if (notificationsWithCounts == null) {
          this.loadingNotifications = false;
          return;
        }
        this.allUnreadNotificationsCount = notificationsWithCounts.allUnreadNotificationsCount;
        this.allNotificationsCount = notificationsWithCounts.allNotificationsCount;
        this.notifications = notificationsWithCounts.notifications;
        this.loadingNotifications = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingNotifications = false;
      });
  }

  /**
   * Oznacza wybrane powiadomienie jako odczytane, a następnie odświeża listę powiadomień
   *
   * @param {Notification} notification - powiadomienie do odczytania
   */
  private readNotification(notification: Notification) {
    this.loadingNotifications = true;
    this.notificationService.updateNotificationReadDate(notification).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Powiadomienia zostały zaktualizowane');
          this.loadNotifications(this.issueId);
          this.loadingNotifications = false;
        } else {
          this.messageService.showMessage('Nie udało się zaktualizować powiadomień');
          this.loadingNotifications = false;
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingNotifications = false;
      }
    );
  }

  /**
   * Wyświetla komunikat pytający o potwierdzenie czy przeczytano powiadomienie. Jeżeli użytkownik odpowie twierdząco to oznacza wybrane powiadomienie jako
   * odczytane, a następnie odświeża listę powiadomień
   *
   * @param {Notification} notification - powiadomienie do odczytania
   */
  readNotificationAfterConfirmation(notification: Notification) {
    const dialog = this.dialog.open(ConfirmationDialogComponent);
    dialog.componentInstance.title = 'Przeczytanie powiadomienia';
    dialog.componentInstance.message = 'Czy powiadomienie zostało przeczytane?';
    dialog.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.readNotification(notification);
      }
    });
  }

  /**
   * Otwiera okno umożliwiające dodanie nowego powiadomienia do tego zlecenia.
   * Aktualizuje listę powiadomień w przypadku poprawnego dodania nowego powiadomienia.
   */
  createNewNotification() {
    const dialogRef = this.dialog.open(CreateNotificationDialogComponent);
    dialogRef.componentInstance.issueId = this.issueId;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.loadNotifications(this.issueId);
      }
    });
  }

  /**
   * Sprawdza czy podana rola użytkownika jest taka sama jak rola zalogowanego użytkownika
   * @param {string} role - sprawdzana rola
   * @returns {boolean} - true jeżeli podana rola jest taka sama jak rola zalogowanego użytkownika, false w przeciwnym wypadku
   */
  hasRole(role: string) {
    return this.userAccessService.hasRole(role);
  }

}

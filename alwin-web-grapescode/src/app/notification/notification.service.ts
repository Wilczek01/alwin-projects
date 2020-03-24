import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Observable} from 'rxjs';
import {NotificationsWithCounts} from './notifications-with-counts';
import {Notification} from './notification';

/**
 * Serwis dostępu do powiadomień
 */
@Injectable()
export class NotificationService {
  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera wszystkie powiadomienia dla podanego zlecenia wraz z ilością nieprzeczytanych oraz wszystkich powiadomień
   * @param {number} issueId - identyfikator zlecenia
   *
   * @returns {Observable<NotificationsWithCounts>} - lista powiadomień z licznikami
   */
  getAllNotificationsWithCounters(issueId: number): Observable<NotificationsWithCounts> {
    return this.http.get<NotificationsWithCounts>(`notifications/issue/${issueId}`);
  }

  /**
   * Oznacza powiadomienie jako przeczytane
   * @param {Notification} notification - powiadomienie do przeczytania
   *
   * @returns {any} - poprawny kod aktualizacji powiadomienia 202
   */
  updateNotificationReadDate(notification: Notification): any {
    return this.http.patch(`notifications/${notification.id}`, notification);
  }

  /**
   * Tworzy nowe powiadomienie
   * @param {Notification} notification - powiadomienie do utworzenia
   *
   * @returns {any} - poprawny kod utworzenia powiadomienia 201
   */
  createNotification(notification: Notification): any {
    return this.http.postObserve('notifications/', notification);
  }
}

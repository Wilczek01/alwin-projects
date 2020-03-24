import {Notification} from './notification';

/**
 * Powiadomienia do zlecenia wraz z ilością nieprzeczytanych oraz ilością wszystkich powiadomień
 */
export class NotificationsWithCounts {
  notifications: Notification[];
  allUnreadNotificationsCount: number;
  allNotificationsCount: number;
}

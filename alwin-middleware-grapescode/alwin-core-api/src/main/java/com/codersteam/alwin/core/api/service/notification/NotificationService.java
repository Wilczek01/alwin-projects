package com.codersteam.alwin.core.api.service.notification;

import com.codersteam.alwin.core.api.notification.NotificationDto;
import com.codersteam.alwin.core.api.notification.NotificationsWithCountsDto;

import javax.ejb.Local;

/**
 * Serwis do obsługi powiadomień
 *
 * @author Adam Stepnowski
 */
@Local
public interface NotificationService {

    /**
     * Pobieranie powiadomień po identyfikatorze zlecenia
     *
     * @param issueId -  identyfikator zlecenia
     * @return powiadomienia wraz z informacją o ich łącznej liczbie oraz ilości nieprzeczytanych
     */
    NotificationsWithCountsDto findAllIssueNotifications(final Long issueId);

    /**
     * Tworzenie nowego powiadomienia
     *
     * @param notificationDto - dane powiadomienia
     */
    void createNotification(NotificationDto notificationDto);

    /**
     * Pobieranie powiadomienia po identyfikatorze
     *
     * @param notificationid - identyfikator powiadomienia
     * @return powiadomienie
     */
    NotificationDto findNotification(final Long notificationid);

    /**
     * Oznacza powiadomienie jako przeczytane, poprzez aktualizację daty odczytania
     *
     * @param notificationId - identyfikator powiadomienia
     */
    void markNotificationAsRead(Long notificationId);
}

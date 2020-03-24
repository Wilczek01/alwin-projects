package com.codersteam.alwin.core.api.notification;

import java.util.List;

/**
 * @author Adam Stepnowski
 */
public class NotificationsWithCountsDto {
    private final List<NotificationDto> notifications;
    private final Long allUnreadNotificationsCount;
    private final Long allNotificationsCount;

    public NotificationsWithCountsDto(final List<NotificationDto> notifications, final Long allUnreadNotificationsCount) {
        this.notifications = notifications;
        this.allUnreadNotificationsCount = allUnreadNotificationsCount;
        this.allNotificationsCount = (long) notifications.size();
    }

    public List<NotificationDto> getNotifications() {
        return notifications;
    }

    public Long getAllUnreadNotificationsCount() {
        return allUnreadNotificationsCount;
    }

    public Long getAllNotificationsCount() {
        return allNotificationsCount;
    }
}

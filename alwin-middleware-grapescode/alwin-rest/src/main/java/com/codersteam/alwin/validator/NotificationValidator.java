package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.notification.NotificationDto;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.notification.NotificationService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Logika walidująca poprawność danych dla powiadomień
 *
 * @author Adam Stepnowski
 */
@Stateless
public class NotificationValidator {

    private static final String NOTIFICATION_NOT_EXISTS_MESSAGE = "Próba odczytania nieistniejącego powiadomienia";
    private static final String NOTIFICATION_ALREADY_MARKED_AS_READ_MESSAGE = "Powiadomienie zostało już odczytane wcześniej";
    private static final String NOTIFICATION_READ_CONFIRMATION_NOT_REQUIRED_MESSAGE = "Powiadomienie nie wymaga potwierdzenia przeczytania";
    private static final String ISSUE_NOT_EXISTS_MESSAGE = "Zlecenie, dla którego jest tworzone powiadomienie, nie istnieje";
    private static final String NOTIFICATION_MESSAGE_IS_EMPTY_MESSAGE = "Treść powiadomienia jest pusta";

    private NotificationService notificationService;
    private IssueService issueService;

    public NotificationValidator() {
    }

    @Inject
    public NotificationValidator(final NotificationService notificationService, final IssueService issueService) {
        this.notificationService = notificationService;
        this.issueService = issueService;
    }

    public void validateForUpdate(final Long notificationId) {
        final NotificationDto notification = validateIfNotificationExists(notificationId);
        checkNotificationReadConfirmationRequired(notification);
        checkNotificationNotReadBefore(notification);
    }

    public void validateForCreate(final NotificationDto notificationDto) {
        checkIssueExists(notificationDto);
        checkMessageFilledWitContent(notificationDto);
    }

    private void checkMessageFilledWitContent(final NotificationDto notificationDto) {
        if (isBlank(notificationDto.getMessage())) {
            throw new AlwinValidationException(NOTIFICATION_MESSAGE_IS_EMPTY_MESSAGE);
        }
    }

    private void checkIssueExists(final NotificationDto notificationDto) {
        final IssueDto issue = issueService.findIssue(notificationDto.getIssueId());
        if (issue == null) {
            throw new AlwinValidationException(ISSUE_NOT_EXISTS_MESSAGE);
        }
    }

    private void checkNotificationNotReadBefore(final NotificationDto notification) {
        final Date readDate = notification.getReadDate();
        if (readDate != null) {
            throw new AlwinValidationException(NOTIFICATION_ALREADY_MARKED_AS_READ_MESSAGE);
        }
    }

    private void checkNotificationReadConfirmationRequired(final NotificationDto notificationDto) {
        if (!notificationDto.isReadConfirmationRequired()) {
            throw new AlwinValidationException(NOTIFICATION_READ_CONFIRMATION_NOT_REQUIRED_MESSAGE);
        }
    }

    private NotificationDto validateIfNotificationExists(final Long notificationId) {
        try {
            return notificationService.findNotification(notificationId);
        } catch (final Exception e) {
            throw new AlwinValidationException(NOTIFICATION_NOT_EXISTS_MESSAGE);
        }
    }
}

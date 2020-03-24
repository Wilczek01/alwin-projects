package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.notification.NotificationDto;
import com.codersteam.alwin.core.api.notification.NotificationsWithCountsDto;
import com.codersteam.alwin.jpa.Notification;
import com.codersteam.alwin.jpa.operator.Operator;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.OperatorTestData.testOperator4;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto4;
import static com.codersteam.alwin.testdata.TestDateUtils.formatWithoutTime;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCompanyExcessPaymentTestData.companyExcessPaymentsDto;

/**
 * @author Adam Stepnowski
 */
public class NotificationTestData {

    public static final Long NOT_EXISTING_NOTIFICATION_ID = 1L;

    public static final Long ID_1 = 1L;
    public static final Long ISSUE_ID_1 = AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
    private static final Date CREATION_DATE_1 = parse("2017-11-29 00:00:00.000000");
    public static final String MESSAGE_1 = "Powiadomienie nieodczytane nr 1";
    private static final Operator SENDER_1 = testOperator4();
    public static final Date READ_DATE_3 = parse("2017-11-30 00:00:00.000000");
    public static final boolean READ_CONFIRMATION_REQUIRED_1 = true;

    private static final Long ID_2 = 2L;
    private static final Long ISSUE_ID_2 = AssignedIssuesWithHighPriorityTestData.ISSUE_ID_21;
    private static final Date CREATION_DATE_2 = parse("2017-11-30 14:57:14.934934");
    private static final String MESSAGE_2 = "Powiadomienie nieodczytane nr 2";
    private static final Operator SENDER_2 = testOperator4();
    private static final boolean READ_CONFIRMATION_REQUIRED_2 = false;

    public static final Long ID_3 = 3L;
    private static final Long ISSUE_ID_3 = AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
    private static final Date CREATION_DATE_3 = parse("2017-11-19 00:00:00.000000");
    private static final String MESSAGE_3 = "Powiadomienie odczytane nr 1";
    private static final Operator SENDER_3 = testOperator4();
    private static final boolean READ_CONFIRMATION_REQUIRED_3 = true;

    private static final Long ID_4 = 4L;
    private static final Long ISSUE_ID_4 = AssignedIssuesWithHighPriorityTestData.ISSUE_ID_21;
    private static final Date CREATION_DATE_4 = parse("2017-11-21 00:00:00.000000");
    private static final Date READ_DATE_4 = parse("2017-11-30 00:00:00.000000");
    private static final String MESSAGE_4 = "Powiadomienie odczytane nr 2";
    private static final Operator SENDER_4 = testOperator4();
    private static final boolean READ_CONFIRMATION_REQUIRED_4 = true;

    public static NotificationDto notificationFieldsSetupDto() {
        return notificationDto(null, ISSUE_ID_1, null, null, MESSAGE_1, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_1);
    }

    public static NotificationDto notificationDto() {
        return notificationDto(ID_1, ISSUE_ID_1, CREATION_DATE_1, null, MESSAGE_1, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_1);
    }

    public static NotificationDto exampleNotificationWithEmptyMessageDto() {
        return notificationDto(ID_1, ISSUE_ID_1, CREATION_DATE_1, null, null, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_1);
    }

    public static NotificationDto exampleNotificationWithReadDateDto() {
        return notificationDto(ID_1, ISSUE_ID_1, CREATION_DATE_1, READ_DATE_4, null, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_1);
    }

    public static NotificationDto notificationWithReadConfirmationNotRequired() {
        return notificationDto(ID_1, ISSUE_ID_1, CREATION_DATE_1, READ_DATE_4, null, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_2);
    }

    public static Notification exampleNotification() {
        return notification(ID_1, ISSUE_ID_1, CREATION_DATE_1, null, MESSAGE_1, SENDER_1, READ_CONFIRMATION_REQUIRED_1);
    }

    public static List<Notification> allNotifications() {
        return Arrays.asList(notification(ID_1, ISSUE_ID_1, CREATION_DATE_1, null, MESSAGE_1, SENDER_1, READ_CONFIRMATION_REQUIRED_1),
                notification(ID_2, ISSUE_ID_2, CREATION_DATE_2, null, MESSAGE_2, SENDER_2, READ_CONFIRMATION_REQUIRED_2),
                notification(ID_3, ISSUE_ID_3, CREATION_DATE_3, READ_DATE_3, MESSAGE_3, SENDER_3, READ_CONFIRMATION_REQUIRED_3),
                notification(ID_4, ISSUE_ID_4, CREATION_DATE_4, READ_DATE_4, MESSAGE_4, SENDER_4, READ_CONFIRMATION_REQUIRED_4));
    }

    public static List<Notification> exampleNotifications() {
        return Arrays.asList(notification(ID_1, ISSUE_ID_1, CREATION_DATE_1, null, MESSAGE_1, SENDER_1, READ_CONFIRMATION_REQUIRED_1),
                notification(ID_3, ISSUE_ID_3, CREATION_DATE_3, READ_DATE_3, MESSAGE_3, SENDER_3, READ_CONFIRMATION_REQUIRED_3));
    }

    public static List<NotificationDto> allNotificationDtos() {
        return Arrays.asList(notificationDto(ID_1, ISSUE_ID_1, CREATION_DATE_1, null, MESSAGE_1, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_1),
                notificationDto(ID_2, ISSUE_ID_2, CREATION_DATE_2, null, MESSAGE_2, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_2),
                notificationDto(ID_3, ISSUE_ID_3, CREATION_DATE_3, READ_DATE_3, MESSAGE_3, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_3),
                notificationDto(ID_4, ISSUE_ID_4, CREATION_DATE_4, READ_DATE_4, MESSAGE_4, testOperatorDto4(), READ_CONFIRMATION_REQUIRED_4));
    }

    public static List<NotificationDto> excessPaymentsNotificationsDto(final Date currentDate) {
        final List<NotificationDto> notifications = new ArrayList<>();
        companyExcessPaymentsDto().forEach(aidaExcessPayment -> {
            final String message = String.format("U klienta stwierdzono nadpłatę w wysokości %s powstałą w wyniku wpłaty z dnia %s (%s).",
                    aidaExcessPayment.getExcessPayment(), formatWithoutTime(aidaExcessPayment.getPaymentDate()), aidaExcessPayment.getDocDescription());
            notifications.add(notificationDto(null, ISSUE_ID_1, currentDate, null, message, null, false));
        });
        return notifications;
    }

    public static NotificationsWithCountsDto allNotificationsWithCountsDto() {
        return new NotificationsWithCountsDto(allNotificationDtos(), 2L);
    }

    public static NotificationsWithCountsDto allNotificationsWithExcessPaymentsAndCountsDto(final Date currentDate) {
        final List<NotificationDto> notifications = new ArrayList<>();
        notifications.addAll(excessPaymentsNotificationsDto(currentDate));
        notifications.addAll(allNotificationDtos());
        return new NotificationsWithCountsDto(notifications, 6L);
    }

    public static NotificationsWithCountsDto emptyNotificationsWithCountsDto() {
        return new NotificationsWithCountsDto(Lists.emptyList(), 0L);
    }

    private static Notification notification(final Long id, final Long issueId, final Date creationDate, final Date readDate, final String message,
                                             final Operator sender, final boolean allowedToMarkAsRead) {
        final Notification notification = new Notification();
        notification.setId(id);
        notification.setIssueId(issueId);
        notification.setCreationDate(creationDate);
        notification.setReadDate(readDate);
        notification.setMessage(message);
        notification.setSender(sender);
        notification.setReadConfirmationRequired(allowedToMarkAsRead);

        return notification;
    }

    private static NotificationDto notificationDto(final Long id, final Long issueId, final Date creationDate, final Date readDate, final String message,
                                                   final OperatorDto sender, final boolean allowedToMarkAsRead) {
        final NotificationDto notification = new NotificationDto();
        notification.setId(id);
        notification.setIssueId(issueId);
        notification.setCreationDate(creationDate);
        notification.setReadDate(readDate);
        notification.setMessage(message);
        notification.setSender(sender);
        notification.setReadConfirmationRequired(allowedToMarkAsRead);

        return notification;
    }

    public static Notification buildNewNotification() {
        return notification(null, ISSUE_ID_1, CREATION_DATE_1, null, MESSAGE_1, SENDER_1, READ_CONFIRMATION_REQUIRED_1);
    }

    public static Notification notificationToUpdate() {
        return notification(ID_3, ISSUE_ID_3, CREATION_DATE_3, READ_DATE_3, MESSAGE_3, SENDER_3, READ_CONFIRMATION_REQUIRED_3);
    }
}


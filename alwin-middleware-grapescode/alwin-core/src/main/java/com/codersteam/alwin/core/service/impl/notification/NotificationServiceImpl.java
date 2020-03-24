package com.codersteam.alwin.core.service.impl.notification;

import com.codersteam.aida.core.api.model.ExcessPaymentDto;
import com.codersteam.aida.core.api.service.ExcessPaymentService;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.notification.NotificationDto;
import com.codersteam.alwin.core.api.notification.NotificationsWithCountsDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.notification.NotificationService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.core.util.IssueUtils;
import com.codersteam.alwin.db.dao.NotificationDao;
import com.codersteam.alwin.jpa.Notification;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;
    private final AlwinMapper mapper;
    private final DateProvider dateProvider;
    private final IssueService issueService;
    private final ExcessPaymentService excessPaymentService;
    private static final String EXCESS_PAYMENT_MESSAGE = "U klienta stwierdzono nadpłatę w wysokości %s powstałą w wyniku wpłaty z dnia %s (%s).";

    @Inject
    public NotificationServiceImpl(final NotificationDao notificationDao, final AlwinMapper mapper, final DateProvider dateProvider, IssueService issueService,
                                   final AidaService aidaService) {
        this.notificationDao = notificationDao;
        this.mapper = mapper;
        this.dateProvider = dateProvider;
        this.issueService = issueService;
        this.excessPaymentService = aidaService.getExcessPaymentService();
    }

    @Override
    public NotificationsWithCountsDto findAllIssueNotifications(final Long issueId) {
        List<NotificationDto> notificationDtos = mapper.mapAsList(notificationDao.findAllIssueNotifications(issueId), NotificationDto.class);
        long unreadNotification = notificationDtos.stream().filter(n -> n.getReadDate() == null).count();
        List<NotificationDto> allNotifications = findNotificationsWithExcessPayments(issueId);
        int excessPaymentNotificationsCount = allNotifications.size();
        allNotifications.addAll(notificationDtos);
        return new NotificationsWithCountsDto(allNotifications, unreadNotification + excessPaymentNotificationsCount);
    }

    @Override
    public void createNotification(final NotificationDto notificationDto) {
        final Notification notificationEntity = mapper.map(notificationDto, Notification.class);
        notificationEntity.setCreationDate(dateProvider.getCurrentDate());
        notificationDao.create(notificationEntity);
    }

    @Override
    public NotificationDto findNotification(final Long notificationId) {
        final Optional<Notification> origin = notificationDao.get(notificationId);
        final Notification notification = origin.orElseThrow(() -> new EntityNotFoundException(notificationId));
        return mapper.map(notification, NotificationDto.class);
    }

    @Override
    @Transactional
    public void markNotificationAsRead(final Long notificationId) {
        final Optional<Notification> origin = notificationDao.get(notificationId);

        final Notification notificationEntity = origin.orElseThrow(() -> new EntityNotFoundException(notificationId));
        markNotificationAsRead(notificationEntity);
        notificationDao.update(notificationEntity);
    }

    protected List<NotificationDto> findNotificationsWithExcessPayments(final Long issueId) {
        final IssueDto issue = issueService.findIssue(issueId);
        final Long extCompanyId = IssueUtils.getExtCompanyId(issue);
        final List<ExcessPaymentDto> companyExcessPayments = excessPaymentService.findCompanyExcessPayments(extCompanyId);
        final List<NotificationDto> notifications = new ArrayList<>(companyExcessPayments.size());
        final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        companyExcessPayments.forEach(excessPayment -> {
            final NotificationDto notification = new NotificationDto();
            notification.setIssueId(issueId);
            final String paymentDate = format.format(excessPayment.getPaymentDate());
            notification.setMessage(String.format(EXCESS_PAYMENT_MESSAGE, excessPayment.getExcessPayment(), paymentDate, excessPayment.getDocDescription()));
            notification.setCreationDate(dateProvider.getCurrentDate());
            notification.setReadConfirmationRequired(false);
            notifications.add(notification);
        });
        return notifications;
    }

    private void markNotificationAsRead(final Notification notificationEntity) {
        notificationEntity.setReadDate(dateProvider.getCurrentDate());
    }
}

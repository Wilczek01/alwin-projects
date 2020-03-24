package com.codersteam.alwin.core.service.impl.notification

import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.NotificationDao
import com.codersteam.alwin.integration.mock.AidaServiceMock
import com.codersteam.alwin.jpa.Notification
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.integration.mock.ExcessPaymentServiceMock.EXCESS_PAYMENTS
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.NotificationTestData.*
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_4
import static com.codersteam.alwin.testdata.aida.AidaCompanyExcessPaymentTestData.companyExcessPaymentsDto
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class NotificationServiceImplTest extends Specification {

    private NotificationDao notificationDao = Mock(NotificationDao)
    private AlwinMapper mapper = new AlwinMapper()
    private DateProvider dateProvider = Mock(DateProvider)
    private IssueService issueService = Mock(IssueService)

    @Subject
    NotificationServiceImpl service

    def setup() {
        service = new NotificationServiceImpl(notificationDao, mapper, dateProvider, issueService, new AidaServiceMock())
    }

    def cleanup(){
        EXCESS_PAYMENTS.clear()
    }

    def "should return all notifications without excessPayments"() {
        given:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        and:
            notificationDao.findAllIssueNotifications(ISSUE_ID_1) >> allNotifications()

        when:
            def notifications = service.findAllIssueNotifications(ISSUE_ID_1)

        then:
            assertThat(notifications).isEqualToComparingFieldByFieldRecursively(allNotificationsWithCountsDto())
    }

    def "should return all notifications with excessPayments"() {
        given:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        and:
            EXCESS_PAYMENTS.put(EXT_COMPANY_ID_1, companyExcessPaymentsDto())

        and:
            def currentDate = new Date()
            dateProvider.getCurrentDate() >> currentDate

        and:
            notificationDao.findAllIssueNotifications(ISSUE_ID_1) >> allNotifications()

        when:
            def notifications = service.findAllIssueNotifications(ISSUE_ID_1)

        then:
            assertThat(notifications).isEqualToComparingFieldByFieldRecursively(allNotificationsWithExcessPaymentsAndCountsDto(currentDate))
    }

    def "should not return notifications"() {
        given:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        and:
            notificationDao.findAllIssueNotifications(ISSUE_ID_1) >> []

        when:
            def notifications = service.findAllIssueNotifications(ISSUE_ID_1)

        then:
            assertThat(notifications).isEqualToComparingFieldByFieldRecursively(emptyNotificationsWithCountsDto())
    }

    def "should create new notification"() {
        given:
            def toDate = new Date()

        and:
            dateProvider.getCurrentDate() >> toDate

        when:
            service.createNotification(notificationDto())

        then:
            1 * notificationDao.create(_ as Notification) >> { List arguments ->
                Notification notification = (Notification) arguments[0]
                assert notification.issueId
                assert notification.creationDate == toDate
                assert notification.readDate == null
                assert notification.message == 'Powiadomienie nieodczytane nr 1'
                assert notification.sender.id == OPERATOR_ID_4
            }
    }

    def "should return notification"() {
        given:
            notificationDao.get(ISSUE_ID_1) >> Optional.of(exampleNotification())

        when:
            def notification = service.findNotification(ISSUE_ID_1)

        then:
            assertThat(notification).isEqualToComparingFieldByFieldRecursively(notificationDto())
    }

    def "should not return notification"() {
        given:
            notificationDao.get(NOT_EXISTING_NOTIFICATION_ID) >> Optional.empty()

        when:
            service.findNotification(NOT_EXISTING_NOTIFICATION_ID)

        then:
            thrown(EntityNotFoundException)
    }

    def "should mark notification as read"() {
        given:
            notificationDao.get(ISSUE_ID_1) >> Optional.of(exampleNotification())

        when:
            service.markNotificationAsRead(ISSUE_ID_1)

        then:
            1 * notificationDao.update(_ as Notification)
    }

    def "should prepare company excess payments notifications"() {
        given:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        and:
            EXCESS_PAYMENTS.put(EXT_COMPANY_ID_1, companyExcessPaymentsDto())

        and:
            def currentDate = new Date()
            dateProvider.getCurrentDate() >> currentDate

        when:
            def excessPaymentsNotifications = service.findNotificationsWithExcessPayments(ISSUE_ID_1)

        then:
            assertThat(excessPaymentsNotifications).isEqualToComparingFieldByFieldRecursively(excessPaymentsNotificationsDto(currentDate))
    }
}
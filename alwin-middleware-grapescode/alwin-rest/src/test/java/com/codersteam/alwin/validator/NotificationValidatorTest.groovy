package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.model.issue.IssueDto
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.core.api.service.notification.NotificationService
import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto1
import static com.codersteam.alwin.testdata.NotificationTestData.*
import static com.codersteam.alwin.validator.NotificationValidator.*

/**
 * @author Adam Stepnowski
 */
class NotificationValidatorTest extends Specification {

    private issueService = Mock(IssueService)
    private notificationService = Mock(NotificationService)

    def "should validate not existing notification"() {
        given:
            notificationService.findNotification(ID_1) >> { throw new IllegalArgumentException() }

        and:
            def validator = new NotificationValidator(notificationService, issueService)

        when:
            validator.validateForUpdate(ID_1)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NOTIFICATION_NOT_EXISTS_MESSAGE
    }

    def "should validate notification read date"() {
        given:
            def notificationDto = exampleNotificationWithReadDateDto()
        and:
            notificationService.findNotification(ID_1) >> notificationDto

        and:
            issueService.findIssue(notificationDto.getIssueId()) >> issueDto1()

        and:
            def validator = new NotificationValidator(notificationService, issueService)

        when:
            validator.validateForUpdate(ID_1)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NOTIFICATION_ALREADY_MARKED_AS_READ_MESSAGE
    }

    def "should validate notification read confirmation required"() {
        given:
            def notificationDto = notificationWithReadConfirmationNotRequired()
        and:
            notificationService.findNotification(ID_1) >> notificationDto

        and:
            issueService.findIssue(notificationDto.getIssueId()) >> issueDto1()

        and:
            def validator = new NotificationValidator(notificationService, issueService)

        when:
            validator.validateForUpdate(ID_1)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NOTIFICATION_READ_CONFIRMATION_NOT_REQUIRED_MESSAGE
    }

    def "should have default public constructor"() {
        when:
            def notificationValidator = new NotificationValidator()
        then:
            notificationValidator
    }

    def "should validate related issue"() {
        given:
            issueService.findIssue(notificationDto().getIssueId()) >> null

        and:
            def validator = new NotificationValidator(notificationService, issueService)

        when:
            validator.validateForCreate(notificationDto())

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == ISSUE_NOT_EXISTS_MESSAGE
    }

    def "should validate notification message"() {
        given:
            def issue = new IssueDto()

        and:
            def notificationDto = exampleNotificationWithEmptyMessageDto()

        and:
            issueService.findIssue(notificationDto.getIssueId()) >> issue

        and:
            def validator = new NotificationValidator(notificationService, issueService)

        when:
            validator.validateForCreate(notificationDto)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NOTIFICATION_MESSAGE_IS_EMPTY_MESSAGE
    }
}

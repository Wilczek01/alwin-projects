package com.codersteam.alwin.core.service.impl.email

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage
import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage
import com.codersteam.alwin.core.api.service.email.EmailSenderService
import spock.lang.Specification
import spock.lang.Subject

import javax.jms.JMSException
import javax.jms.ObjectMessage
import javax.jms.TextMessage
import javax.mail.MessagingException

import static com.codersteam.alwin.testdata.MissingIssuesEmailMessageTestData.MISSING_ISSUES_EMAIL_MESSAGE
import static com.codersteam.alwin.testdata.UnresolvedIssuesEmailMessageTestData.UNRESOLVED_ISSUES_EMAIL_MESSAGE

/**
 * @author Tomasz Sliwinski
 */
class EmailSendMessageHandlerServiceTest extends Specification {

    @Subject
    private EmailSendMessageHandlerService service

    private EmailSenderService emailSender = Mock(EmailSenderService)

    def "setup"() {
        service = new EmailSendMessageHandlerService(emailSender)
    }

    def "should send missing issues email"() {
        given:
            def missingIssuesEmailMessage = MISSING_ISSUES_EMAIL_MESSAGE
        and:
            def message = Mock(ObjectMessage)
            message.getObject() >> missingIssuesEmailMessage
        when:
            service.onMessage(message)
        then:
            1 * emailSender.sendMissingIssuesReportMail(missingIssuesEmailMessage)
            0 * emailSender.sendUnresolvedIssuesReportMail(_ as UnresolvedIssuesEmailMessage)
    }

    def "should send unresolved issues email"() {
        given:
            def unresolvedIssuesEmailMessage = UNRESOLVED_ISSUES_EMAIL_MESSAGE
        and:
            def message = Mock(ObjectMessage)
            message.getObject() >> unresolvedIssuesEmailMessage
        when:
            service.onMessage(message)
        then:
            0 * emailSender.sendMissingIssuesReportMail(_ as MissingIssuesEmailMessage)
            1 * emailSender.sendUnresolvedIssuesReportMail(unresolvedIssuesEmailMessage)
    }

    def "should have default constructor for deployment purposes"() {
        when:
            def service = new EmailSendMessageHandlerService()
        then:
            service
    }

    def "should trow exception for improper message type"() {
        given:
            def message = Mock(TextMessage)
        when:
            service.onMessage(message)
        then:
            thrown(IllegalArgumentException)
    }

    def "should throw exception for improper object type"() {
        given:
            def missingIssuesEmailMessage = "some improper object"
        and:
            def message = Mock(ObjectMessage)
            message.getObject() >> missingIssuesEmailMessage
        when:
            service.onMessage(message)
        then:
            thrown(IllegalArgumentException)
    }

    def "should throw exception when email sending fails"() {
        given:
            def missingIssuesEmailMessage = MISSING_ISSUES_EMAIL_MESSAGE
        and:
            def message = Mock(ObjectMessage)
            message.getObject() >> missingIssuesEmailMessage
        and:
            emailSender.sendMissingIssuesReportMail(missingIssuesEmailMessage) >> { throw new MessagingException() }
        when:
            service.onMessage(message)
        then:
            thrown(RuntimeException)
    }

    def "should catch JMS exception"() {
        given:
            def message = Mock(ObjectMessage)
            message.getObject() >> { throw new JMSException("some serious reason") }
        when:
            service.onMessage(message)
        then:
            noExceptionThrown()
    }
}

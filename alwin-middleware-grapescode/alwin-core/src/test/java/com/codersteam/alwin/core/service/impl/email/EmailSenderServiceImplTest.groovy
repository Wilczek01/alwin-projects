package com.codersteam.alwin.core.service.impl.email

import com.codersteam.alwin.core.service.template.AlwinTemplateEngine
import org.apache.commons.io.IOUtils
import spock.lang.Specification
import spock.lang.Subject

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

import static com.codersteam.alwin.core.api.model.email.EmailType.MISSING_ISSUES
import static com.codersteam.alwin.core.api.model.email.EmailType.UNRESOLVED_ISSUES
import static com.codersteam.alwin.testdata.EmailTestData.*
import static com.codersteam.alwin.testdata.MissingIssuesEmailMessageTestData.MISSING_ISSUES_EMAIL_MESSAGE
import static com.codersteam.alwin.testdata.UnresolvedIssuesEmailMessageTestData.UNRESOLVED_ISSUES_EMAIL_MESSAGE

/**
 * @author Tomasz Sliwinski
 */
class EmailSenderServiceImplTest extends Specification {

    @Subject
    private EmailSenderServiceImpl service

    private AlwinTemplateEngine templateEngine = new AlwinTemplateEngine()
    private EmailProperties emailProperties = Mock(EmailProperties)

    def setup() {
        emailProperties.getEmailFrom() >> EMAIL_FROM
        //noinspection GroovyAssignabilityCheck
        service = Spy(EmailSenderServiceImpl, constructorArgs: [templateEngine, emailProperties])
    }

    def "should send missing issues email"() {
        given:
            def missingIssueEmailDto = MISSING_ISSUES_EMAIL_MESSAGE
        and:
            service.createMessage() >> Mock(MimeMessage)
        and:
            def expectedEmailBody = IOUtils.toString(this.getClass().getResource('/email/missingIssues.html').openStream(), 'utf-8')
        and:
            service.send(_ as MimeMessage) >> {}
        when:
            service.sendMissingIssuesReportMail(missingIssueEmailDto)
        then:
            1 * service.send(MISSING_ISSUES_MANAGER_EMAILS, MISSING_ISSUES.topic, _ as String) >> { List arguments ->
                def emailBody = arguments[2]
                assert emailBody == expectedEmailBody
            }
    }

    def "should send unresolved issues email"() {
        given:
            def unresolvedIssueEmailDto = UNRESOLVED_ISSUES_EMAIL_MESSAGE
        and:
            service.createMessage() >> Mock(MimeMessage)
        and:
            def expectedEmailBody = IOUtils.toString(this.getClass().getResource('/email/unresolvedIssues.html').openStream(), 'utf-8')
        and:
            service.send(_ as MimeMessage) >> {}
        when:
            service.sendUnresolvedIssuesReportMail(unresolvedIssueEmailDto)
        then:
            1 * service.send(UNRESOLVED_ISSUES_MANAGER_EMAILS, UNRESOLVED_ISSUES.topic, _ as String) >> { List arguments ->
                def emailBody = arguments[2]
                assert emailBody == expectedEmailBody
            }
    }

    def "should send unresolved email"() {
        given:
            def unresolvedIssueEmailDto = UNRESOLVED_ISSUES_EMAIL_MESSAGE
        and:
            service.createMessage() >> Mock(MimeMessage)
        and:
            service.send(_ as MimeMessage) >> {}
        when:
            service.sendUnresolvedIssuesReportMail(unresolvedIssueEmailDto)
        then:
            true
    }

    def "should throw exception when unable to send mail"() {
        given:
            service.send(_ as MimeMessage) >> { throw new MessagingException() }
        when:
            service.send(MISSING_ISSUES_MANAGER_EMAILS, MISSING_ISSUES.topic, MISSING_ISSUES_MAIL_BODY)
        then:
            thrown(MessagingException)
    }

    def "should prepare email recipients"() {
        when:
            def addresses = service.prepareRecipients(MISSING_ISSUES_MANAGER_EMAILS)
        then:
            addresses.size() == 2
    }
}

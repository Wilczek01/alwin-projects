package com.codersteam.alwin.core.service.impl.email

import spock.lang.Specification
import spock.lang.Subject

import javax.jms.JMSContext
import javax.jms.JMSProducer

import static com.codersteam.alwin.testdata.MissingIssuesEmailMessageTestData.MISSING_ISSUES_EMAIL_MESSAGE
import static com.codersteam.alwin.testdata.UnresolvedIssuesEmailMessageTestData.UNRESOLVED_ISSUES_EMAIL_MESSAGE

/**
 * @author Tomasz Sliwinski
 */
class EmailSendMessageEnqueueServiceImplTest extends Specification {

    @Subject
    private EmailSendMessageEnqueueServiceImpl service

    private JMSProducer jmsProducer = Mock(JMSProducer)

    def setup() {
        JMSContext jmsContext = Mock(JMSContext)
        jmsContext.createProducer() >> jmsProducer
        service = new EmailSendMessageEnqueueServiceImpl(jmsContext)
    }

    def "should enqueue missing issues message"() {
        given:
            def missingIssuesEmailMessage = MISSING_ISSUES_EMAIL_MESSAGE
        when:
            service.enqueueMissingIssuesEmail(missingIssuesEmailMessage)
        then:
            1 * jmsProducer.send(_, _)
    }

    def "should enqueue unresolved issues message"() {
        given:
            def unresolvedIssuesEmailMessage = UNRESOLVED_ISSUES_EMAIL_MESSAGE
        when:
            service.enqueueUnresolvedIssuesEmail(unresolvedIssuesEmailMessage)
        then:
            1 * jmsProducer.send(_, _)
    }
}

package com.codersteam.alwin.core.service.impl.message

import com.codersteam.alwin.core.api.exception.SmsSendingException
import org.apache.http.client.fluent.Content
import org.apache.http.client.fluent.Request
import org.apache.http.client.fluent.Response
import org.apache.http.entity.ContentType
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_ID
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_MESSAGE
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_PHONE_NUMBER

/**
 * @author Piotr Naroznik
 */
class SmsSenderServiceImplTest extends Specification {

    @Subject
    private SmsSenderServiceImpl service = Spy(SmsSenderServiceImpl)

    def "setup"() {
        def props = new Properties()
        props.load this.getClass().getResource('/application.properties').openStream()
        service.setSmsApiUrl(props.getProperty('mobiltek.url'))
        service.setSmsApiOrig(props.getProperty('mobiltek.orig'))
        service.setSmsApiPass(props.getProperty('mobiltek.passwd'))
        service.setSmsApiLogin(props.getProperty('mobiltek.login'))
        service.setSmsApiService(props.getProperty('mobiltek.service'))
    }

    def "should send sms message without error"() {
        given:
            def response = Mock(Response)
        and:
            response.returnContent() >> new Content("OK\n".getBytes(), ContentType.DEFAULT_BINARY)
        and:
            service.send(_ as Request) >> response
        when:
            service.send(SMS_PHONE_NUMBER, SMS_MESSAGE, SMS_ID)
        then:
            true
    }

    def "should throw exception if communication error occurs during sending message"() {
        given:
            def ioException = new IOException()
        and:
            service.send(_ as Request) >> { throw ioException }
        when:
            service.send(SMS_PHONE_NUMBER, SMS_MESSAGE, SMS_ID)
        then:
            def exception = thrown(SmsSendingException)
            exception.getPhoneNumber() == SMS_PHONE_NUMBER
            exception.getSmsMessage() == SMS_MESSAGE
            exception.getCause() == ioException
            exception.getId() == SMS_ID
    }

    def "should throw exception if wrong data was send during sending message"() {
        given:
            def response = Mock(Response)
        and:
            response.returnContent() >> new Content(responseBody.getBytes(), ContentType.DEFAULT_BINARY)
        and:
            service.send(_ as Request) >> response
        when:
            service.send(SMS_PHONE_NUMBER, SMS_MESSAGE, SMS_ID)
        then:
            def exception = thrown(SmsSendingException)
            exception.getPhoneNumber() == SMS_PHONE_NUMBER
            exception.getSmsMessage() == SMS_MESSAGE
            exception.getCause() == null
            exception.getId() == SMS_ID
        where:
            responseBody << ["OK\n1001:Duplicate\n", "ERROR\n4011:parameter id missing\n"]
    }

    /**
     * Należy zmienić numer telefonu na istaniejący, zachowując podany format.
     * Uruchomić test i sprawdzić czy wiadomość dodatarła na podany numer.
     * Wiadomość może zawierać max 160 znaków i nie może mieć polskich liter.
     * Jeżeli zostanie zwrócony błąd odnośnie duplikacji wysyłki należy zmienić id.
     */
    @Ignore("manual testing for developer")
    def "should send message to user with manual sms verification "() {
        given:
            def service = new SmsSenderServiceImpl()
        when:
            service.send(SMS_PHONE_NUMBER, SMS_MESSAGE, SMS_ID)
        then:
            true
    }
}

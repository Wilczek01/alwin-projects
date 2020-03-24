package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.model.message.SmsMessageDto
import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SmsValidatorTest extends Specification {

    @Subject
    SmsValidator validator = new SmsValidator()

    @Unroll
    def "should return validate exception for wrong phone number is '#phoneNumber'"() {
        given:
            def smsMessage = new SmsMessageDto()
            smsMessage.setPhoneNumber(phoneNumber)
        when:
            validator.validate(smsMessage)
        then:
            def e = thrown(AlwinValidationException)
            e.message == "Niepoprawny numer telefonu '$phoneNumber', prawidłowy format to '48xxxxxxxxx'."
        where:
            phoneNumber << [null, "", "+48123234345", "48 123 234 345", "48 123-234-345", "4812323434", "481232343456", "49123234345"]
    }

    def "should return validate exception for wrong sms message"() {
        given:
            def smsMessage = new SmsMessageDto()
            smsMessage.setPhoneNumber('48123234345')
            smsMessage.setMessage(message)
        when:
            validator.validate(smsMessage)
        then:
            def e = thrown(AlwinValidationException)
            e.message == "Wiadomość sms nie może być pusta."
        where:
            message << [null, '', '    ']
    }

    def "should return validate exception for too long sms message"() {
        given:
            def smsMessage = new SmsMessageDto()
            smsMessage.setPhoneNumber('48123234345')
            smsMessage.setMessage('too long sms message, too long sms message, too long sms message, too long sms message, too long sms message, too long sms' +
                    ' message, too long sms message, too lon')
        when:
            validator.validate(smsMessage)
        then:
            def e = thrown(AlwinValidationException)
            e.message == "Wiadomość sms jest za długa. Maksymalna długość to 160 znaków."
    }

    def "should pass validation when message is correct"() {
        given:
            def smsMessage = new SmsMessageDto()
            smsMessage.setPhoneNumber('48123234345')
            smsMessage.setMessage('sms test message')
        when:
            validator.validate(smsMessage)
        then:
            true
    }
}

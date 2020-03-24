package com.codersteam.alwin.core.service.impl.email

import spock.lang.Specification

import static com.codersteam.alwin.testdata.EmailTestData.EMAIL_FROM

/**
 * @author Tomasz Sliwinski
 */
class EmailPropertiesTest extends Specification {

    private EmailProperties emailProperties

    def "setup"() {
        emailProperties = new EmailProperties()
    }

    def "should get emailFrom"() {
        given:
            emailProperties.setEmailFrom(EMAIL_FROM)
        when:
            def emailFrom = emailProperties.getEmailFrom()
        then:
            emailFrom == EMAIL_FROM
    }
}

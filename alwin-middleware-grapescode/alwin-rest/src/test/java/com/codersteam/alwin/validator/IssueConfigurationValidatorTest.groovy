package com.codersteam.alwin.validator

import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.testdata.IssueTypeConfigurationTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.validator.IssueConfigurationValidator.*

/**
 * @author Tomasz Sliwinski
 */
class IssueConfigurationValidatorTest extends Specification {

    @Subject
    private IssueConfigurationValidator validator

    def "setup"() {
        validator = new IssueConfigurationValidator()
    }

    def "should validate dpdStart set null message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.dpdStart = null
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NEGATIVE_OR_ZER0_DPD_START_MESSAGE
    }

    def "should validate dpdStart set 0 message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.dpdStart = 0
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NEGATIVE_OR_ZER0_DPD_START_MESSAGE
    }

    def "should validate dpdStart set negative value message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.dpdStart = -12
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NEGATIVE_OR_ZER0_DPD_START_MESSAGE
    }

    def "should validate dpdStart set positive value message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.dpdStart = 1
        when:
            validator.validate(issueConfiguration)
        then:
            noExceptionThrown()
    }

    def "should validate duration set null message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.duration = null
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NEGATIVE_OR_ZER0_DURATION_MESSAGE
    }

    def "should validate duration set 0 message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.duration = 0
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NEGATIVE_OR_ZER0_DURATION_MESSAGE
    }

    def "should validate duration set negative value message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.duration = -12
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == NEGATIVE_OR_ZER0_DURATION_MESSAGE
    }

    def "should validate duration set positive value message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.duration = 1
        when:
            validator.validate(issueConfiguration)
        then:
            noExceptionThrown()
    }

    def "should validate minBalanceStart set null message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.minBalanceStart = null
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == POSITIVE_OR_ZERO_MIN_BALANCE_START_MESSAGE
    }

    def "should validate minBalanceStart set 0 message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.minBalanceStart = 0.00
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == POSITIVE_OR_ZERO_MIN_BALANCE_START_MESSAGE
    }

    def "should validate minBalanceStart set positive value message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.minBalanceStart = 1.00
        when:
            validator.validate(issueConfiguration)
        then:
            def exception = thrown(AlwinValidationException)
            exception.message == POSITIVE_OR_ZERO_MIN_BALANCE_START_MESSAGE
    }

    def "should validate minBalanceStart set negative value message"() {
        given:
            def issueConfiguration = IssueTypeConfigurationTestData.issueTypeConfigurationDto1()
            issueConfiguration.minBalanceStart = -12.00
        when:
            validator.validate(issueConfiguration)
        then:
            noExceptionThrown()
    }
}

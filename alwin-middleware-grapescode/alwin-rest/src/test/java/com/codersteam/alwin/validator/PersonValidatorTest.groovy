package com.codersteam.alwin.validator

import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.PersonTestData.createPersonDto
import static com.codersteam.alwin.testdata.PersonTestData.personDto1
import static com.codersteam.alwin.validator.PersonValidator.*

/**
 * @author Piotr Narożnik
 */
class PersonValidatorTest extends Specification {

    @Subject
    PersonValidator personValidator = new PersonValidator()

    def "should validate person with missing data"() {
        given:
            def person = createPersonDto()
            person.firstName = null
            person.lastName = null
        when:
            def validationResult = personValidator.validatePerson(person)
        then:
            validationResult.get(FIRST_NAME) == FIRST_NAME_IS_MISSING_MESSAGE
            validationResult.get(LAST_NAME) == LAST_NAME_IS_MISSING_MESSAGE
    }

    def "should validate person with wrong data"() {
        given:
            def person = createPersonDto()
            person.firstName = 'a' * 101
            person.lastName = 'b' * 101
        when:
            def validationResult = personValidator.validatePerson(person)
        then:
            validationResult.get(FIRST_NAME) == FIRST_NAME_IS_TOO_LONG_MESSAGE
            validationResult.get(LAST_NAME) == LAST_NAME_IS_TOO_LONG_MESSAGE

    }

    def "should pass validation"() {
        given:
            def person = createPersonDto()
        when:
            def validationResult = personValidator.validatePerson(person)
        then:
            validationResult == [:]
    }

    def "should remove person ids"() {
        given:
            def person = personDto1()

        when:
            def validationResult = personValidator.validatePerson(person)
        then:
            person.id == null
            person.personId == null
            validationResult == [:]
    }
}
package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.model.user.EditableUserDto
import com.codersteam.alwin.core.api.service.operator.OperatorService
import com.codersteam.alwin.core.api.service.user.UserService
import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification

import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_ID_1
import static java.util.Arrays.asList

/**
 * @author Michal Horowic
 */
class UserValidatorTest extends Specification {

    def "should validate user ids"() {
        given:
            def userToUpdate = new EditableUserDto()

        and:
            def validator = new UserValidator()

        when:
            validator.validate(userToUpdate, userIdToUpdate)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == 'Niezgadzające się identyfikatory użytkowników'

        where:
            userIdToUpdate << [null, TEST_USER_ID_1]
    }

    def "should validate if user exists"() {
        given:
            def userIdToUpdate = TEST_USER_ID_1

        and:
            def userToUpdate = new EditableUserDto()
            userToUpdate.setId(userIdToUpdate)

        and:
            def userService = Mock(UserService)
            userService.doesUserExist(userIdToUpdate) >> false

        and:
            def operatorService = Mock(OperatorService)

        and:
            def validator = new UserValidator(userService, operatorService)

        when:
            validator.validate(userToUpdate, userIdToUpdate)

        then:
            def exception = thrown(EntityNotFoundException)
            exception.message == 'Użytkownik o podanym identyfikatorze nie istnieje'
    }

    def "should validate if multiple operator logins already exist"(serviceReturn, message) {
        given:
            def userIdToUpdate = TEST_USER_ID_1

        and:
            def userToUpdate = new EditableUserDto()
            userToUpdate.setId(userIdToUpdate)
            userToUpdate.setOperators(asList(testEditableOperatorWithoutId1(), testEditableOperator2(), testEditableOperatorWithoutId3()))

        and:
            def userService = Mock(UserService)
            userService.doesUserExist(userIdToUpdate) >> true

        and:
            def operatorService = Mock(OperatorService)
            operatorService.checkIfOperatorsExist(asList(testEditableOperatorWithoutId1().login, testEditableOperatorWithoutId3().login)) >> serviceReturn

        and:
            def validator = new UserValidator(userService, operatorService)

        when:
            validator.validate(userToUpdate, userIdToUpdate)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == message

        where:
            serviceReturn                                                                    | message
            [testEditableOperatorWithoutId1().login, testEditableOperatorWithoutId3().login] | "Operatorzy z loginami [${testEditableOperatorWithoutId1().login}, ${testEditableOperatorWithoutId3().login}] już istnieją"
            [testEditableOperatorWithoutId1().login]                                         | "Operator z loginem [${testEditableOperatorWithoutId1().login}] już istnieje"
    }

    def "should validate if multiple operator logins already duplicated"() {
        given:
            def userIdToUpdate = TEST_USER_ID_1

        and:
            def userToUpdate = new EditableUserDto()
            userToUpdate.setId(userIdToUpdate)
            userToUpdate.setOperators(asList(testEditableOperatorWithoutId1(), testEditableOperatorWithoutId1()))

        and:
            def userService = Mock(UserService)
            userService.doesUserExist(userIdToUpdate) >> true

        and:
            def operatorService = Mock(OperatorService)
            operatorService.checkIfOperatorsExist(asList(testEditableOperatorWithoutId1().login, testEditableOperatorWithoutId1().login)) >> []

        and:
            def validator = new UserValidator(userService, operatorService)

        when:
            validator.validate(userToUpdate, userIdToUpdate)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Operator z loginem ${testEditableOperatorWithoutId1().login} już istnieje"
    }

}

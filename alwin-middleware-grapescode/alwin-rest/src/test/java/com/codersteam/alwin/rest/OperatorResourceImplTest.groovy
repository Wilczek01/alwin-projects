package com.codersteam.alwin.rest

import com.codersteam.alwin.auth.model.ChangePasswordRequest
import com.codersteam.alwin.core.api.service.auth.JwtService
import com.codersteam.alwin.core.api.service.operator.OperatorService
import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.validator.OperatorValidator
import spock.lang.Specification
import spock.lang.Unroll

import static com.codersteam.alwin.testdata.OperatorTestData.*

/**
 * @author Tomasz Sliwinski
 */
class OperatorResourceImplTest extends Specification {

    def "should have default public constructor"() {
        when:
            def resource = new OperatorResource()
        then:
            resource
    }

    @Unroll
    def "should not reset operator password if new password is empty"(String password) {
        given:
            def operatorService = Mock(OperatorService)
            operatorService.findOperatorById(OPERATOR_ID_1) >> testOperatorDto1()

        and:
            def validator = new OperatorValidator(operatorService)

        and:
            def resource = new OperatorResource(operatorService, null, null, null, null, validator)

        when:
            resource.resetPassword(OPERATOR_ID_1, new ChangePasswordRequest(password))

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == "Hasło nie może być puste"

        where:
            password << [null, "", " "]
    }

    def "should not reset password for not existing operator"() {
        given:
            def operatorService = Mock(OperatorService)
            operatorService.findOperatorById(OPERATOR_ID_1) >> null

        and:
            def validator = new OperatorValidator(operatorService)

        and:
            def resource = new OperatorResource(operatorService, null, null, null, null, validator)

        when:
            resource.resetPassword(OPERATOR_ID_1, new ChangePasswordRequest(NEW_PASSWORD))

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == "Operator o podanym identyfikatorze nie istnieje"

    }

    def "should reset operator password"() {
        given:
            def operatorService = Mock(OperatorService)
            operatorService.findOperatorById(OPERATOR_ID_1) >> testOperatorDto1()

        and:
            def validator = new OperatorValidator(operatorService)

        and:
            def resource = new OperatorResource(operatorService, null, null, null, null, validator)

        when:
            resource.resetPassword(OPERATOR_ID_1, new ChangePasswordRequest(NEW_PASSWORD))

        then:
            1 * operatorService.changePassword(OPERATOR_ID_1, NEW_PASSWORD, true)
    }

    @Unroll
    def "should not update operator password if new password is empty"(String password) {
        given:
            def operatorService = Mock(OperatorService)
            operatorService.findOperatorById(OPERATOR_ID_1) >> testOperatorDto1()

        and:
            def validator = new OperatorValidator(operatorService)

        and:
            def resource = new OperatorResource(operatorService, null, null, null, null, validator)

        when:
            resource.changePassword("token", new ChangePasswordRequest(password))

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == "Hasło nie może być puste"

        where:
            password << [null, "", " "]
    }

    def "should update operator password"() {
        given:
            def operatorService = Mock(OperatorService)
            def operator = testOperatorDto1()
            operator.setForceUpdatePassword(true)
            operatorService.findOperatorById(OPERATOR_ID_1) >> operator

        and:
            def validator = new OperatorValidator(operatorService)

        and:
            def token = "token"

        and:
            def jwtService = Mock(JwtService)
            jwtService.getLoggedOperatorId(token) >> OPERATOR_ID_1

        and:
            def resource = new OperatorResource(operatorService, jwtService, null, null, null, validator)

        when:
            resource.changePassword(token, new ChangePasswordRequest(NEW_PASSWORD))

        then:
            1 * operatorService.changePassword(OPERATOR_ID_1, NEW_PASSWORD, false)
    }

    def "should not update operator password if not forced"() {
        given:
            def operatorService = Mock(OperatorService)
            def operator = testOperatorDto1()
            operator.setForceUpdatePassword(false)
            operatorService.findOperatorById(OPERATOR_ID_1) >> operator

        and:
            def validator = new OperatorValidator(operatorService)

        and:
            def token = "token"

        and:
            def jwtService = Mock(JwtService)
            jwtService.getLoggedOperatorId(token) >> OPERATOR_ID_1

        and:
            def resource = new OperatorResource(operatorService, jwtService, null, null, null, validator)

        when:
            resource.changePassword(token, new ChangePasswordRequest(NEW_PASSWORD))

        then:
            def exception = thrown(AlwinValidationException)

        and:
            exception.message == "Nie można zmienić hasła ponieważ nie było ono resetowane"
    }
}

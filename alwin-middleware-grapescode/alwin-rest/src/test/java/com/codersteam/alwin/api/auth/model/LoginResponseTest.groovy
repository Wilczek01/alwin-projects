package com.codersteam.alwin.api.auth.model

import com.codersteam.alwin.auth.model.LoginResponse
import com.codersteam.alwin.core.api.model.operator.PermissionDto
import spock.lang.Specification

import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO

/**
 * @author Tomasz Sliwinski
 */
class LoginResponseTest extends Specification {

    def "should create login response"() {
        given:
            def userName = "userName"
            def role = TEST_OPERATOR_TYPE_DTO
            def forceUpdatePassword = false
            def permission = new PermissionDto()
        when:
            def loginResponse = new LoginResponse(userName, role, forceUpdatePassword, permission)
        then:
            loginResponse.getUsername() == userName
            loginResponse.getRole() == role
            loginResponse.isForceUpdatePassword() == forceUpdatePassword
            loginResponse.getPermission() == permission
    }
}

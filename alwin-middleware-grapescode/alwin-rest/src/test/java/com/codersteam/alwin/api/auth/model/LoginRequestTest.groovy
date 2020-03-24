package com.codersteam.alwin.api.auth.model

import com.codersteam.alwin.auth.model.LoginRequest
import spock.lang.Specification

/**
 * @author Tomasz Sliwinski
 */
class LoginRequestTest extends Specification {

    def "should create loginRequest"() {
        given:
            def password = "password"
            def username = "username"
        when:
            def loginRequest = new LoginRequest()
            loginRequest.setPassword(password)
            loginRequest.setUsername(username)
        then:
            loginRequest.getPassword() == password
            loginRequest.getUsername() == username
    }
}

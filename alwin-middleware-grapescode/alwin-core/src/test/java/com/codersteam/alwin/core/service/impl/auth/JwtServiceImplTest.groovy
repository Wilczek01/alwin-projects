package com.codersteam.alwin.core.service.impl.auth

import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import spock.lang.Specification

/**
 * @author Tomasz Sliwinski
 */
class JwtServiceImplTest extends Specification {

    private JwtServiceImpl sut

    def "setup"() {
        sut = new JwtServiceImpl()
    }

    def "should create token"() {
        given:
            def id = 1L
            def userName = "VeryImportantUser"
            def operatorType = "ADMIN"
            def firstName = "Jan"
            def lastName = "Kowalski"
        when:
            def token = sut.createToken(userName, operatorType, id, firstName, lastName)
        then:
            token
            token.length() > 0
    }

    def "should parse valid token"() {
        given:
            def id = 1L
            def userName = "VeryImportantUser"
            def operatorType = "ADMIN"
            def firstName = "Jan"
            def lastName = "Kowalski"
            def token = sut.createToken(userName, operatorType, id, firstName, lastName)
        when:
            def jwt = sut.parseToken(token)
        then:
            noExceptionThrown()
            jwt
            def operatorTypeFromToken = jwt.getBody().get(JwtServiceImpl.OPERATOR_TYPE)
            operatorTypeFromToken == operatorType
            jwt.getBody().get(JwtServiceImpl.OPERATOR_FULL_NAME) == "$firstName $lastName"
    }

    def "should parse malformed token"() {
        given:
            def token = "malformed token"
        when:
            sut.parseToken(token)
        then:
            thrown(MalformedJwtException)
    }

    def "should parse invalid token"() {
        given:
            def invalidToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJWZXJ5SW1wb3J0YW50VXNlciIsInJvbGUiOiJBRE1JTiIsImV4cCI6MTUwMDIzMDAxNywiaWF0IjoxNTAwMjAxMjE3fQ.xjKTtAxRr9EHsieo1HNg-FV6YId4U-HJz9NKt7A49Jic_mQSdxNHY31ckeF97mh13SMqCb1xvXEK_0ERO_ylJg"
        when:
            sut.parseToken(invalidToken)
        then:
            thrown(SignatureException)
    }

    def "should get logged user id from token"(long userId) {
        given:
            def userName = "VeryImportantUser"
            def operatorType = "ADMIN"
            def firstName = "Jan"
            def lastName = "Kowalski"
            def token = sut.createToken(userName, operatorType, userId, firstName, lastName)
        when:
            def loggedUserId = sut.getLoggedOperatorId(token)
        then:
            loggedUserId == userId

        where:
            userId << [1, 982374987324789]

    }

    def "should get logged user id from token extracted from header"(long userId) {
        given:
            def userName = "VeryImportantUser"
            def operatorType = "ADMIN"
            def firstName = "Jan"
            def lastName = "Kowalski"
            def token = sut.createToken(userName, operatorType, userId, firstName, lastName)
        when:
            def loggedUserId = sut.getLoggedOperatorId(token)
        then:
            loggedUserId == userId

        where:
            userId << [1, 982374987324789]

    }
}

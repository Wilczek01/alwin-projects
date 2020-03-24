package com.codersteam.alwin.rest

import com.codersteam.alwin.auth.model.LoginRequest
import com.codersteam.alwin.auth.model.LoginResponse
import com.codersteam.alwin.core.api.model.operator.OperatorDto
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto
import com.codersteam.alwin.core.api.model.user.UserDto
import com.codersteam.alwin.core.api.service.auth.AuthenticationService
import com.codersteam.alwin.core.api.service.auth.JwtService
import com.codersteam.alwin.core.api.service.user.UserService
import com.codersteam.alwin.validator.UserValidator
import spock.lang.Specification

import static com.codersteam.alwin.testdata.UserTestData.*
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION
import static javax.ws.rs.core.Response.Status.OK
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class UserResourceImplTest extends Specification {

    private UserResource userResource

    private JwtService jwtService
    private AuthenticationService authenticationService
    private UserService userService
    private UserValidator userValidator

    def "setup"() {
        jwtService = Mock(JwtService)
        authenticationService = Mock(AuthenticationService)
        userService = Mock(UserService)
        userValidator = Mock(UserValidator)
        userResource = new UserResource(jwtService, authenticationService, userService, userValidator)
    }

    def "should process unsuccessful login"() {
        given:
            def userName = "invalid user name"
            def password = "invalid user password"
        and:
            def loginRequest = new LoginRequest()
            loginRequest.setUsername(userName)
            loginRequest.setPassword(password)
        and:
            authenticationService.authenticateUser(userName, password) >> Optional.ofNullable(null)
        when:
            def response = userResource.login(loginRequest)
        then:
            response.status == UNAUTHORIZED.statusCode
    }

    def "should process successful login"() {
        given:
            def userName = "valid user name"
            def password = "valid user password"
            def typeName = "ADMIN"
            def typeLabel = "Administrator systemu"
        and:
            def loginRequest = new LoginRequest()
            loginRequest.setUsername(userName)
            loginRequest.setPassword(password)
        and:
            def id = 1L
            def firstName = "Jan"
            def lastName = "Kowalski"
            def type = new OperatorTypeDto(id, typeName, typeLabel)
            def userDto = new UserDto(id, firstName, lastName)
            def operatorDto = new OperatorDto()
            operatorDto.user = userDto
            operatorDto.type = type
            operatorDto.id = id
        and:
            authenticationService.authenticateUser(userName, password) >> Optional.of(operatorDto)
        and:
            def token = "token"
            jwtService.createToken(userName, operatorDto.getType().getTypeName(), id, operatorDto.getUser().getFirstName(), operatorDto.getUser().getLastName()) >> token
        when:
            def response = userResource.login(loginRequest)
        then:
            response.status == OK.statusCode
            response.headers[AUTHORIZATION][0] == UserResource.BEARER_PREFIX + token
            response.headers[UserResource.ACCESS_CONTROL_EXPOSE_HEADERS][0] == AUTHORIZATION
        and:
            def responseEntity = response.entity as LoginResponse
            responseEntity.role.typeName == typeName
            responseEntity.username == String.format(UserResource.USER_NAME_FORMAT, firstName, lastName)
    }

    def "should return page of users"(int firstResult, int maxResult, def expectedPage) {
        given:
            userService.findAllUsers(firstResult, maxResult) >> expectedPage

        when:
            def result = userResource.findAllUsers(firstResult, maxResult, null)

        then: 'issues were found'
            assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedPage)

        where:
            firstResult | maxResult | expectedPage
            0           | 2         | FIRST_PAGE_OF_USERS_DTO
            2           | 2         | SECOND_PAGE_OF_USERS_DTO
    }

    def "should return page of users filtered by login or name"(int firstResult, int maxResult, def expectedPage) {
        given:
            def searchText = "Test test"

        and:
            userService.findAllUsersFilterByNameAndLogin(firstResult, maxResult, searchText) >> expectedPage

        when:
            def result = userResource.findAllUsers(firstResult, maxResult, searchText)

        then: 'issues were found'
            assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedPage)

        where:
            firstResult | maxResult | expectedPage
            0           | 2         | FIRST_PAGE_OF_USERS_DTO
            2           | 2         | SECOND_PAGE_OF_USERS_DTO
    }

    def "should return error response if service failed to create a user"() {
        given:
            def user = TEST_USER_DTO_1

        and:
            userService.createUser(user) >> { throw new Exception() }

        when:
            userResource.createUser(user)

        then:
            thrown(Exception)
    }

    def "should return error response if service failed to update a user"() {
        given:
            def user = alwinEditableUserDto1()

        and:
            userService.updateUser(user) >> { throw new Exception() }

        and:
            userValidator.validate(user, user.id) >> {}

        when:
            userResource.updateUser(user.id, user)

        then:
            thrown(Exception)
    }

    def "should have default public constructor"() {
        when:
            def resource = new UserResource()
        then:
            resource
    }
}

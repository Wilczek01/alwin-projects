package com.codersteam.alwin.core.service.impl.auth

import com.codersteam.alwin.core.api.model.operator.OperatorDto
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.OperatorDao
import com.codersteam.alwin.jpa.operator.Operator
import spock.lang.Specification

/**
 * @author Michal Horowic
 */
class AuthenticationServiceImplTest extends Specification {

    def mapper = Mock(AlwinMapper)

    def "should authenticate operator if correct password was provided"() {
        given: 'operator credentials'
            def login = 'amickiewicz'
            def password = 'test'

        and: 'operator in database'
            def operator = new Operator()
            operator.setLogin(login)
            operator.setSalt('7ad53ec9-876f-4cf3-b7cd-526a153e5836')
            operator.setPassword('30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d')

        and: 'operator data access object'
            def operatorDao = Mock(OperatorDao)
            operatorDao.findActiveOperatorByLogin(login) >> Optional.of(operator)

        and: 'authentication service'
            def authenticationService = new AuthenticationServiceImpl(operatorDao, mapper)

        and:
            mapper.map(operator, OperatorDto) >> Mock(OperatorDto)

        when: 'authenticating operator'
            def authenticatedUser = authenticationService.authenticateUser(login, password)

        then: 'operator is authenticated'
            authenticatedUser.isPresent()
    }

    def "should not authenticate operator if wrong password was provided"() {
        given: 'operator credentials'
            def login = 'amickiewicz'
            def wrongPassword = 'testqwerty'

        and: 'operator in database'
            def operator = new Operator()
            operator.setLogin(login)
            operator.setSalt('7ad53ec9-876f-4cf3-b7cd-526a153e5836')
            operator.setPassword('30e2bd81539d250a9db53a89bd51a94a38eb8d1a41319f6d01bf5a5779ed7db5ba9964a69326ccd875c6f5b1e22b2e2f550fc55f882eaad64b50318bfa79ce8d')

        and: 'operator data access object'
            def operatorDao = Mock(OperatorDao)
            operatorDao.findActiveOperatorByLogin(login) >> Optional.of(operator)

        and: 'authentication service'
            def authenticationService = new AuthenticationServiceImpl(operatorDao, mapper)

        when: 'authenticating operator'
            def authenticatedUser = authenticationService.authenticateUser(login, wrongPassword)

        then: 'operator is not authenticated'
            !authenticatedUser.isPresent()
    }

    def "should not authenticate operator if user with given login does not exist"() {
        given: 'user credentials'
            def login = 'amickiewicz'
            def password = 'test'

        and: 'user data access object'
            def operatorDao = Mock(OperatorDao)
            operatorDao.findActiveOperatorByLogin(login) >> Optional.empty()

        and: 'authentication service'
            def authenticationService = new AuthenticationServiceImpl(operatorDao, mapper)

        when: 'authenticating user'
            def authenticatedUser = authenticationService.authenticateUser(login, password)

        then: 'user is not authenticated'
            !authenticatedUser.isPresent()
    }
}

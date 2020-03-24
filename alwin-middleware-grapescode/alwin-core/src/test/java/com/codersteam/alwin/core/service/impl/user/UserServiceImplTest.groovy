package com.codersteam.alwin.core.service.impl.user

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.OperatorDao
import com.codersteam.alwin.db.dao.OperatorTypeDao
import com.codersteam.alwin.db.dao.UserDao
import com.codersteam.alwin.jpa.User
import spock.lang.Specification

import static com.codersteam.alwin.testdata.OperatorTestData.testEditableOperator1
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypeAdmin
import static com.codersteam.alwin.testdata.UserTestData.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class UserServiceImplTest extends Specification {

    def "should find page of all users"(int firstResult, int maxResult, def expectedPage, def expectedResult) {
        given:
            def userDao = Mock(UserDao)
            userDao.findAllUsers(firstResult, maxResult) >> expectedPage
            userDao.findAllUsersCount() >> TOTAL_VALUES

        and:
            def operatorDao = Mock(OperatorDao)

        and:
            def operatorTypeDao = Mock(OperatorTypeDao)

        and:
            def userService = new UserServiceImpl(userDao, operatorDao, operatorTypeDao, new AlwinMapper())

        when: 'finding first page of all users'
            def result = userService.findAllUsers(firstResult, maxResult)

        then: 'users were found'
            assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult)

        where:
            firstResult | maxResult | expectedPage         | expectedResult
            0           | 2         | FIRST_PAGE_OF_USERS  | FIRST_PAGE_OF_USERS_DTO
            2           | 2         | SECOND_PAGE_OF_USERS | SECOND_PAGE_OF_USERS_DTO
    }

    def "should find page of all users filtered by login or name"(int firstResult, int maxResult, def expectedPage, def expectedResult) {
        given:
            def searchText = "Firstname Lastname"

        and:
            def userDao = Mock(UserDao)
            userDao.findAllUsersFilterByNameAndLogin(firstResult, maxResult, searchText) >> expectedPage
            userDao.findAllUsersFilterByNameAndLoginCount(searchText) >> TOTAL_VALUES

        and:
            def operatorDao = Mock(OperatorDao)

        and:
            def operatorTypeDao = Mock(OperatorTypeDao)

        and:
            def userService = new UserServiceImpl(userDao, operatorDao, operatorTypeDao, new AlwinMapper())

        when: 'finding first page of all users'
            def result = userService.findAllUsersFilterByNameAndLogin(firstResult, maxResult, searchText)

        then: 'users were found'
            assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult)

        where:
            firstResult | maxResult | expectedPage         | expectedResult
            0           | 2         | FIRST_PAGE_OF_USERS  | FIRST_PAGE_OF_USERS_DTO
            2           | 2         | SECOND_PAGE_OF_USERS | SECOND_PAGE_OF_USERS_DTO
    }

    def "should create new user"() {
        given:
            def userDao = Mock(UserDao)

        and:
            def operatorDao = Mock(OperatorDao)

        and:
            def operatorTypeDao = Mock(OperatorTypeDao)

        and:
            def userService = new UserServiceImpl(userDao, operatorDao, operatorTypeDao, new AlwinMapper())

        when:
            userService.createUser(TEST_USER_DTO_1)

        then:
            1 * userDao.create(_ as User)
    }

    def "should update user with operators"() {
        given:
            def userDao = Mock(UserDao)
            userDao.get(alwinEditableUserDto1().id) >> Optional.of(alwinUser1())

        and:
            def operatorDao = Mock(OperatorDao)

        and:
            def operatorTypeDao = Mock(OperatorTypeDao)
            operatorTypeDao.get(testEditableOperator1().type.id) >> Optional.of(operatorTypeAdmin())

        and:
            def userService = new UserServiceImpl(userDao, operatorDao, operatorTypeDao, new AlwinMapper())

        when:
            userService.updateUser(alwinEditableUserDto1())

        then:
            1 * userDao.update(_ as User)
    }

    def "should check if user exists"(dbUser, expectedResult) {
        given:
            def userDao = Mock(UserDao)
            userDao.get(TEST_USER_ID_1) >> dbUser

        and:
            def operatorDao = Mock(OperatorDao)

        and:
            def operatorTypeDao = Mock(OperatorTypeDao)

        and:
            def userService = new UserServiceImpl(userDao, operatorDao, operatorTypeDao, new AlwinMapper())

        when:
            def result = userService.doesUserExist(TEST_USER_ID_1)

        then:
            result == expectedResult

        where:
            dbUser                    | expectedResult
            Optional.empty()          | false
            Optional.of(alwinUser1()) | true
    }

    def "should find user"(dbUser, expectedResult) {
        given:
            def userDao = Mock(UserDao)
            userDao.get(TEST_USER_ID_1) >> dbUser

        and:
            def operatorDao = Mock(OperatorDao)

        and:
            def operatorTypeDao = Mock(OperatorTypeDao)

        and:
            def userService = new UserServiceImpl(userDao, operatorDao, operatorTypeDao, new AlwinMapper())

        when:
            def result = userService.findUser(TEST_USER_ID_1)

        then:
            result.isPresent() == expectedResult

        where:
            dbUser                    | expectedResult
            Optional.empty()          | false
            Optional.of(alwinUser1()) | true
    }

}

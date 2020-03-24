package com.codersteam.alwin.core.service.impl.operator

import com.codersteam.alwin.core.api.model.common.Page
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueTypeDao
import com.codersteam.alwin.db.dao.OperatorDao
import com.codersteam.alwin.jpa.type.OperatorNameType
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER
import static com.codersteam.alwin.jpa.type.OperatorNameType.ACCOUNT_MANAGER
import static com.codersteam.alwin.jpa.type.OperatorNameType.FIELD_DEBT_COLLECTOR
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeWithEmptyOperatorTypes
import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_EMAIL_1
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_EMAIL_2
import static java.util.Arrays.asList
import static java.util.Collections.emptyList
import static java.util.Collections.singletonList
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class OperatorServiceImplTest extends Specification {

    @Subject
    private OperatorServiceImpl service

    private AlwinMapper mapper = new AlwinMapper()
    private OperatorDao operatorDao = Mock(OperatorDao)
    private IssueTypeDao issueTypeDao = Mock(IssueTypeDao)

    def "setup"() {
        service = new OperatorServiceImpl(operatorDao, issueTypeDao, mapper)
    }

    def "should find managed operators"() {
        given:
            def parentOperatorId = testOperator4().getId()

        and:
            operatorDao.findActiveManagedOperators(parentOperatorId) >> singletonList(testOperator2())

        when:
            def result = service.findManagedOperators(parentOperatorId, null)

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(singletonList(testOperatorDto2()))
    }

    def "should find account managers"() {
        given:
            operatorDao.findOperatorsByNameType(ACCOUNT_MANAGER) >> singletonList(testOperator2())

        when:
            def result = service.findAllAccountManagers()

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(singletonList(testOperatorDto2()))
    }

    def "should return empty list of managed operators"() {
        given:
            def parentOperatorId = testOperator4().getId()

        and:
            operatorDao.findActiveManagedOperators(parentOperatorId) >> emptyList()

        when:
            def result = service.findManagedOperators(parentOperatorId, null)

        then:
            result.isEmpty()
    }

    def "should find operators assign to company activities"() {
        given:
            operatorDao.findOperatorsAssignToCompanyActivities(COMPANY_ID_1) >> [testOperator1(), testOperator2()]

        when:
            def result = service.findOperatorsAssignToCompanyActivities(COMPANY_ID_1)

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively([testOperatorUserDto1(), testOperatorUserDto2()])
    }

    def "should return empty list of operators assign to company activities"() {
        given:
            operatorDao.findOperatorsAssignToCompanyActivities(COMPANY_ID_1) >> emptyList()

        when:
            def result = service.findOperatorsAssignToCompanyActivities(COMPANY_ID_1)

        then:
            result.isEmpty()
    }

    def "should return empty email list when no operator of given type found"() {
        given:
            operatorDao.findOperatorsByNameType(OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER) >> []
        when:
            def emails = service.findOperatorEmails(PHONE_DEBT_COLLECTOR_MANAGER)
        then:
            emails.size() == 0
    }

    def "should return emails of operators of given type"() {
        given:
            operatorDao.findOperatorsByNameType(OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER) >> [testOperator1(), testOperator2()]
        when:
            def emails = service.findOperatorEmails(PHONE_DEBT_COLLECTOR_MANAGER)
        then:
            emails.size() == 2
            emails.containsAll([TEST_USER_EMAIL_1, TEST_USER_EMAIL_2])
    }

    def "should find operator by id"() {
        given:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator1())
        when:
            def operator = service.findOperatorById(OPERATOR_ID_1)
        then:
            assertThat(operator).isEqualToComparingFieldByFieldRecursively(testOperatorDto1())
    }

    def "should not find operator by id"() {
        given:
            operatorDao.get(OPERATOR_ID_1) >> Optional.empty()
        when:
            def operator = service.findOperatorById(OPERATOR_ID_1)
        then:
            operator == null
    }

    def "should check if operator exists"(exists, expectedResult) {
        given:
            operatorDao.exists(OPERATOR_ID_1) >> exists

        when:
            def result = service.checkIfOperatorExists(OPERATOR_ID_1)

        then:
            result == expectedResult

        where:
            exists | expectedResult
            true   | true
            false  | false
    }

    def "should find page of active field debt collectors"() {
        given:
            operatorDao.findActiveOperatorsByType(FIELD_DEBT_COLLECTOR, 0, 5) >> asList(testOperator1(), testOperator2())
        and:
            operatorDao.findActiveOperatorsByTypeCount(FIELD_DEBT_COLLECTOR) >> 2
        when:
            def result = service.findActiveFieldDebtCollectors(0, 5)
        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(new Page<>(asList(testOperatorDto1(), testOperatorDto2()), 2))
    }

    def "should not filter operators by issue type when issue type id is null"() {
        given:
            def operators = asList(testOperator1(), testOperator2())

        when:
            def filteredOperators = service.filterOperatorsByIssueType(operators, null)

        then:
            filteredOperators == operators

        and:
            0 * issueTypeDao.findIssueTypeById(_ as Long)
    }

    def "should filter operators by issue type when issue type is present"() {
        given:
            def operators = asList(testOperator1(), testOperator2(), testOperator12(), testOperator15(), testOperator16())

        and:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueType1())

        when:
            def filteredOperators = service.filterOperatorsByIssueType(operators, ISSUE_TYPE_ID_1)

        then:
            filteredOperators.size() == 3
            filteredOperators.get(0).id == testOperator1().id
            filteredOperators.get(1).id == testOperator2().id
            filteredOperators.get(2).id == testOperator15().id
    }

    def "should not filter operators by issue type when issue type not exists"() {
        given:
            def operators = asList(testOperator1(), testOperator2(), testOperator12(), testOperator15(), testOperator16())

        and:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.empty()

        when:
            def filteredOperators = service.filterOperatorsByIssueType(operators, ISSUE_TYPE_ID_1)

        then:
            filteredOperators == operators
    }

    def "should filter operators by issue type when issue type has no operatora types exists"() {
        given:
            def operators = asList(testOperator1(), testOperator2(), testOperator12(), testOperator15(), testOperator16())

        and:
            issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1) >> Optional.of(issueTypeWithEmptyOperatorTypes())

        when:
            def filteredOperators = service.filterOperatorsByIssueType(operators, ISSUE_TYPE_ID_1)

        then:
            filteredOperators.size() == 0
    }
}

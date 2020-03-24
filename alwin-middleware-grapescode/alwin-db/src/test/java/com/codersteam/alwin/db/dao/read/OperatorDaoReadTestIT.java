package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.testdata.assertion.OperatorAssert;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_1;
import static com.codersteam.alwin.jpa.type.OperatorNameType.FIELD_DEBT_COLLECTOR;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_12;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_2;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_LOGIN_1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_LOGIN_2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator15;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator16;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator4;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator6;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_FIRST_NAME_1;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_LAST_NAME_1;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OperatorDaoReadTestIT extends ReadTestBase {

    @EJB
    private OperatorDao operatorDao;

    @Test
    public void shouldFindActiveUserByLogin() {
        // when
        final Optional<Operator> operator = operatorDao.findActiveOperatorByLogin(testOperator1().getLogin());

        // then
        assertTrue(operator.isPresent());
        OperatorAssert.assertEquals(operator.get(), testOperator1());
    }

    @Test
    public void shouldFindActiveManagedOperators() {
        // when
        final List<Operator> operators = operatorDao.findActiveManagedOperators(testOperator4().getId());

        // then
        OperatorAssert.assertEquals(operators, asList(testOperator2(), testOperator15(), testOperator16()));
    }

    @Test
    public void shouldFindOperatorsAssignedToCompanyActivities() {
        // when
        final List<Operator> operators = operatorDao.findOperatorsAssignToCompanyActivities(COMPANY_ID_1);

        // then
        OperatorAssert.assertEquals(operators, asList(testOperator1(), testOperator2()));
    }

    @Test
    public void shouldFindOperatorByNameType() {
        // when
        final List<Operator> operators = operatorDao.findOperatorsByNameType(PHONE_DEBT_COLLECTOR_MANAGER);

        // then
        OperatorAssert.assertEquals(operators, singletonList(testOperator4()));
    }

    @Test
    public void shouldFindExistingOperatorLogins() {
        // given
        final List<String> logins = asList(OPERATOR_LOGIN_1, OPERATOR_LOGIN_2, "not_existing_login");

        // when
        final List<String> operators = operatorDao.findExistingOperatorLogins(logins);

        // then
        assertEquals(2, operators.size());
        assertTrue(operators.contains(OPERATOR_LOGIN_1));
        assertTrue(operators.contains(OPERATOR_LOGIN_2));
    }

    @Test
    public void shouldGetPaginatedUsersFilteredByName() {
        // given
        final String searchText = TEST_USER_FIRST_NAME_1 + " " + TEST_USER_LAST_NAME_1;

        // when
        final List<Operator> firstPage = operatorDao.findAllOperatorsFilterByNameAndLogin(0, 2, searchText, OPERATOR_ID_2);

        // then
        OperatorAssert.assertEquals(firstPage, singletonList(testOperator1()));
    }

    @Test
    public void shouldGetPaginatedUsersFilteredByLogin() {
        // when
        final List<Operator> firstPage = operatorDao.findAllOperatorsFilterByNameAndLogin(0, 2, OPERATOR_LOGIN_1, OPERATOR_ID_2);

        // then
        OperatorAssert.assertEquals(firstPage, singletonList(testOperator1()));
    }

    @Test
    public void shouldGetPaginatedUsersFilteredByPartOfTheLogin() {
        //given
        final String partOfTheLogin = OPERATOR_LOGIN_1.substring(0, OPERATOR_LOGIN_1.length() / 2);

        // when
        final List<Operator> firstPage = operatorDao.findAllOperatorsFilterByNameAndLogin(0, 2, partOfTheLogin, OPERATOR_ID_2);

        // then
        OperatorAssert.assertEquals(firstPage, singletonList(testOperator1()));
    }

    @Test
    public void shouldGetTotalUsersNumberFilteredByLogin() {
        // when
        final long result = operatorDao.findAllOperatorsFilterByNameAndLoginCount(OPERATOR_LOGIN_1, OPERATOR_ID_2);

        // then
        assertEquals(1, result);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Operator> type = operatorDao.getType();

        // then
        assertEquals(Operator.class, type);
    }

    @Test
    public void shouldNotFindOperatorIssueTypes() {
        // when
        final List<IssueTypeName> operatorIssueTypes = operatorDao.findOperatorIssueTypes(OPERATOR_ID_12);

        // then
        assertEquals(0, operatorIssueTypes.size());
    }

    @Test
    public void shouldFindOperatorIssueType() {
        // when
        final List<IssueTypeName> operatorIssueTypes = operatorDao.findOperatorIssueTypes(OPERATOR_ID_2);

        // then
        assertEquals(1, operatorIssueTypes.size());
        assertEquals(PHONE_DEBT_COLLECTION_1, operatorIssueTypes.get(0));
    }

    @Test
    public void shouldFindActiveOperatorsByType() {
        // when
        final List<Operator> operators = operatorDao.findActiveOperatorsByType(FIELD_DEBT_COLLECTOR, 0, 5);

        // then
        assertEquals(1, operators.size());
        OperatorAssert.assertEquals(operators.get(0), testOperator6());
    }

    @Test
    public void shouldFindActiveOperatorsByTypeCount() {
        // when
        final long count = operatorDao.findActiveOperatorsByTypeCount(FIELD_DEBT_COLLECTOR);

        // then
        assertEquals(1, count);
    }
}
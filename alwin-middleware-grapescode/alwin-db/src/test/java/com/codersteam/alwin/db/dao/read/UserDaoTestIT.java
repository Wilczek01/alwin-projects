package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.UserDao;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.operator.Operator;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Comparator;
import java.util.List;

import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_LOGIN_1;
import static com.codersteam.alwin.testdata.UserTestData.FIRST_PAGE_OF_USERS;
import static com.codersteam.alwin.testdata.UserTestData.SECOND_PAGE_OF_USERS;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_FIRST_NAME_1;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_LAST_NAME_1;
import static com.codersteam.alwin.testdata.UserTestData.TOTAL_VALUES;
import static com.codersteam.alwin.testdata.UserTestData.alwinUser1;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class UserDaoTestIT extends ReadTestBase {

    @EJB
    private UserDao userDao;

    @Test
    public void shouldGetTotalUsersNumber() {
        // when
        final long result = userDao.findAllUsersCount();

        // then
        assertEquals(TOTAL_VALUES, result);
    }

    @Test
    public void shouldGetPaginatedAllUsers() {
        // when
        final List<User> firstPage = userDao.findAllUsers(0, 2);
        final List<User> secondPage = userDao.findAllUsers(2, 4);

        // then
        assertUsersListEquals(firstPage, FIRST_PAGE_OF_USERS);

        // and
        assertUsersListEquals(secondPage, SECOND_PAGE_OF_USERS);
    }

    @Test
    public void shouldGetPaginatedUsersFilteredByName() {
        // given
        final String searchText = TEST_USER_FIRST_NAME_1 + " " + TEST_USER_LAST_NAME_1;

        // when
        final List<User> firstPage = userDao.findAllUsersFilterByNameAndLogin(0, 2, searchText);

        // then
        assertUsersListEquals(firstPage, singletonList(alwinUser1()));
    }

    @Test
    public void shouldGetPaginatedUsersFilteredByLogin() {
        // when
        final List<User> firstPage = userDao.findAllUsersFilterByNameAndLogin(0, 2, OPERATOR_LOGIN_1);

        // then
        assertUsersListEquals(firstPage, singletonList(alwinUser1()));
    }

    @Test
    public void shouldGetPaginatedUsersFilteredByPartOfTheLogin() {
        //given
        final String partOfTheLogin = OPERATOR_LOGIN_1.substring(0, OPERATOR_LOGIN_1.length() / 2);

        // when
        final List<User> firstPage = userDao.findAllUsersFilterByNameAndLogin(0, 2, partOfTheLogin);

        // then
        assertUsersListEquals(firstPage, singletonList(alwinUser1()));
    }

    @Test
    public void shouldGetTotalUsersNumberFilteredByLogin() {
        // when
        final long result = userDao.findAllUsersFilterByNameAndLoginCount(OPERATOR_LOGIN_1);

        // then
        assertEquals(1, result);
    }

    private void assertUsersListEquals(final Object actual, final Object expected) {
        AssertionsForClassTypes.assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "creationDate", "updateDate")
                .usingComparatorForFields((Comparator<List<Operator>>) (o1, o2) -> 0, "operators")
                .isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        Class<User> type = userDao.getType();

        // then
        assertEquals(User.class, type);
    }

}
package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.UserDao;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.operator.Operator;
import org.assertj.core.api.AssertionsForClassTypes;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypeAdmin;
import static com.codersteam.alwin.testdata.UserTestData.alwinUser1;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@UsingDataSet({"test-permission.json", "test-data.json"})
public class UserDaoTestIT extends WriteTestBase {

    @EJB
    private UserDao userDao;

    @Test
    public void shouldCreateNewUser() {
        // given
        final User user = buildNewUser();

        // when
        userDao.create(user);

        // then
        final Optional<User> savedUser = userDao.get(user.getId());
        assertTrue(savedUser.isPresent());
        assertUsersListEquals(savedUser.get(), user);
    }

    @Test
    public void shouldUpdateUserWithOperators() {
        // given
        final User expected = alwinUser1();
        expected.setFirstName("FNameTest");

        // and
        final User user = alwinUser1();
        assertNotEquals(user.getFirstName(), expected.getFirstName());
        user.setFirstName(expected.getFirstName());

        // and
        final Operator firstOperator = buildNewOperator(user);
        user.setOperators(singletonList(firstOperator));

        // and
        expected.setOperators(singletonList(firstOperator));

        // when
        final User updatedUser = userDao.update(user);

        // then
        assertEquals(1, updatedUser.getOperators().size());
        firstOperator.setId(updatedUser.getOperators().get(0).getId());

        // and
        final Optional<User> savedUser = userDao.get(user.getId());
        assertTrue(savedUser.isPresent());

        assertUsersListEquals(savedUser.get(), expected);
        assertUsersListEquals(savedUser.get().getOperators(), expected.getOperators());
    }

    private void assertUsersListEquals(final Object actual, final Object expected) {
        AssertionsForClassTypes.assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "creationDate", "updateDate")
                .usingComparatorForFields((Comparator<List<Operator>>) (o1, o2) -> 0, "operators")
                .isEqualToComparingFieldByFieldRecursively(expected);
    }

    private User buildNewUser() {
        final User user = new User();
        user.setFirstName("FTest");
        user.setLastName("LTest");
        user.setPhoneNumber("123123123");
        user.setCreationDate(new Date());
        user.setUpdateDate(new Date());
        user.setEmail("test@grapescode.pl");
        return user;
    }

    private Operator buildNewOperator(final User user) {
        final Operator firstOperator = new Operator();
        firstOperator.setUser(user);
        firstOperator.setLogin("test");
        firstOperator.setSalt("test");
        firstOperator.setPassword("test");
        firstOperator.setActive(true);
        firstOperator.setType(operatorTypeAdmin());
        return firstOperator;
    }
}
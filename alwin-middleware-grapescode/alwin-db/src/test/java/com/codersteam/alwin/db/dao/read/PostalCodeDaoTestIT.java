package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.PostalCodeDao;
import com.codersteam.alwin.jpa.PostalCode;
import com.codersteam.alwin.jpa.operator.Operator;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.PostalCodeTestData.MASK_1;
import static com.codersteam.alwin.testdata.PostalCodeTestData.NOT_EXISTING_MASK;
import static com.codersteam.alwin.testdata.PostalCodeTestData.OPERATOR_1;
import static com.codersteam.alwin.testdata.PostalCodeTestData.OPERATOR_4;
import static com.codersteam.alwin.testdata.PostalCodeTestData.OPERATOR_5;
import static com.codersteam.alwin.testdata.PostalCodeTestData.testPostalCode1;
import static com.codersteam.alwin.testdata.PostalCodeTestData.testPostalCode2;
import static com.codersteam.alwin.testdata.PostalCodeTestData.testPostalCode3;
import static com.codersteam.alwin.testdata.PostalCodeTestData.testPostalCode4;
import static com.codersteam.alwin.testdata.PostalCodeTestData.testPostalCode5;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostalCodeDaoTestIT extends ReadTestBase {

    @EJB
    private PostalCodeDao dao;

    @Test
    public void shouldFindPostalCodeWithNoMask() {
        // when
        final List<PostalCode> postalCodes = dao.findPostalCodesByMask("12-345");

        // then
        assertThat(postalCodes)
                .usingComparatorForFields(SKIP_COMPARATOR, "operator.user", "operator.parentOperator")
                .isEqualToComparingFieldByFieldRecursively(asList(testPostalCode1(), testPostalCode2(), testPostalCode3()));
    }

    @Test
    public void shouldFindPostalCodeMatchingMask() {
        // when
        final List<PostalCode> postalCodes = dao.findPostalCodesByMask("12-346");

        // then
        assertThat(postalCodes)
                .usingComparatorForFields(SKIP_COMPARATOR, "operator.user", "operator.parentOperator")
                .isEqualToComparingFieldByFieldRecursively(asList(testPostalCode2(), testPostalCode3()));
    }

    @Test
    public void shouldFindPostalCodeForPartialMask() {
        // when
        final List<PostalCode> postalCodes = dao.findPostalCodesByMask("12-xxx");

        // then
        assertThat(postalCodes)
                .usingComparatorForFields(SKIP_COMPARATOR, "operator.user", "operator.parentOperator")
                .isEqualToComparingFieldByFieldRecursively(asList(testPostalCode1(), testPostalCode2(), testPostalCode3()));
    }

    @Test
    public void shouldFindPostalCodeForMask() {
        // when
        final List<PostalCode> postalCodes = dao.findPostalCodesByMask("23-451");

        // then
        assertThat(postalCodes)
                .usingComparatorForFields(SKIP_COMPARATOR, "operator.user", "operator.parentOperator")
                .isEqualToComparingFieldByFieldRecursively(asList(testPostalCode4(), testPostalCode5()));
    }

    @Test
    public void shouldFindPostalCodeForFullMask() {
        // when
        final List<PostalCode> postalCodes = dao.findPostalCodesByMask("2x-xxx");

        // then
        assertThat(postalCodes)
                .usingComparatorForFields(SKIP_COMPARATOR, "operator.user", "operator.parentOperator")
                .isEqualToComparingFieldByFieldRecursively(asList(testPostalCode4(), testPostalCode5()));
    }

    @Test
    public void shouldFindTheMostMatchingOperatorForPostalCode() {
        // when
        final Optional<Operator> operator = dao.findOperatorForPostalCode("12-345");

        // then
        assertTrue(operator.isPresent());
        assertThat(operator.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "user", "parentOperator")
                .isEqualToComparingFieldByFieldRecursively(OPERATOR_1);
    }

    @Test
    public void shouldFindTheMostMatchingOperatorForPostalCodeIfThereIsNoFullCodeAssignment() {
        // when
        final Optional<Operator> operator = dao.findOperatorForPostalCode("23-456");

        // then
        assertTrue(operator.isPresent());
        assertThat(operator.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "user", "parentOperator")
                .isEqualToComparingFieldByFieldRecursively(OPERATOR_4);
    }

    @Test
    public void shouldFindTheMostMatchingOperatorForPostalCodeIfThereIsOnlyFullMaskAssignment() {
        // when
        final Optional<Operator> operator = dao.findOperatorForPostalCode("29-999");

        // then
        assertTrue(operator.isPresent());
        assertThat(operator.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "user", "parentOperator")
                .isEqualToComparingFieldByFieldRecursively(OPERATOR_5);
    }

    @Test
    public void shouldCheckThatMaskAlreadyExists() {
        // when
        final boolean result = dao.checkIfPostalCodeMaskExists(MASK_1);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldCheckThatMaskDoesNotExist() {
        // when
        final boolean result = dao.checkIfPostalCodeMaskExists(NOT_EXISTING_MASK);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldFindPostalCodeForPartialMaskCount() {
        // when
        final long count = dao.findPostalCodesByMaskCount("12-xxx");

        // then
        assertEquals(3, count);
    }

}

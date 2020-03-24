package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.IssueTypeDao;
import com.codersteam.alwin.jpa.issue.IssueType;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_2;
import static com.codersteam.alwin.jpa.type.OperatorNameType.ACCOUNT_MANAGER;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ALL_ISSUE_TYPES;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.NOT_EXISTING_ISSUE_TYPE_ID;
import static com.codersteam.alwin.testdata.IssueTypeTestData.PHONE_DEBT_MANAGER_ISSUE_TYPES;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Piotr Naroznik
 */
public class IssueTypeDaoTestIT extends ReadTestBase {

    @EJB
    private IssueTypeDao issueTypeDao;

    @Test
    public void shouldReturnAllIssueTypeTransitions() throws Exception {
        //when
        final IssueType issueTypeByName = issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_2);

        //then
        AssertionsForClassTypes.assertThat(issueTypeByName).isEqualToComparingFieldByFieldRecursively(issueType2());
    }

    @Test
    public void shouldFindAllTypes() {
        // when
        final List<IssueType> allIssueTypes = issueTypeDao.findAllIssueTypes();

        // then
        assertEquals(ALL_ISSUE_TYPES.size(), allIssueTypes.size());
        assertThat(allIssueTypes).isEqualToComparingFieldByFieldRecursively(ALL_ISSUE_TYPES);
    }

    @Test
    public void shouldFindIssueTypeById() {
        // when
        final Optional<IssueType> issueType = issueTypeDao.findIssueTypeById(ISSUE_TYPE_ID_1);

        // then
        assertTrue(issueType.isPresent());
        assertThat(issueType.get()).isEqualToComparingFieldByField(issueType1());
    }

    @Test
    public void shouldNotFindIssueTypeById() {
        // when
        final Optional<IssueType> issueType = issueTypeDao.findIssueTypeById(NOT_EXISTING_ISSUE_TYPE_ID);

        // then
        assertFalse(issueType.isPresent());
    }

    @Test
    public void shouldFindIssueTypesByOperatorNameType() {
        // when
        final List<IssueType> issueTypes = issueTypeDao.findIssueTypesByOperatorNameType(PHONE_DEBT_COLLECTOR_MANAGER);

        // then
        assertEquals(PHONE_DEBT_MANAGER_ISSUE_TYPES.size(), issueTypes.size());
        assertThat(issueTypes).isEqualToComparingFieldByFieldRecursively(PHONE_DEBT_MANAGER_ISSUE_TYPES);
    }

    @Test
    public void shouldNotFindIssueTypesByOperatorNameType() {
        // when
        final List<IssueType> issueTypes = issueTypeDao.findIssueTypesByOperatorNameType(ACCOUNT_MANAGER);

        // then
        assertTrue(issueTypes.isEmpty());
    }

    @Test
    public void getType() throws Exception {
        assertEquals(IssueType.class, issueTypeDao.getType());
    }
}
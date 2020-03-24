package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.IssueWallet;
import com.codersteam.alwin.jpa.TagWallet;
import com.codersteam.alwin.jpa.Wallet;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.testdata.ActivityTestData;
import com.codersteam.alwin.testdata.OperatorTestData;
import com.codersteam.alwin.testdata.TagTestData;
import com.codersteam.alwin.testdata.TestDateUtils;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.issue.IssueTypeName.*;
import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;
import static com.codersteam.alwin.jpa.type.IssueState.*;
import static com.codersteam.alwin.jpa.type.OperatorNameType.ADMIN;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.ISSUE_ID_10;
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.NORMAL_PRIORITY;
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_TO_CLOSE_IDS;
import static com.codersteam.alwin.testdata.CompanyIssuesTestData.*;
import static com.codersteam.alwin.testdata.CompanyTestData.*;
import static com.codersteam.alwin.testdata.CustomerTestData.ID_1;
import static com.codersteam.alwin.testdata.CustomerTestData.ID_3;
import static com.codersteam.alwin.testdata.IssueTestData.*;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ID_2;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1;
import static com.codersteam.alwin.testdata.ManagedIssuesTestData.*;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_11;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.issue18;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.WalletTestData.*;
import static com.codersteam.alwin.testdata.assertion.IssueAssert.assertIssueListEquals;
import static com.codersteam.alwin.testdata.criteria.IssueForCompanySearchCriteriaTestData.*;
import static com.codersteam.alwin.testdata.criteria.IssueSearchCriteriaTestData.searchCriteriaByBalance;
import static com.codersteam.alwin.testdata.criteria.IssueSearchCriteriaTestData.searchCriteriaByStartDate;
import static com.codersteam.alwin.testdata.criteria.IssueSearchCriteriaTestData.*;
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.searchCriteriaByBalance;
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.searchCriteriaByStartDate;
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.*;
import static com.codersteam.alwin.testdata.criteria.IssuesSortTestData.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class IssueDaoReadTestIT extends ReadTestBase {

    @EJB
    private IssueDao issueDao;

    private final LinkedHashMap<IssueSortField, SortOrder> NO_SORT_CRITERIA = new LinkedHashMap<>();

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssue() {
        //given
        final Issue givenIssue = issue1();

        // when
        final List<Issue> issues = issueDao.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, givenIssue.getId(),
                issueForCompanySearchCriteria(0, 6));

        // then
        assertEquals(COMPANY_ID_1, givenIssue.getCustomer().getCompany().getId());
        assertIssueListEquals(issues, expectedCompanyIssuesExcludingFirst());
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithRestrictiveMaxResults() {
        //given
        final Issue givenIssue = issue1();

        // when
        final List<Issue> issues = issueDao.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, givenIssue.getId(),
                issueForCompanySearchCriteria(0, 1));

        // then
        assertEquals(COMPANY_ID_1, givenIssue.getCustomer().getCompany().getId());
        assertIssueListEquals(issues, expectedIssuesForCompanyExcludingGivenIssueWithRestrictiveMaxResults());
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithIssueType() {
        //given
        final Issue givenIssue = issue1();
        // when
        final List<Issue> issues = issueDao.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, givenIssue.getId(),
                issueForCompanySearchCriteriaByIssueType(0, 6));

        // then
        assertEquals(COMPANY_ID_1, givenIssue.getCustomer().getCompany().getId());
        assertIssueListEquals(issues, expectedIssuesForCompanyExcludingGivenIssueWithIssueType());
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithActivityType() {
        //given
        final Issue givenIssue = issue1();
        // when
        final List<Issue> issues = issueDao.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, givenIssue.getId(),
                issueForCompanySearchCriteriaByActivityType(0, 6));

        // then
        assertEquals(COMPANY_ID_1, givenIssue.getCustomer().getCompany().getId());
        assertIssueListEquals(issues, expectedIssuesForCompanyExcludingGivenIssueWithActivityType());
    }

    @Test
    public void shouldGetAllIssuesForCompanyExcludingGivenIssueWithActivityDate() {
        //given
        final Issue givenIssue = issue1();
        // when
        final List<Issue> issues = issueDao.findAllIssuesForCompanyExcludingGivenIssue(COMPANY_ID_1, givenIssue.getId(),
                issueForCompanySearchCriteriaByActivityDate(0, 6));

        // then
        assertEquals(COMPANY_ID_1, givenIssue.getCustomer().getCompany().getId());
        assertIssueListEquals(issues, expectedIssuesForCompanyExcludingGivenIssueWithActivityDate());
    }

    @Test
    public void shouldGetTotalNumberOfOperatorIssues() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final long result = issueDao.findOperatorIssuesCount(assigneeId, searchCriteria(0, 6));

        // then
        assertEquals(TOTAL_ASSIGNED_NOT_CLOSED_VALUES, result);
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<Issue> type = issueDao.getType();

        // then
        assertEquals(Issue.class, type);
    }

    @Test
    public void shouldNotAssignedActivitiesToOperator() {
        // when
        final boolean result = issueDao.assignNotAssignedActivityToOperator(ActivityTestData.ACTIVITY_ID_1, ID_1);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldReturnAllActiveIssues() {
        // when
        final List<Issue> allActiveIssues = issueDao.findAllActiveIssues();

        // then
        assertEquals(ACTIVE_ISSUE_IDS.size(), allActiveIssues.size());
        final Set<Long> activeIssuesIds = allActiveIssues.stream().map(Issue::getId).collect(Collectors.toSet());
        assertTrue(ACTIVE_ISSUE_IDS.containsAll(activeIssuesIds));
    }

    @Test
    public void shouldReturnActivePhoneDebtCollectionIssues() {
        // when
        final List<Issue> allActiveIssues = issueDao.findActiveIssues(PHONE_DEBT_COLLECTION);

        // then
        assertEquals(ACTIVE_PHONE_DEBT_COLLECTION_ISSUE_IDS.size(), allActiveIssues.size());
        final Set<Long> activeIssuesIds = allActiveIssues.stream().map(Issue::getId).collect(Collectors.toSet());
        assertTrue(ACTIVE_PHONE_DEBT_COLLECTION_ISSUE_IDS.containsAll(activeIssuesIds));
    }

    @Test
    public void shouldNotFindFutureIssuesForUserToWorkOn() {
        // given
        final Long userId = ID_1;
        final Date inspectionDate = TestDateUtils.parse("2017-09-30");

        // when
        final Optional<Activity> issueForUserToWorkOn = issueDao.findFutureActivityForUserToWorkOn(userId, PHONE_DEBT_COLLECTION, inspectionDate);

        // then
        assertFalse(issueForUserToWorkOn.isPresent());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithEmptySearchCriteria() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteria(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteria(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPage());

        // and
        assertIssueListEquals(secondPage, expectedAssignedNotClosedSecondPage());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithSearchCriteriaByStartDate() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByStartDate(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByStartDate(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPageWithSearchCriteriaByStartDate());

        // and
        assertTrue(secondPage.isEmpty());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithSearchCriteriaByExpirationDate() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByExpirationDate(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByExpirationDate(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPageWithSearchCriteriaByExpirationDate());

        // and
        assertTrue(secondPage.isEmpty());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithSearchCriteriaByBalance() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByBalance(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByBalance(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPageWithSearchCriteriaByBalance());

        // and
        assertTrue(secondPage.isEmpty());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithSearchCriteriaByActivityDate() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByActivityDate(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByActivityDate(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPage());

        // and
        assertIssueListEquals(secondPage, expectedAssignedNotClosedSecondPage());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithSearchCriteriaByPlannedDate() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByPlannedDate(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByPlannedDate(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPageWithSearchCriteriaByPlannedDate());

        // and
        assertTrue(secondPage.isEmpty());
    }

    @Test
    public void shouldNotGetPaginatedAllOperatorIssuesWithSearchCriteriaByPlannedDateAndBalance() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByPlannedDateAndBalance(0, 6));

        // then
        assertTrue(firstPage.isEmpty());
    }

    @Test
    public void shouldGetPaginatedAllOperatorIssuesWithSearchCriteriaByPlannedDateAndExpirationDate() {
        // given
        final long assigneeId = OperatorTestData.testOperator1().getId();

        // when
        final List<Issue> firstPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByPlannedDateAndExpirationDate(0, 6));
        final List<Issue> secondPage = issueDao.findOperatorIssues(assigneeId, searchCriteriaByPlannedDateAndExpirationDate(6, 6));

        // then
        assertIssueListEquals(firstPage, expectedAssignedNotClosedFirstPageWithSearchCriteriaByPlannedDateAndExpirationDate());

        // and
        assertTrue(secondPage.isEmpty());
    }

    @Test
    public void shouldFindIssuesToClose() {
        // given
        final Date inspectionDate = TestDateUtils.parse("2024-01-15");

        // when
        final List<Issue> issuesToClose = issueDao.findIssuesToClose(inspectionDate);

        // then
        assertEquals(ISSUE_TO_CLOSE_IDS.size(), issuesToClose.size());
        final Set<Long> issuesToCloseIds = issuesToClose.stream().map(Issue::getId).collect(Collectors.toSet());
        assertTrue(ISSUE_TO_CLOSE_IDS.containsAll(issuesToCloseIds));
    }

    @Test
    public void shouldNotFindIssuesToClose() {
        // given
        final Date inspectionDate = TestDateUtils.parse("2016-01-15");

        // when
        final List<Issue> issuesToClose = issueDao.findIssuesToClose(inspectionDate);

        // then
        assertTrue(issuesToClose.isEmpty());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByBalance() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByBalance(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByBalance());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByTags() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTag(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByTags());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByPartOfCompanyName() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPartOfCompanyName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByPartOfCompanyName());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByPartOfCompanyName() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPartOfCompanyName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByPartOfCompanyName());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByInvoiceContractNo() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByInvoiceContractNo(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByInvoiceContractNo());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByInvoiceContractNo() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByInvoiceContractNo(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByInvoiceContractNo());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByPartOfInvoiceContractNo() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPartOfInvoiceContractNo(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByPartOfInvoiceContractNo());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByPartOfInvoiceContractNo() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPartOfInvoiceContractNo(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByPartOfInvoiceContractNo());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByPersonPesel() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPersonPesel(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByPersonPesel());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByPersonPesel() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPersonPesel(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByPersonPesel());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByLoweredPersonName() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByLoweredPersonName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByLoweredPersonName());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByLoweredPersonName() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByLoweredPersonName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByLoweredPersonName());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByRevertedLoweredPersonName() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByRevertedLoweredPersonName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByRevertedLoweredPersonName());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByRevertedLoweredPersonName() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByRevertedLoweredPersonName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByRevertedLoweredPersonName());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactName() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactName(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAllIssuesFilteredByContactName());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactRevertedName() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactRevertedName(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAllIssuesFilteredByContactRevertedName());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactDocumentEmail() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactDocumentEmail(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAllIssuesFilteredByDocumentEmail());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactOfficeEmail() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactOfficeEmail(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAllIssuesFilteredByOfficeEmail());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactEmail() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactEmail(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAllIssuesFilteredByContactEmail());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactPhone() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactPhone(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAllIssuesFilteredByContactPhone());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactName() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByContactName());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactRevertedName() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactRevertedName(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByContactRevertedName());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactDocumentEmail() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactDocumentEmail(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByContactDocumentEmail());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactOfficeEmail() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactOfficeEmail(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByContactOfficeEmail());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactEmail() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactEmail(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByContactEmail());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactPhone() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactPhone(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByContactPhone());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByCompanyNip() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByCompanyNip(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByCompanyNip());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByCompanyNip() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByCompanyNip(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByCompanyNip());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByCompanyRegon() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByCompanyRegon(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesFilteredByCompanyRegon());
    }

    @Test
    public void shouldFindPaginatedAllIssuesFilteredByCompanyRegon() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByCompanyRegon(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedActiveIssuesFilteredByCompanyRegon());
    }

    @Test
    public void shouldFindOnlyNormalPriority() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPriority(NORMAL_PRIORITY), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertThat(result).allMatch(expectedPriority(NORMAL_PRIORITY));
    }

    @Test
    public void shouldFindOnlyHighPriority() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByPriority(HIGH_PRIORITY), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertThat(result).allMatch(expectedPriority(HIGH_PRIORITY));
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByBalanceCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByBalance(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_BALANCE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByExtCompanyIds() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByExtCompanyIds(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByExtCompanyIds());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesSortedByBalanceStartDescending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByBalanceStartDescending());
        System.out.println(result.size());
        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByBalanceStartDescending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByCurrentBalanceDescending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByCurrentBalanceDescending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByCurrentBalanceDescending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByCustomerNameAscending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByCustomerNameAscending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByCustomerNameAscending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByExpirationDateAscending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByExpirationDateAscending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByExpirationDateAscending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByPaymentsAscending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByPaymentsAscending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByPaymentsAscending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByRpbAscending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByRpbAscending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByRpbAscending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByStartDateAscending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByStartDateAscending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByStartDateAscending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByStateAscending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByStateAscending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByStateAscending());
    }

    @Test
    public void shouldFindPaginatedMangedIssuesSortedByTypeNameDescending() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, emptySearchCriteria(), CLOSED_ISSUE_STATES, OPEN_STATES, sortByTypeNameDescending());

        // then
        assertIssueListEquals(result, expectedMangedIssuesSortedByTypeNameDescending());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByExtCompanyIdsCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByExtCompanyIds(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_EXT_COMPANY_IDS_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByExtCompanyIds() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByExtCompanyIds(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByExtCompanyIds());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByExtCompanyIdsCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByExtCompanyIds(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_EXT_COMPANY_IDS_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByBalance() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByBalance(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByBalance());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByBalanceCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByBalance(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_BALANCE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByBalance() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByBalance(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByBalance());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByBalanceCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByBalance(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_BALANCE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByExtCompanyIds() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByExtCompanyIds(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByExtCompanyIds());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByExtCompanyIdsCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByExtCompanyIds(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_BY_EXT_COMPANY_IDS_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByTotalCurrentBalancePLNMin() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTotalCurrentBalancePLNMin(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByTotalCurrentBalancePLNMin());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByTotalCurrentBalancePLNMinCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByTotalCurrentBalancePLNMin(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_BALANCE_MIN_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTotalCurrentBalancePLNMin() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTotalCurrentBalancePLNMin(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByTotalCurrentBalancePLNMin());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTotalCurrentBalancePLNMinCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByTotalCurrentBalancePLNMin(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_BALANCE_MIN_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByTotalCurrentBalancePLNMin() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTotalCurrentBalancePLNMin(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByTotalCurrentBalancePLNMin());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByTotalCurrentBalancePLNMinCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByTotalCurrentBalancePLNMin(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_BALANCE_MIN_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByTotalCurrentBalancePLNMax() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTotalCurrentBalancePLNMax(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByTotalCurrentBalancePLNMax());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByTotalCurrentBalancePLNMaxCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByTotalCurrentBalancePLNMax(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_BALANCE_MAX_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTotalCurrentBalancePLNMax() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTotalCurrentBalancePLNMax(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByTotalCurrentBalancePLNMax());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByTotalCurrentBalancePLNMaxCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByTotalCurrentBalancePLNMax(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_BALANCE_MAX_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByTotalCurrentBalancePLNMax() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByTotalCurrentBalancePLNMax(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByTotalCurrentBalancePLNMax());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByTotalCurrentBalancePLNMaxCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByTotalCurrentBalancePLNMax(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_BALANCE_MAX_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStartDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartDate(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByStartDate());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStartDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartDate(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByStartDate());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartDate() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByStartDate());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartDateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStartOfStartDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartOfStartDate(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByStartOfStartDate());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStartOfStartDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartOfStartDate(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_START_OF_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartOfStartDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartOfStartDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByStartOfStartDate());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartOfStartDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartOfStartDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_START_OF_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartOfStartDate() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartOfStartDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByStartOfStartDate());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartOfStartDateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartOfStartDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_START_OF_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByEndOfStartDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByEndOfStartDate(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByEndOfStartDate());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByEndOfStartDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByEndOfStartDate(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_END_OF_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByEndOfStartDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByEndOfStartDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByEndOfStartDate());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByEndOfStartDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByEndOfStartDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_END_OF_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByEndOfStartDate() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByEndOfStartDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByEndOfStartDate());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByEndOfStartDateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByEndOfStartDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_END_OF_START_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByContactDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactDate(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByContactDate());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByContactDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByContactDate(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByContactDate());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByContactDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByContactDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactDate() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByContactDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByContactDate());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByContactDateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByContactDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStartOfContactDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartOfContactDate(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByStartOfContactDate());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStartOfContactDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartOfContactDate(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_START_OF_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartOfContactDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartOfContactDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByStartOfContactDate());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStartOfContactDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartOfContactDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_START_OF_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartOfContactDate() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByStartOfContactDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByStartOfContactDate());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStartOfContactDateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByStartOfContactDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_START_OF_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByEndOfContactDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByEndOfContactDate(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByEndOfContactDate());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByEndOfContactDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByEndOfContactDate(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_END_OF_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByEndOfContactDate() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByEndOfContactDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByEndOfContactDate());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByEndOfContactDateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByEndOfContactDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_END_OF_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByEndOfContactDate() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByEndOfContactDate(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByEndOfContactDate());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByEndOfContactDateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByEndOfContactDate(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_END_OF_CONTACT_DATE_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByOperator() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByOperator(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedActiveIssuesByOperator());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByOperatorCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByOperator(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_ACTIVE_ISSUES_BY_OPERATOR_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByOperator() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByOperator(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesByOperator());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByOperatorCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByOperator(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_BY_OPERATOR_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByOperator() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByOperator(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesByOperator());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByOperatorCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByOperator(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_BY_OPERATOR_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByNotAssignedOperator() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByNotAssigned(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedUnassignedManagedActiveIssues());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByNotAssignedOperatorCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByNotAssigned(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_UNASSIGNED_MANAGED_ACTIVE_ISSUES_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByNotAssignedOperator() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByNotAssigned(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedUnassignedManagedIssues());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByNotAssignedOperatorCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByNotAssigned(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_UNASSIGNED_MANAGED_ISSUES_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByNotAssignedOperator() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByNotAssigned(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedUnassignedAdminIssues());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByNotAssignedOperatorCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByNotAssigned(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_UNASSIGNED_ADMIN_ISSUES_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByState() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStates(), CLOSED_ISSUE_STATES, OPEN_STATES, NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedNewAndInProgressIssues());
    }

    @Test
    public void shouldFindPaginatedManagedActiveIssuesFilteredByStateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByIssueOpenStates(), CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(EXPECTED_MANAGED_NEW_AND_IN_PROGRESS_ISSUES_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByState() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStates(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedManagedIssuesInAllStates());
    }

    @Test
    public void shouldFindPaginatedManagedIssuesFilteredByStateCount() {
        // given
        final String operatorType = PHONE_DEBT_COLLECTOR_MANAGER.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByIssueOpenStates(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_MANAGED_ISSUES_IN_ALL_STATES_COUNT, result);
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByState() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<Issue> result = issueDao.findAllFilteredIssues(operatorType, searchCriteriaByIssueOpenStates(), NONE, emptyList(), NO_SORT_CRITERIA);

        // then
        assertIssueListEquals(result, expectedAdminIssuesInAllStates());
    }

    @Test
    public void shouldFindPaginatedIssuesForAdminFilteredByStateCount() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final Long result = issueDao.findAllFilteredIssuesCount(operatorType, searchCriteriaByIssueOpenStates(), NONE, emptyList());

        // then
        assertEquals(EXPECTED_ADMIN_ISSUES_IN_ALL_STATES_COUNT, result);
    }

    @Test
    public void shouldFindIssuesCreatedBySystem() {
        // given
        final Date fromDate = parse("2023-07-09 00:00:00.000000");
        final Date toDate = parse("2024-07-09 00:00:00.000000");

        // when
        final List<Issue> issues = issueDao.findIssuesCreatedBySystem(PHONE_DEBT_COLLECTION_1, IN_PROGRESS, fromDate, toDate);

        // then
        assertIssueListEquals(issues, singletonList(issue18()));
    }

    @Test
    public void shouldFindCompanyActiveIssueId() {
        // when
        final Optional<Long> companyActiveIssueId = issueDao.findCompanyActiveIssueId(EXT_COMPANY_ID_7);

        // then
        assertTrue(companyActiveIssueId.isPresent());
        assertEquals(ISSUE_ID_4, companyActiveIssueId.get());
    }

    @Test
    public void shouldNotFindActiveIssueIdForCompanyWithoutOne() {
        // when
        final Optional<Long> companyActiveIssueId = issueDao.findCompanyActiveIssueId(EXT_COMPANY_ID_3);

        // then
        assertFalse(companyActiveIssueId.isPresent());
    }

    @Test
    public void shouldNotFindActiveIssueIdForNotExistingCompany() {
        // when
        final Optional<Long> companyActiveIssueId = issueDao.findCompanyActiveIssueId(NON_EXISTING_COMPANY_ID);

        // then
        assertFalse(companyActiveIssueId.isPresent());
    }

    @Test
    public void shouldFindAllCompaniesWithActiveIssue() {
        // when
        final List<Company> companies = issueDao.findAllCompaniesWithActiveIssue();

        // then
        assertEquals(13, companies.size());
        final List<Long> companiesIds = companies.stream().map(Company::getId).collect(toList());
        assertThat(companiesIds).contains(COMPANY_ID_14, COMPANY_ID_22, COMPANY_ID_5, COMPANY_ID_8, COMPANY_ID_6, COMPANY_ID_12, COMPANY_ID_9, COMPANY_ID_1,
                COMPANY_ID_7, COMPANY_ID_23, COMPANY_ID_24, COMPANY_ID_26, COMPANY_ID_2);
    }

    @Test
    public void shouldFindWalletsByIssueType() {
        // when
        final Optional<IssueWallet> wallet = issueDao.findWalletsByIssueType(PHONE_DEBT_COLLECTION_1, Segment.A, OPEN_ISSUE_STATES);

        // then
        assertTrue(wallet.isPresent());
        assertThat(wallet.get()).isEqualToComparingFieldByFieldRecursively(issueWallet1());
    }

    @Test
    public void shouldNotFindWalletByIssueState() {
        // when
        final Wallet wallet = issueDao.findWalletByIssueState(WAITING_FOR_TERMINATION);

        // then
        assertThat(wallet).isEqualToComparingFieldByFieldRecursively(emptyWallet());
    }

    @Test
    public void shouldFindWalletByIssueState() {
        // when
        final Wallet wallet = issueDao.findWalletByIssueState(NEW);

        // then
        assertThat(wallet).isEqualToComparingFieldByFieldRecursively(walletForIssueStateNew());
    }

    @Test
    public void shouldFindCurrentIssueQueueCountByIssueState() {
        // given
        final Date forDate = TestDateUtils.parse("2017-08-21");

        // when
        final Long currentIssueQueueCount = issueDao.findCurrentIssueQueueCountByIssueState(NEW, forDate);

        // then
        assertEquals(5L, currentIssueQueueCount.longValue());
    }

    @Test
    public void shouldFindTagWallets() {
        // when
        final List<TagWallet> tagWallets = issueDao.findTagWallets();

        // then
        assertThat(tagWallets)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(tagWallet1(), tagWallet2());
    }

    @Test
    public void shouldNotFindWalletsByIssueType() {
        // when
        final Optional<IssueWallet> wallet = issueDao.findWalletsByIssueType(PHONE_DEBT_COLLECTION_2, Segment.B, OPEN_ISSUE_STATES);

        // then
        assertFalse(wallet.isPresent());
    }

    @Test
    public void shouldFindOverdueIssueIds() {
        // given
        final Date date = TestDateUtils.parse("2017-08-15");

        // when
        final List<Long> issueIds = issueDao.findOverdueIssueIdsPerDay(date);

        // then
        assertEquals(new HashSet<>(asList(1L, 2L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L)), new HashSet<>(issueIds));
    }

    @Test
    public void shouldFindCurrentIssueQueueCountForTag() {
        // given
        final Date forDate = TestDateUtils.parse("2017-08-21");

        // when
        final Long currentIssueQueueCount = issueDao.findCurrentIssueQueueCountByTag(TagTestData.ID_1, forDate);

        // then
        assertEquals(2L, currentIssueQueueCount.longValue());
    }


    @Test
    public void shouldFindCurrentIssueQueueCountForPhoneDebtCollection1AndSegmentA() {
        // given
        final Date forDate = TestDateUtils.parse("2017-08-21");

        // when
        final Long currentIssueQueueCount = issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.A, forDate, OPEN_ISSUE_STATES);

        // then
        assertEquals(5L, currentIssueQueueCount.longValue());
    }

    @Test
    public void shouldFindCurrentIssueQueueCountForPhoneDebtCollection1AndSegmentB() {
        // given
        final Date forDate = TestDateUtils.parse("2017-08-21");

        // when
        final Long currentIssueQueueCount = issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(PHONE_DEBT_COLLECTION_1, Segment.B, forDate, OPEN_ISSUE_STATES);

        // then
        assertEquals(0L, currentIssueQueueCount.longValue());
    }

    @Test
    public void shouldContainsTotalBalanceFields() {
        // when
        final Issue issue = issueDao.get(ISSUE_ID_1).orElseThrow(() -> new RuntimeException("Issue not found"));

        // then
        assertThat(issue.getTotalBalanceStartPLN()).isEqualTo(TOTAL_BALANCE_START_PLN_1);
        assertThat(issue.getTotalCurrentBalancePLN()).isEqualTo(TOTAL_CURRENT_BALANCE_PLN_1);
        assertThat(issue.getTotalPaymentsPLN()).isEqualTo(TOTAL_PAYMENTS_PLN_1);
    }

    @Test
    public void shouldReturnIssueIdsWithUnpaidDeclarations() {
        // given
        final Date declaredPaymentDateStart = TestDateUtils.parse("2017-07-01");
        final Date declaredPaymentDateEnd = TestDateUtils.parse("2017-07-03");

        // when
        final List<Long> issueIds = issueDao.findIssueIdsWithUnpaidDeclarations(declaredPaymentDateStart, declaredPaymentDateEnd);

        //then
        assertEquals(3, issueIds.size());
        assertTrue(issueIds.contains(ISSUE_ID_1));
        assertTrue(issueIds.contains(ISSUE_ID_10));
        assertTrue(issueIds.contains(ISSUE_ID_11));
    }

    @Test
    public void shouldFindOperatorTypesForIssues() {
        // when
        final List<IssueType> issueTypes = issueDao.findOperatorTypesForIssues(asList(ISSUE_ID_1, ISSUE_ID_3));

        // then
        assertEquals(2, issueTypes.size());
        final List<Long> ids = issueTypes.stream().map(IssueType::getId).collect(Collectors.toList());
        assertTrue(ids.containsAll(asList(ISSUE_TYPE_ID_1, ID_2)));
    }

    @Test
    public void shouldFindOperatorTypesForIssuesFilteredByPartOfCompanyName() {
        // given
        final String operatorType = ADMIN.name();

        // when
        final List<IssueType> issueTypes = issueDao.findOperatorTypesForIssues(operatorType, searchCriteriaByPartOfCompanyName(),
                CLOSED_ISSUE_STATES, OPEN_STATES);

        // then
        assertEquals(3, issueTypes.size());
        final List<Long> ids = issueTypes.stream().map(IssueType::getId).collect(Collectors.toList());
        assertTrue(ids.containsAll(asList(ISSUE_TYPE_ID_1, ID_2, ID_3)));
    }
}

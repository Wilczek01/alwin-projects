package com.codersteam.alwin.integration;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.issue.IssueAssignnmentService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.issue.IssueTerminationService;
import com.codersteam.alwin.core.service.impl.issue.CloseExpiredIssuesService;
import com.codersteam.alwin.core.service.impl.issue.CreateIssueService;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.testdata.OperatorTestData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.integration.mock.CurrencyExchangeRateServiceMock.EUR_EXCHANGE_RATE_BY_DATE;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.SIMPLE_INVOICES_BY_DUE_DATE;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.REMARK;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.COMMENT;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*;
import static com.codersteam.alwin.testdata.CompanyTestData.*;
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.expectedIssueTerminationRequestDto;
import static com.codersteam.alwin.testdata.IssueTestData.*;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_11;
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_13;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_2;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_3;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static com.codersteam.alwin.testdata.assertion.IssueAssert.assertIssueDtoEquals;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.criteria.IssuesSearchCriteriaTestData.searchCriteriaByIssueOpenStates;
import static java.util.Collections.emptyList;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class IssueServiceImplTestIT extends CoreTestBase {

    private final LinkedHashMap<IssueSortField, SortOrder> NO_SORT_CRITERIA = new LinkedHashMap<>();
    @Inject
    private IssueService issueService;
    @Inject
    private IssueAssignnmentService issueAssignnmentService;
    @Inject
    private CreateIssueService createIssueService;
    @Inject
    private ActivityService activityService;
    @Inject
    private CloseExpiredIssuesService closeExpiredIssuesService;
    @Inject
    private IssueTerminationService issueTerminationService;
    @Inject
    private IssueDao issueDao;

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json"})
    public void shouldFindIssue() {
        // given
        CURRENT_DATE = "2018-08-15";

        // when
        final IssueDto issue = issueService.findIssue(ISSUE_ID_2);

        // then
        assertIssueDtoEquals(issue, issueDto2());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-type-configuration.json"})
    public void shouldNotCreateIssuesWhenNoDueDocumentsFound() {
        // given
        CURRENT_DATE = "1970-01-06";

        // and
        SIMPLE_INVOICES_BY_DUE_DATE.put("1970-01-01", emptyList());

        // and
        final Page<IssueDto> allIssuesBefore = issueService.findAllIssues(PHONE_DEBT_COLLECTOR_MANAGER, searchCriteriaByIssueOpenStates(), NO_SORT_CRITERIA);

        // when
        createIssueService.createIssues();

        // then amount of issues is the same as before create issue (no new issue created)
        final Page<IssueDto> allIssues = issueService.findAllIssues(PHONE_DEBT_COLLECTOR_MANAGER, searchCriteriaByIssueOpenStates(), NO_SORT_CRITERIA);
        assertEquals(allIssuesBefore.getTotalValues(), allIssues.getTotalValues());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-type-configuration.json"})
    public void shouldCloseIssues() {
        // given
        CURRENT_DATE = "2018-10-06";

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_23, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_24, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_26, emptyList());

        // when
        closeExpiredIssuesService.closeExpiredIssuesAndCreateChildIssuesIfNeeded();

        // then
        final List<IssueDto> allActiveIssues = issueService.findAllActiveIssues();
        final Set<Long> allActiveIssuesIds = allActiveIssues.stream().map(IssueDto::getId).collect(Collectors.toSet());
        assertEquals(21, allActiveIssues.size());
        assertFalse(allActiveIssuesIds.contains(ISSUE_ID_1));
        assertFalse(allActiveIssuesIds.contains(ISSUE_ID_2));
        assertOneDirectDebtCollectionIssueExistsWithOperator(allActiveIssues, OperatorTestData.testOperatorUserDto1());
    }

    /**
     * Sprawdza, czy na liście zleceń występuje jedno zlecenie windykacji bezpośredniej z przypisanym operatorem
     */
    private void assertOneDirectDebtCollectionIssueExistsWithOperator(final List<IssueDto> allActiveIssues, final OperatorUserDto assignedOperator) {
        final List<IssueDto> directDebtCollectionIssues = allActiveIssues.stream()
                .filter(issue -> issue.getIssueType().getName().equals(IssueTypeName.DIRECT_DEBT_COLLECTION.name()))
                .collect(Collectors.toList());
        assertEquals(1, directDebtCollectionIssues.size());
        assertThat(directDebtCollectionIssues.get(0).getAssignee()).isEqualToComparingFieldByFieldRecursively(assignedOperator);
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
    public void shouldTerminateIssue() {
        // given
        CURRENT_DATE = "2018-08-15";
        final Date terminationDate = parse(CURRENT_DATE);
        final List<ActivityDto> allIssueActivitiesBefore = activityService.findAllIssueActivities(ISSUE_ID_1);

        // when
        issueService.terminateIssue(ISSUE_ID_1, OPERATOR_ID_2, TERMINATION_CAUSE, EXCLUDE_FROM_STATS, EXCLUSION_CAUSE);

        // then
        final IssueDto issue = issueService.findIssue(ISSUE_ID_1);
        assertNotNull(issue);
        assertIssueDtoEquals(issue, terminatedIssueDto(terminationDate));
        assertCommentActivity(allIssueActivitiesBefore);
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json"})
    public void shouldCreateIssueTerminationRequest() {
        // given
        CURRENT_DATE = "2018-08-15";
        final List<ActivityDto> allIssueActivitiesBefore = activityService.findAllIssueActivities(ISSUE_ID_1);

        // when
        issueService.terminateIssue(ISSUE_ID_1, OPERATOR_ID_3, TERMINATION_CAUSE, EXCLUDE_FROM_STATS, EXCLUSION_CAUSE);

        // then
        final IssueDto issue = issueService.findIssue(ISSUE_ID_1);
        assertNotNull(issue);
        assertIssueDtoEquals(issue, waitingForTerminationIssueDto());

        final Date terminationDate = parse(CURRENT_DATE);
        final IssueTerminationRequestDto requestAfter = issueTerminationService.findTerminationRequestByIssueId(ISSUE_ID_1);
        assertNotNull(requestAfter);
        AssertionsForClassTypes.assertThat(requestAfter)
                .usingComparatorForFields(SKIP_COMPARATOR, "requestOperator")
                .isEqualToComparingFieldByField(expectedIssueTerminationRequestDto(terminationDate));
        assertEquals(OPERATOR_ID_3, requestAfter.getRequestOperator().getId());
        assertCommentActivity(allIssueActivitiesBefore);
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice-my-work.json", "test-activity.json",
            "test-issue-type-configuration.json"})
    public void shouldFindIssueToWorkOnWithUpdatedBalanceForPhoneDebtCollector() throws Exception {
        // given
        CURRENT_DATE = "2018-08-15";

        // and issue balance update date before current date
        setIssueBalanceUpdateDate(ISSUE_ID_11, "2018-08-14");

        // and
        setupAidaMocksForIssue11BalanceUpdateWithNegativeBalance();

        // when
        final Optional<IssueDto> issueToWorkOn = issueAssignnmentService.findWorkForUser(TEST_USER_ID_2);

        // then
        assertTrue(issueToWorkOn.isPresent());
        final IssueDto issue = issueToWorkOn.get();
        assertEquals(ISSUE_ID_11, issue.getId());

        // balance update date is updated
        assertEquals(parse(CURRENT_DATE), issue.getBalanceUpdateDate());
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice-my-work.json", "test-activity.json",
            "test-issue-type-configuration.json"})
    public void shouldFindIssueToWorkOnWithUpdatedBalanceForPhoneDebtCollectorAfterClosingIssue() throws Exception {
        // given
        CURRENT_DATE = "2018-08-15";

        // and issue balance update date before current date
        setIssueBalanceUpdateDate(ISSUE_ID_11, "2018-08-14");

        // and
        setupAidaMocksForIssue11BalanceUpdateWithAllPaidBalance();

        // when
        final Optional<IssueDto> issueToWorkOn = issueAssignnmentService.findWorkForUser(TEST_USER_ID_2);

        // then
        assertTrue(issueToWorkOn.isPresent());
        final IssueDto issue = issueToWorkOn.get();
        assertEquals(ISSUE_ID_13, issue.getId());

        // and issue 11 after recalculation is closed
        assertThatIssueAndActivitiesAreClosed(ISSUE_ID_11);

        // balance update date is after current date (as set in test data [2023-07-10 00:00:00.0])
        assertTrue(issue.getBalanceUpdateDate().after(parse(CURRENT_DATE)));
    }

    @SuppressWarnings("SameParameterValue")
    private void assertThatIssueAndActivitiesAreClosed(final Long issueId) {
        // issue is closed
        final IssueDto issue = issueService.findIssue(issueId);
        assertEquals(IssueStateDto.CANCELED, issue.getIssueState());

        // issue activities are closed
        final List<ActivityDto> issueActivities = activityService.findAllIssueActivities(issueId);
        assertFalse(issueActivities.isEmpty());
        issueActivities.forEach(activity -> assertEquals(ActivityStateDto.CANCELED, activity.getState()));
    }

    private void setupAidaMocksForIssue11BalanceUpdateWithNegativeBalance() {
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_14, dueInvoicesWithCorrections());
        setupAidaMocks();
    }

    private void setupAidaMocksForIssue11BalanceUpdateWithAllPaidBalance() {
        final List<AidaInvoiceWithCorrectionsDto> invoices = dueInvoicesWithCorrections();
        invoices.forEach(invoice -> invoice.setBalanceOnDocument(ZERO));
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_14, invoices);
        setupAidaMocks();
    }

    private void setupAidaMocks() {
        EUR_EXCHANGE_RATE_BY_DATE.put(parse(CURRENT_DATE), new BigDecimal("4.12"));

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_14, emptyList());
    }

    @SuppressWarnings("SameParameterValue")
    private void setIssueBalanceUpdateDate(final long issueId, final String issueBalanceUpdateDate) throws Exception {
        final Optional<Issue> issue = issueDao.get(issueId);
        assertTrue(issue.isPresent());
        final Issue issue11 = issue.get();
        issue11.setBalanceUpdateDate(parse(issueBalanceUpdateDate));
        issueDao.update(issue11);
        commitTrx();
    }

    private void assertCommentActivity(final List<ActivityDto> allIssueActivitiesBefore) {
        final List<ActivityDto> allIssueActivitiesAfter = activityService.findAllIssueActivities(ISSUE_ID_1);
        assertEquals(1, allIssueActivitiesAfter.size() - allIssueActivitiesBefore.size());
        allIssueActivitiesAfter.removeAll(allIssueActivitiesBefore);
        assertEquals(1, allIssueActivitiesAfter.size());
        final ActivityDto commentActivityDto = allIssueActivitiesAfter.get(0);
        assertEquals(COMMENT.name(), commentActivityDto.getActivityType().getKey());
        assertEquals(1, commentActivityDto.getActivityDetails().size());
        final ActivityDetailDto activityDetailDto = commentActivityDto.getActivityDetails().get(0);
        assertEquals(REMARK.name(), activityDetailDto.getDetailProperty().getKey());
        assertEquals(TERMINATION_CAUSE, activityDetailDto.getValue());
    }
}

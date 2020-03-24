package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.service.issue.IssueCreatorService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.db.dao.ActivityDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.activity.Activity;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.INVOICES_FOR_BALANCE_CALCULATION_START;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENT;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1;
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_2;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_2;
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.customerInvolvementDto1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueInvoicesWithCorrections;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
public class IssueCreatorServiceImplTestIT extends CoreTestBase {

    @EJB
    private IssueCreatorService issueCreatorService;

    @EJB
    private IssueService issueService;

    @Inject
    private ActivityDao activityDao;

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-activity.json", "test-default-activity.json", "test-issue-type-configuration.json"})
    public void shouldCreateIssueManuallyWithAssignment() {
        // given
        final Date expirationDate = parse("2023-10-12");

        // and
        CURRENT_DATE = "2017-09-01";

        // and
        final List<IssueDto> allActiveIssuesBefore = issueService.findAllActiveIssues();

        // and
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_1, A);

        // and
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_1, INVOLVEMENT_2);

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, dueInvoicesWithCorrections());

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, singletonList(customerInvolvementDto1()));

        // when
        issueCreatorService.createIssueManually(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, expirationDate, OPERATOR_ID_2);

        // then: there is new issue
        final List<IssueDto> allActiveIssuesAfter = issueService.findAllActiveIssues();
        assertEquals(allActiveIssuesBefore.size() + 1, allActiveIssuesAfter.size());

        // and: new issue is properly set
        final IssueDto newIssue = getNewIssue(allActiveIssuesBefore, allActiveIssuesAfter);
        assertNewIssue(newIssue, expirationDate);

        assertNewIssueBalances(newIssue);

        // and: activities created and assigned
        assertNewIssueActivities(newIssue.getId());
    }

    private void assertNewIssueBalances(final IssueDto issue) {
        assertEquals(new BigDecimal("10.00"), issue.getRpbPLN());
        assertEquals(new BigDecimal("-99334.35"), issue.getBalanceStartPLN());
        assertEquals(new BigDecimal("-99334.35"), issue.getCurrentBalancePLN());
        assertEquals(new BigDecimal("0.00"), issue.getPaymentsPLN());
        assertEquals(new BigDecimal("0.00"), issue.getRpbEUR());
        assertEquals(new BigDecimal("0.00"), issue.getBalanceStartEUR());
        assertEquals(new BigDecimal("0.00"), issue.getCurrentBalanceEUR());
        assertEquals(new BigDecimal("0.00"), issue.getPaymentsEUR());
    }

    private void assertNewIssueActivities(final Long issueId) {
        final List<Activity> activities = activityDao.findAllIssueActivities(issueId);
        assertEquals(4, activities.size());
        activities.forEach(activity -> assertEquals(OPERATOR_ID_2, activity.getOperator().getId()));
    }

    private void assertNewIssue(final IssueDto issue, final Date expirationDate) {
        assertNotNull(issue);
        assertEquals(EXT_COMPANY_ID_1, issue.getCustomer().getCompany().getExtCompanyId());
        assertEquals(expirationDate, issue.getExpirationDate());
        assertEquals(OPERATOR_ID_2, issue.getAssignee().getId());
        assertEquals(ISSUE_TYPE_ID_1, issue.getIssueType().getId());
    }

    private IssueDto getNewIssue(final List<IssueDto> allActiveIssuesBefore, final List<IssueDto> allActiveIssuesAfter) {
        final List<Long> beforeIds = allActiveIssuesBefore.stream().map(IssueDto::getId).collect(Collectors.toList());
        final Map<Long, IssueDto> afterIdsToIssue = allActiveIssuesAfter.stream().collect(Collectors.toMap(IssueDto::getId, Function.identity()));
        final Set<Long> afterIds = afterIdsToIssue.keySet();
        afterIds.removeAll(beforeIds);
        assertEquals(1, afterIds.size());
        return afterIdsToIssue.get(afterIds.iterator().next());
    }
}

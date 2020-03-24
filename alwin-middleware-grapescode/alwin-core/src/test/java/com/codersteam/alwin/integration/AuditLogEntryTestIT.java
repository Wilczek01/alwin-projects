package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.api.model.audit.AuditLogEntryDto;
import com.codersteam.alwin.core.api.service.audit.AuditService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.service.impl.issue.CreateIssueService;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.issue.Issue;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.List;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.*;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENT;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.integration.mock.PersonServiceMock.PERSONS_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto;
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.customerInvolvementDto1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.TEST_EXT_PERSON_1;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.TEST_EXT_PERSON_2;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

@UsingDataSet({"test-create-issues.json", "test-issue-type-configuration.json"})
public class AuditLogEntryTestIT extends CoreTestBase {

    @EJB
    private IssueService issueService;

    @Inject
    private CreateIssueService createIssueService;

    @EJB
    private AuditService auditService;

    @Inject
    private IssueDao issueDao;

    @Test
    public void shouldCreateIssuesAndGetAuditLog() throws Exception {
        // given
        CURRENT_DATE = "2017-07-11";

        // and there are due documents on DPD=5
        SIMPLE_INVOICES_BY_DUE_DATE.put("2017-07-10", dueSimpleInvoices());

        //and invoices balances
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_1, dueInvoice1());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_2, dueInvoice2());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_3, dueInvoice3());

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, dueInvoicesWithCorrections());

        // and there are invoices for customer 1
        OUTSTANDING_INVOICES.put(EXT_COMPANY_ID_1, dueInvoices());

        // and company has persons
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, asList(TEST_EXT_PERSON_1, TEST_EXT_PERSON_2));

        // and company data
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto(EXT_COMPANY_ID_1));

        // and company involvement
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_1, INVOLVEMENT_1);

        // and company segment
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_1, A);

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, singletonList(customerInvolvementDto1()));

        // when create issues
        createIssueService.createIssues();

        // then new issue created
        final List<Issue> issues = issueDao.findAllActiveIssues();
        final Issue newIssue = issues.get(0);
        issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(newIssue.getId());
        commitTrx();

        // then should find audit log entries
        final List<AuditLogEntryDto> issueChanges = auditService.findIssueChanges(newIssue.getId());
        assertEquals(issueChanges.size(), 9);
    }
}

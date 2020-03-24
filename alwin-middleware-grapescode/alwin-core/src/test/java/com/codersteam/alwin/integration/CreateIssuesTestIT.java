package com.codersteam.alwin.integration;

import com.codersteam.aida.core.api.model.AidaPersonDto;
import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.service.impl.issue.CreateIssueService;
import com.codersteam.alwin.core.util.DateUtils;
import com.codersteam.alwin.core.util.InvoiceUtils;
import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.IssueInvoiceDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.Person;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.codersteam.aida.core.api.model.CompanySegment.A;
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_2;
import static com.codersteam.alwin.integration.mock.CompanyServiceMock.COMPANIES_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.DateProviderMock.CURRENT_DATE;
import static com.codersteam.alwin.integration.mock.InvoiceServiceMock.*;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENT;
import static com.codersteam.alwin.integration.mock.InvolvementServiceMock.COMPANIES_INVOLVEMENTS;
import static com.codersteam.alwin.integration.mock.PersonServiceMock.PERSONS_BY_COMPANY_ID;
import static com.codersteam.alwin.integration.mock.SegmentServiceMock.COMPANIES_SEGMENT;
import static com.codersteam.alwin.testdata.CompanyTestData.*;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.DURATION_1;
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.DURATION_2;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_1;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_5;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.INVOLVEMENT_6;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.*;
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.customerInvolvementDto1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.*;
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jboss.arquillian.transaction.api.annotation.TransactionMode.DISABLED;
import static org.junit.Assert.*;

@UsingDataSet({"test-create-issues.json", "test-issue-type-configuration.json"})
public class CreateIssuesTestIT extends CoreTestBase {

    @Inject
    private CreateIssueService createIssueService;

    @Inject
    private IssueDao issueDao;

    @Inject
    private InvoiceDao invoiceDao;

    @Inject
    private IssueInvoiceDao issueInvoiceDao;

    @Test
    public void shouldCreateIssuesAndStoreInvoices() {
        // given
        CURRENT_DATE = "2017-07-11";

        // and there are due documents on DPD=1
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
        assertFalse(issues.isEmpty());
        assertEquals(1, issues.size());
        final Issue newIssue = issues.get(0);
        assertIssue(newIssue, IssueTypeName.PHONE_DEBT_COLLECTION_1, Segment.A, DURATION_1);
        assertNewIssueBalances(newIssue);
        assertIssueInvoices(newIssue.getIssueInvoices(), newIssue.getStartDate());

        // and invoices stored
        final List<Invoice> invoices = invoiceDao.all();
        assertFalse(invoices.isEmpty());
        assertEquals(4, invoices.size());

        final List<IssueInvoice> issueInvoices = issueInvoiceDao.all();
        assertFalse(issueInvoices.isEmpty());
        assertEquals(3, issueInvoices.size());
    }

    @Test
    public void shouldCreateCompanyPersonsWithIssueCreation() {
        // given
        CURRENT_DATE = "2017-07-11";

        // and there are due documents on DPD=1
        SIMPLE_INVOICES_BY_DUE_DATE.put("2017-07-10", dueSimpleInvoicesToCreate3Issues());

        //and invoices balances are
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_1, dueInvoice1());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_2, dueInvoice2());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_3, dueInvoice3());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_5, dueInvoice5());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_6, dueInvoice6());

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, singletonList(dueInvoiceWithCorrections1()));
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_5, singletonList(dueInvoiceWithCorrections5()));
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_6, singletonList(dueInvoiceWithCorrections6()));

        // and company data
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto1());
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_5, aidaCompanyDto5());
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_6, aidaCompanyDto6());

        // and company persons
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, singletonList(aidaPerson9999_1()));
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_5, singletonList(aidaPerson9995_1()));
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_6, emptyList());

        // and company involvement
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_1, INVOLVEMENT_1);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_5, INVOLVEMENT_5);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_6, INVOLVEMENT_6);

        // and company segment
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_1, A);
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_5, A);
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_6, A);

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_6, emptyList());

        // and no issues in db
        final List<Issue> issuesBeforeCreation = issueDao.findAllActiveIssues();
        assertTrue(issuesBeforeCreation.isEmpty());

        // when create issues
        createIssueService.createIssues();

        // then proper persons added to issues
        final List<CompanyPerson> company1Persons = getCompanyPersonsFromCompanyIssue(EXT_COMPANY_ID_1);
        assertCompanyPerson(company1Persons, aidaPerson9999_1());

        final List<CompanyPerson> company5Persons = getCompanyPersonsFromCompanyIssue(EXT_COMPANY_ID_5);
        assertCompanyPerson(company5Persons, aidaPerson9995_1());

        final List<CompanyPerson> company6Persons = getCompanyPersonsFromCompanyIssue(EXT_COMPANY_ID_6);
        assertTrue(company6Persons.isEmpty());
    }

    @Test
    @Transactional(DISABLED)
    public void shouldCreateIssuesEvenWhenErrorOccurs() {
        // given
        CURRENT_DATE = "2017-07-11";

        // and there are due documents on DPD=1
        SIMPLE_INVOICES_BY_DUE_DATE.put("2017-07-10", dueSimpleInvoicesWithExceptionCausingInvoice());

        //and invoices balances are
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_1, dueInvoice1());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_2, dueInvoice2());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_3, dueInvoice3());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_5, dueInvoice5());
        INVOICES_WITH_BALANCES_BY_INVOICE_ID.put(INVOICE_ID_6, dueInvoice6());

        // and
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_1, singletonList(dueInvoiceWithCorrections1()));
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_5, singletonList(dueInvoiceWithCorrections5()));
        INVOICES_FOR_BALANCE_CALCULATION_START.put(EXT_COMPANY_ID_6, singletonList(dueInvoiceWithCorrections6()));

        // and company data
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, aidaCompanyDto1());
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_5, aidaCompanyDto5());
        COMPANIES_BY_COMPANY_ID.put(EXT_COMPANY_ID_6, aidaCompanyDto6());

        // and company persons
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_1, asList(TEST_EXT_PERSON_1, TEST_EXT_PERSON_2));
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_5, emptyList());
        PERSONS_BY_COMPANY_ID.put(EXT_COMPANY_ID_6, emptyList());

        // and company involvement
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_1, INVOLVEMENT_1);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_5, INVOLVEMENT_5);
        COMPANIES_INVOLVEMENT.put(EXT_COMPANY_ID_6, INVOLVEMENT_6);

        // and company segment
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_1, A);
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_5, A);
        COMPANIES_SEGMENT.put(EXT_COMPANY_ID_6, A);

        // and RPB
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_5, emptyList());
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_6, emptyList());

        // and no issues in db
        final List<Issue> issuesBeforeCreation = issueDao.findAllActiveIssues();
        assertTrue(issuesBeforeCreation.isEmpty());

        // when create issues
        createIssueService.createIssues();

        // then new issue created
        final List<Issue> issues = issueDao.findAllActiveIssues();
        assertFalse(issues.isEmpty());
        assertEquals(3, issues.size());
        assertThat(issues).allMatch(issue -> !issue.isCreatedManually());
    }

    @Test
    public void shouldCreateWT2IssueAutomatically() {
        // given
        CURRENT_DATE = "2017-07-26";

        // and there are due documents on DPD=16 (WT2, Segment A)
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
        COMPANIES_INVOLVEMENTS.put(EXT_COMPANY_ID_1, emptyList());

        // when create issues
        createIssueService.createIssues();

        // then new issue created
        final List<Issue> issues = issueDao.findAllActiveIssues();
        assertFalse(issues.isEmpty());
        assertEquals(1, issues.size());
        final Issue newIssue = issues.get(0);
        assertIssue(newIssue, PHONE_DEBT_COLLECTION_2, Segment.A, DURATION_2);
    }

    @SuppressWarnings("SameParameterValue")
    private void assertIssue(final Issue issue, final IssueTypeName issueTypeName, final Segment segment, final Integer duration) {
        // assert type and expiration date
        assertEquals(issueTypeName, issue.getIssueType().getName());
        final Date expirationDate = DateUtils.datePlusDays(issue.getStartDate(), duration);
        assertEquals(expirationDate, issue.getExpirationDate());

        // assert issue customer
        assertNotNull(issue.getCustomer());
        assertNotNull(issue.getCustomer().getCompany());
        final Company company = issue.getCustomer().getCompany();
        assertEquals(segment, Segment.A);
        assertEquals(INVOLVEMENT_1, company.getInvolvement());
    }

    private void assertNewIssueBalances(final Issue issue) {
        assertEquals(new BigDecimal("0.00"), issue.getRpbEUR());
        assertEquals(new BigDecimal("0.00"), issue.getBalanceStartEUR());
        assertEquals(new BigDecimal("0.00"), issue.getCurrentBalanceEUR());
        assertEquals(new BigDecimal("0.00"), issue.getPaymentsEUR());
        assertEquals(new BigDecimal("10.00"), issue.getRpbPLN());
        assertEquals(new BigDecimal("-99334.35"), issue.getBalanceStartPLN());
        assertEquals(new BigDecimal("-99334.35"), issue.getCurrentBalancePLN());
        assertEquals(new BigDecimal("0.00"), issue.getPaymentsPLN());
    }

    private void assertIssueInvoices(final List<IssueInvoice> issueInvoices, final Date issueStartDate) {
        issueInvoices.forEach(issueInvoice -> {
            final Invoice invoice = issueInvoice.getInvoice();
            final BigDecimal currentBalance = invoice.getCurrentBalance();
            assertEquals(InvoiceUtils.isIssueSubjectForNewIssue(currentBalance, issueStartDate, invoice.getRealDueDate()), issueInvoice.getIssueSubject());
            assertEquals(currentBalance, issueInvoice.getFinalBalance());
            assertEquals(currentBalance, issueInvoice.getFinalBalance());
        });
    }

    private void assertCompanyPerson(final List<CompanyPerson> companyPersons, final AidaPersonDto aidaPersonDto) {
        assertEquals(1, companyPersons.size());
        final Person person = companyPersons.get(0).getPerson();
        assertEquals(aidaPersonDto.getFirstName(), person.getFirstName());
        assertEquals(aidaPersonDto.getLastName(), person.getLastName());
    }

    private List<CompanyPerson> getCompanyPersonsFromCompanyIssue(final Long extCompanyId) {
        final Optional<Long> issueId = issueDao.findCompanyActiveIssueId(extCompanyId);
        assertTrue(issueId.isPresent());
        final Optional<Issue> issue = issueDao.get(issueId.get());
        assertTrue(issue.isPresent());
        return issue.map(i -> i.getCustomer().getCompany().getCompanyPersons()).get();
    }
}

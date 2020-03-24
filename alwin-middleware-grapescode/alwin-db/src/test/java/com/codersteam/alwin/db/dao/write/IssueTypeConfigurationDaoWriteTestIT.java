package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao;
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration;
import com.codersteam.alwin.testdata.IssueTypeConfigurationTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @author Tomasz Sliwinski
 */
public class IssueTypeConfigurationDaoWriteTestIT extends WriteTestBase {

    @EJB
    private IssueTypeConfigurationDao issueTypeConfigurationDao;

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-configuration-include-due-invoices-as-subjects.json"})
    // Dodane w teście "write" z powodu specyficznego datasetu dla testu
    public void shouldFindIfIncludeDebtInvoicesAsSubjectsDuringIssue() {
        // when
        final Boolean includeDebtInvoicesDuringIssueByTypeNameAndSegment =
                issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(IssueTypeName.PHONE_DEBT_COLLECTION_1, Segment.A);

        // then
        assertTrue(includeDebtInvoicesDuringIssueByTypeNameAndSegment);
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-configuration-not-include-due-invoices-as-subjects.json"})
    // Dodane w teście "write" z powodu specyficznego datasetu dla testu
    public void shouldFindIfNotIncludeDebtInvoicesAsSubjectsDuringIssue() {
        // when
        final Boolean includeDebtInvoicesDuringIssueByTypeNameAndSegment =
                issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(IssueTypeName.PHONE_DEBT_COLLECTION_1, Segment.A);

        // then
        assertFalse(includeDebtInvoicesDuringIssueByTypeNameAndSegment);
    }

    @Test
    @UsingDataSet({"test-permission.json", "test-data.json", "test-issue.json", "test-issue-invoice.json", "test-issue-type-configuration.json"})
    public void updateShouldNotOverwriteCreateAutomaticallyFlag() throws Exception {
        // given
        final Optional<IssueTypeConfiguration> issueTypeConfiguration = issueTypeConfigurationDao.get(IssueTypeConfigurationTestData.ID_1);
        assertTrue(issueTypeConfiguration.isPresent());

        // and switch flag
        final IssueTypeConfiguration issueConfiguration = issueTypeConfiguration.get();
        assertTrue(issueConfiguration.getCreateAutomatically());
        issueConfiguration.setCreateAutomatically(false);

        // when
        issueTypeConfigurationDao.update(issueConfiguration);
        commitTrx();

        // then flag is still set to true
        final Optional<IssueTypeConfiguration> issueTypeConfigurationAfter = issueTypeConfigurationDao.get(IssueTypeConfigurationTestData.ID_1);
        assertTrue(issueTypeConfigurationAfter.isPresent());
        assertTrue(issueTypeConfigurationAfter.get().getCreateAutomatically());
    }
}

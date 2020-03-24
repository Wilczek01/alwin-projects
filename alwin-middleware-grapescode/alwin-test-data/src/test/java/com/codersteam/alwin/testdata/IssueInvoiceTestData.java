package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoiceId;

/**
 * @author Adam Stepnowski
 */
public class IssueInvoiceTestData {

    public static final long ISSUE_ID_21 = 21;
    public static final long ISSUE_ID_1 = 1;
    public static final long INVOICE_ID_1 = 1;
    public static final long INVOICE_ID_2 = 2;
    public static final long INVOICE_ID_3 = 3;
    public static final IssueInvoiceId ISSUE_21_INVOICE_2_ID = createIssueInvoiceId(ISSUE_ID_21, INVOICE_ID_2);
    public static final IssueInvoiceId ISSUE_21_INVOICE_3_ID = createIssueInvoiceId(ISSUE_ID_21, INVOICE_ID_3);
    public static final IssueInvoiceId ISSUE_1_INVOICE_1_ID = createIssueInvoiceId(ISSUE_ID_1, INVOICE_ID_1);

    public static IssueInvoiceId createIssueInvoiceId(final Long issueId, final Long invoiceId) {
        final IssueInvoiceId issueInvoiceId = new IssueInvoiceId();

        final Issue issue = new Issue();
        issue.setId(issueId);

        final Invoice invoice = new Invoice();
        invoice.setId(invoiceId);

        issueInvoiceId.setIssue(issue);
        issueInvoiceId.setInvoice(invoice);
        return issueInvoiceId;
    }
}

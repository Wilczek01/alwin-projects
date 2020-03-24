package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.contract.ContractDto;
import com.codersteam.alwin.core.api.model.customer.CustomerDto;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.issue.*;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.IssueState;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings("WeakerAccess")
public class AbstractIssuesTestData {

    protected static Issue newEntity(final Long id, final Operator assignee, final Customer customer, final Contract contract, final Date startDate,
                                     final Date expirationDate, final String terminationCause, final IssueType issueType, final IssueState issueState,
                                     final BigDecimal rpbPLN, final BigDecimal balanceStartPLN, final BigDecimal currentBalancePLN, final BigDecimal paymentsPLN,
                                     final List<Invoice> invoices, final Boolean excludedFromStats, final Integer priority,
                                     final Date balanceUpdateDate, final boolean createdManually, final Issue parentIssue, final Date terminationDate,
                                     final Operator terminationOperator, final String exclusionCause, final BigDecimal rpbEUR, final BigDecimal balanceStartEUR,
                                     final BigDecimal currentBalanceEUR, final BigDecimal paymentsEUR, final List<Activity> activities,
                                     final BigDecimal totalBalanceStartPLN, final BigDecimal totalCurrentBalancePLN, final BigDecimal totalPaymentsPLN,
                                     final Date dpdStartDate) {
        final Issue issue = new Issue();
        issue.setId(id);
        issue.setAssignee(assignee);
        issue.setCustomer(customer);
        issue.setContract(contract);
        issue.setStartDate(startDate);
        issue.setExpirationDate(expirationDate);
        issue.setTerminationCause(terminationCause);
        issue.setIssueType(issueType);
        issue.setIssueState(issueState);
        issue.setRpbPLN(rpbPLN);
        issue.setBalanceStartPLN(balanceStartPLN);
        issue.setCurrentBalancePLN(currentBalancePLN);
        issue.setPaymentsPLN(paymentsPLN);
        issue.setRpbEUR(rpbEUR);
        issue.setBalanceStartEUR(balanceStartEUR);
        issue.setCurrentBalanceEUR(currentBalanceEUR);
        issue.setPaymentsEUR(paymentsEUR);
        issue.setIssueInvoices(createIssueInvoices(issue, invoices));
        issue.setExcludedFromStats(excludedFromStats);
        issue.setPriority(priority);
        issue.setBalanceUpdateDate(balanceUpdateDate);
        issue.setCreatedManually(createdManually);
        issue.setParentIssue(parentIssue);
        issue.setTerminationDate(terminationDate);
        issue.setTerminationOperator(terminationOperator);
        issue.setExclusionCause(exclusionCause);
        issue.setActivities(activities);
        issue.setTags(Collections.emptySet());
        issue.setTotalBalanceStartPLN(totalBalanceStartPLN);
        issue.setTotalCurrentBalancePLN(totalCurrentBalancePLN);
        issue.setTotalPaymentsPLN(totalPaymentsPLN);
        issue.setDpdStartDate(dpdStartDate);
        return issue;
    }

    protected static Issue newEntity(final Long id, final Operator assignee, final Customer customer, final Contract contract, final Date startDate,
                                     final Date expirationDate, final String terminationCause, final IssueType issueType, final IssueState issueState,
                                     final BigDecimal rpbPLN, final BigDecimal balanceStartPLN, final BigDecimal currentBalancePLN, final BigDecimal paymentsPLN,
                                     final List<Invoice> invoices, final Boolean excludedFromStats, final Integer priority,
                                     final Date balanceUpdateDate, final boolean createdManually, final Issue parentIssue, final Date terminationDate,
                                     final Operator terminationOperator, final String exclusionCause, final BigDecimal rpbEUR, final BigDecimal balanceStartEUR,
                                     final BigDecimal currentBalanceEUR, final BigDecimal paymentsEUR, final List<Activity> activities, final Set<Tag> tags,
                                     final BigDecimal totalBalanceStartPLN, final BigDecimal totalCurrentBalancePLN, final BigDecimal totalPaymentsPLN,
                                     final Date dpdStartDate) {
        final Issue issue = newEntity(id, assignee, customer, contract, startDate, expirationDate, terminationCause, issueType, issueState, rpbPLN,
                balanceStartPLN, currentBalancePLN, paymentsPLN, invoices, excludedFromStats, priority, balanceUpdateDate, createdManually, parentIssue,
                terminationDate, terminationOperator, exclusionCause, rpbEUR, balanceStartEUR, currentBalanceEUR, paymentsEUR, activities,
                totalBalanceStartPLN, totalCurrentBalancePLN, totalPaymentsPLN, dpdStartDate);
        issue.setTags(tags);
        return issue;
    }

    protected static IssueDto newDto(final Long id, final OperatorUserDto assignee, final CustomerDto customer, final ContractDto contract, final Date startDate,
                                     final Date expirationDate, final String terminationCause, final IssueTypeDto issueType, final IssueStateDto issueState,
                                     final BigDecimal rpbPLN, final BigDecimal balanceStartPLN, final BigDecimal currentBalancePLN, final BigDecimal paymentsPLN,
                                     final List<InvoiceDto> invoice, final Boolean excludedFromStats, final Date balanceUpdateDate, final IssuePriorityDto priority,
                                     final Date terminationDate, final OperatorUserDto terminationOperator, final String exclusionCause,
                                     final BigDecimal rpbEUR, final BigDecimal balanceStartEUR, final BigDecimal currentBalanceEUR, final BigDecimal paymentsEUR,
                                     final BigDecimal totalBalanceStartPLN, final BigDecimal totalCurrentBalancePLN, final BigDecimal totalPaymentsPLN,
                                     final Long dpdStart, final Long dpdEstimated) {
        final IssueDto issue = new IssueDto();
        issue.setId(id);
        issue.setAssignee(assignee);
        issue.setCustomer(customer);
        issue.setContract(contract);
        issue.setStartDate(startDate);
        issue.setExpirationDate(expirationDate);
        issue.setTerminationCause(terminationCause);
        issue.setIssueType(issueType);
        issue.setIssueState(issueState);
        issue.setRpbPLN(rpbPLN);
        issue.setBalanceStartPLN(balanceStartPLN);
        issue.setPaymentsPLN(paymentsPLN);
        issue.setCurrentBalancePLN(currentBalancePLN);
        issue.setRpbEUR(rpbEUR);
        issue.setBalanceStartEUR(balanceStartEUR);
        issue.setPaymentsEUR(paymentsEUR);
        issue.setCurrentBalanceEUR(currentBalanceEUR);
        issue.setTotalBalanceStartPLN(totalBalanceStartPLN);
        issue.setTotalCurrentBalancePLN(totalCurrentBalancePLN);
        issue.setTotalPaymentsPLN(totalPaymentsPLN);
        issue.setExcludedFromStats(excludedFromStats);
        issue.setPriority(priority);
        issue.setBalanceUpdateDate(balanceUpdateDate);
        issue.setTerminationDate(terminationDate);
        issue.setTerminationOperator(terminationOperator);
        issue.setExclusionCause(exclusionCause);
        issue.setTags(Collections.emptyList());
        issue.setDpdStart(dpdStart);
        issue.setDpdEstimated(dpdEstimated);
        return issue;
    }

    protected static IssueDto newDto(final Long id, final OperatorUserDto assignee, final CustomerDto customer, final ContractDto contract, final Date startDate,
                                     final Date expirationDate, final String terminationCause, final IssueTypeDto issueType, final IssueStateDto issueState,
                                     final BigDecimal rpbPLN, final BigDecimal balanceStartPLN, final BigDecimal currentBalancePLN, final BigDecimal paymentsPLN,
                                     final List<InvoiceDto> invoice, final Boolean excludedFromStats, final Date balanceUpdateDate, final IssuePriorityDto priority,
                                     final Date terminationDate, final OperatorUserDto terminationOperator, final String exclusionCause,
                                     final BigDecimal rpbEUR, final BigDecimal balanceStartEUR, final BigDecimal currentBalanceEUR,
                                     final BigDecimal paymentsEUR, final BigDecimal totalBalanceStartPLN, final BigDecimal totalCurrentBalancePLN,
                                     final BigDecimal totalPaymentsPLN, final List<TagDto> tags, final Long dpdStart, final Long dpdEstimated) {
        final IssueDto issueDto = newDto(id, assignee, customer, contract, startDate, expirationDate, terminationCause, issueType, issueState, rpbPLN, balanceStartPLN,
                currentBalancePLN, paymentsPLN, invoice, excludedFromStats, balanceUpdateDate, priority, terminationDate, terminationOperator, exclusionCause,
                rpbEUR, balanceStartEUR, currentBalanceEUR, paymentsEUR, totalBalanceStartPLN, totalCurrentBalancePLN, totalPaymentsPLN, dpdStart, dpdEstimated);
        issueDto.setTags(tags);
        return issueDto;
    }

    public static List<IssueInvoice> createIssueInvoices(final Issue issue, final List<Invoice> invoices) {
        final List<IssueInvoice> issueInvoices = new ArrayList<>();
        for (final Invoice invoice : invoices) {
            final IssueInvoice issueInvoice = new IssueInvoice();
            issueInvoice.setIssue(issue);
            issueInvoice.setInvoice(invoice);
            issueInvoices.add(issueInvoice);
            issueInvoice.setAdditionDate(new Date());
            issueInvoice.setExcluded(false);
            issueInvoice.setIssueSubject(isIssueSubject(invoice.getCurrentBalance(), issue.getStartDate(), invoice.getRealDueDate()));
            issueInvoice.setInclusionBalance(invoice.getCurrentBalance());
            if (isIssueSubject(invoice.getCurrentBalance(), issue.getStartDate(), invoice.getRealDueDate())) {
                issueInvoice.setFinalBalance(invoice.getCurrentBalance().add(new BigDecimal("10.00")));
            }
        }
        return issueInvoices;
    }

    private static boolean isIssueSubject(final BigDecimal invoiceCurrentBalance, final Date issueStartDate, final Date invoiceDueDate) {
        return invoiceCurrentBalance.signum() < 0 && invoiceDueDate.before(issueStartDate);
    }

    protected static CompanyIssueDto companyIssueDto(final Long id, final Date startDate, final Date expirationDate, final String terminationCause,
                                                     final IssueTypeDto issueType, final IssueStateDto issueState, final BigDecimal rpbPLN,
                                                     final BigDecimal balanceStartPLN, final BigDecimal currentBalancePLN, final BigDecimal paymentsPLN,
                                                     final Boolean excludedFromStats, final Date terminationDate, final OperatorUserDto terminationOperator,
                                                     final BigDecimal rpbEUR, final BigDecimal balanceStartEUR, final BigDecimal currentBalanceEUR,
                                                     final BigDecimal paymentsEUR) {
        final CompanyIssueDto issue = new CompanyIssueDto();
        issue.setId(id);
        issue.setStartDate(startDate);
        issue.setExpirationDate(expirationDate);
        issue.setTerminationCause(terminationCause);
        issue.setTerminationOperator(terminationOperator);
        issue.setTerminationDate(terminationDate);
        issue.setIssueType(issueType);
        issue.setIssueState(issueState);
        issue.setRpbPLN(rpbPLN);
        issue.setBalanceStartPLN(balanceStartPLN);
        issue.setCurrentBalancePLN(currentBalancePLN);
        issue.setPaymentsPLN(paymentsPLN);
        issue.setRpbEUR(rpbEUR);
        issue.setBalanceStartEUR(balanceStartEUR);
        issue.setCurrentBalanceEUR(currentBalanceEUR);
        issue.setPaymentsEUR(paymentsEUR);
        issue.setExcludedFromStats(excludedFromStats);
        return issue;
    }
}

package com.codersteam.alwin.core.util;

import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.customer.CustomerDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.issue.Issue;

/**
 * @author Tomasz Sliwinski
 */
public final class IssueUtils {

    private IssueUtils() {
    }

    /**
     * Pobranie segmentu firmy ze zlecenia
     *
     * @param issue - zlecenie
     * @return segment klienta
     */
    public static Segment getCompanySegmentFromIssue(final Issue issue) {
        final Customer customer = getCustomer(issue);
        return customer.getCompany() != null ? customer.getCompany().getSegment() : null;
    }

    /**
     * Pobranie zewnętrznego identyfikatora klienta ze zlecenia
     *
     * @param issue - zlecenie
     * @return identyfikator klienta w systemie AIDA
     */
    public static Long getExtCompanyId(final IssueDto issue) {
        final CustomerDto customer = getCustomer(issue);
        return customer.getCompany() != null ? customer.getCompany().getExtCompanyId() : null;
    }

    private static CustomerDto getCustomer(final IssueDto issue) {
        if (issue.getCustomer() != null) {
            return issue.getCustomer();
        } else {
            return issue.getContract().getCustomer();
        }
    }

    /**
     * Pobranie zewnętrznego identyfikatora klienta ze zlecenia
     *
     * @param issue - zlecenie
     * @return identyfikator klienta w systemie AIDA
     */
    public static Long getExtCompanyId(final Issue issue) {
        final Customer customer = getCustomer(issue);
        return customer.getCompany() != null ? customer.getCompany().getExtCompanyId() : null;
    }

    private static Customer getCustomer(final Issue issue) {
        if (issue.getCustomer() != null) {
            return issue.getCustomer();
        } else {
            return issue.getContract().getCustomer();
        }
    }

    /**
     * Sprawdza czy zlecenie jest opłacone
     *
     * @param issue - zlecenie
     * @return true - jeżeli zlecenie jest opłacone, false w przeciwnym przypadku
     */
    public static boolean isPaid(final Issue issue) {
        return issue.getCurrentBalancePLN().signum() >= 0 && issue.getCurrentBalanceEUR().signum() >= 0;
    }
}

package com.codersteam.alwin.core.api.service.issue;

import javax.ejb.Local;

/**
 * @author Adam Stepnowski
 */
@Local
public interface InvoiceBalanceUpdaterService {

    /**
     * Uaktualnienie sald faktur zlecenia
     *
     * @param issueId - identyfikator zlecenia
     */
    void updateIssueInvoicesBalance(Long issueId);
}

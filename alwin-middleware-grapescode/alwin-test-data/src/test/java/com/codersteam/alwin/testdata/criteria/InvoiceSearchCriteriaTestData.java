package com.codersteam.alwin.testdata.criteria;

import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;

import java.util.Date;

import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_11;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

/**
 * @author Adam Stepnowski
 */
public class InvoiceSearchCriteriaTestData {

    private static final long TYPE_ID = 3L;
    private static final Date START_DUE_DATE = parse("2015-11-10 00:00:00.000000");
    private static final Date END_DUE_DATE = parse("2015-11-30 00:00:00.000000");


    public static InvoiceSearchCriteria invoiceSearchCriteria(final int firstResult, final int maxResults) {
        final InvoiceSearchCriteria searchCriteria = new InvoiceSearchCriteria();
        searchCriteria.setFirstResult(firstResult);
        searchCriteria.setMaxResults(maxResults);
        return searchCriteria;
    }

    public static InvoiceSearchCriteria invoiceSearchCriteriaWithContractNumber(final int firstResult, final int maxResults) {
        final InvoiceSearchCriteria searchCriteria = invoiceSearchCriteria(firstResult, maxResults);
        searchCriteria.setContractNumber(CONTRACT_NUMBER_INVOICE_11);
        return searchCriteria;
    }

    public static InvoiceSearchCriteria invoiceSearchCriteriaWithType(final int firstResult, final int maxResults) {
        final InvoiceSearchCriteria searchCriteria = invoiceSearchCriteria(firstResult, maxResults);
        searchCriteria.setTypeId(TYPE_ID);
        return searchCriteria;
    }

    public static InvoiceSearchCriteria invoiceSearchCriteriaWithStartDueDate(final int firstResult, final int maxResults) {
        final InvoiceSearchCriteria searchCriteria = invoiceSearchCriteria(firstResult, maxResults);
        searchCriteria.setStartDueDate(START_DUE_DATE);
        return searchCriteria;
    }

    public static InvoiceSearchCriteria invoiceSearchCriteriaWithStartDueDateAndEndDueDate(final int firstResult, final int maxResults) {
        final InvoiceSearchCriteria searchCriteria = invoiceSearchCriteria(firstResult, maxResults);
        searchCriteria.setStartDueDate(START_DUE_DATE);
        searchCriteria.setEndDueDate(END_DUE_DATE);
        return searchCriteria;
    }

    public static InvoiceSearchCriteria invoiceSearchCriteriaWithOverdueOnly(final int firstResult, final int maxResults) {
        final InvoiceSearchCriteria searchCriteria = invoiceSearchCriteria(firstResult, maxResults);
        searchCriteria.setNotPaidOnly(true);
        return searchCriteria;
    }
}

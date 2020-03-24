package com.codersteam.alwin.testdata.criteria;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;

import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public class DemandForPaymentSearchCriteriaTestData {

    public static DemandForPaymentSearchCriteria demandForPaymentSearchCriteria(final int firstResult, final int maxResults) {
        final DemandForPaymentSearchCriteria criteria = new DemandForPaymentSearchCriteria();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        return criteria;
    }

    public static DemandForPaymentSearchCriteria automaticallyCreatedDemandForPaymentSearchCriteria(final int firstResult, final int maxResults) {
        final DemandForPaymentSearchCriteria criteria = new DemandForPaymentSearchCriteria();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.setAborted(false);
        criteria.setCreatedManually(false);
        return criteria;
    }

    public static DemandForPaymentSearchCriteria automaticallyCreatedDemandForPaymentSearchCriteriaByState(final int firstResult, final int maxResults,
                                                                                                           final DemandForPaymentState state) {
        final DemandForPaymentSearchCriteria criteria = automaticallyCreatedDemandForPaymentSearchCriteria(firstResult, maxResults);
        criteria.setState(Collections.singletonList(state));
        return criteria;
    }

    public static DemandForPaymentSearchCriteria demandForPaymentSearchCriteriaByState(final int firstResult, final int maxResults,
                                                                                       final DemandForPaymentState state) {
        final DemandForPaymentSearchCriteria criteria = demandForPaymentSearchCriteria(firstResult, maxResults);
        criteria.setState(Collections.singletonList(state));
        return criteria;
    }

    public static DemandForPaymentSearchCriteria manualDemandForPaymentSearchCriteriaByState(final int firstResult, final int maxResults,
                                                                                             final DemandForPaymentState state) {
        final DemandForPaymentSearchCriteria criteria = automaticallyCreatedDemandForPaymentSearchCriteria(firstResult, maxResults);
        criteria.setState(Collections.singletonList(state));
        criteria.setCreatedManually(true);
        return criteria;
    }

    public static DemandForPaymentSearchCriteria automaticallyCreatedDemandForPaymentSearchCriteriaByContractNo(final int firstResult, final int maxResults,
                                                                                                                final String contractNo) {
        final DemandForPaymentSearchCriteria criteria = automaticallyCreatedDemandForPaymentSearchCriteria(firstResult, maxResults);
        criteria.setContractNo(contractNo);
        return criteria;
    }

    public static DemandForPaymentSearchCriteria automaticallyCreatedDemandForPaymentSearchCriteriaByType(final int firstResult, final int maxResults,
                                                                                                          final DemandForPaymentTypeKey... type) {
        final DemandForPaymentSearchCriteria criteria = automaticallyCreatedDemandForPaymentSearchCriteria(firstResult, maxResults);
        criteria.setTypes(asList(type));
        return criteria;
    }


}

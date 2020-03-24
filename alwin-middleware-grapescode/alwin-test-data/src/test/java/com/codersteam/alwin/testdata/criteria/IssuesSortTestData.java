package com.codersteam.alwin.testdata.criteria;


import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;

import java.util.LinkedHashMap;

/**
 * @author Michal Horowic
 */
public class IssuesSortTestData {

    public static LinkedHashMap<IssueSortField, SortOrder> sortByBalanceStartDescending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.BALANCE_START, SortOrder.DESC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByCurrentBalanceDescending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.CURRENT_BALANCE, SortOrder.DESC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByCustomerNameAscending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.CUSTOMER, SortOrder.ASC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByExpirationDateAscending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.EXPIRATION_DATE, SortOrder.ASC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByPaymentsAscending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.PAYMENTS, SortOrder.ASC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByRpbAscending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.RPB, SortOrder.ASC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByStartDateAscending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.START_DATE, SortOrder.ASC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByStateAscending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.STATE, SortOrder.ASC);
        return sort;
    }

    public static LinkedHashMap<IssueSortField, SortOrder> sortByTypeNameDescending() {
        final LinkedHashMap<IssueSortField, SortOrder> sort = new LinkedHashMap<>();
        sort.put(IssueSortField.TYPE, SortOrder.DESC);
        return sort;
    }

}

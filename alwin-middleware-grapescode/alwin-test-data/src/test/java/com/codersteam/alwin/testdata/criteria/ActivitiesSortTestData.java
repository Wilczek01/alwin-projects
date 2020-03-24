package com.codersteam.alwin.testdata.criteria;


import com.codersteam.alwin.common.sort.ActivitySortField;
import com.codersteam.alwin.common.sort.SortOrder;

import java.util.LinkedHashMap;

/**
 * @author Tomasz Sliwinski
 */
public class ActivitiesSortTestData {

    public static LinkedHashMap<ActivitySortField, SortOrder> sortByOperatorLastNameAscending() {
        final LinkedHashMap<ActivitySortField, SortOrder> sortCriteria = new LinkedHashMap<>();
        sortCriteria.put(ActivitySortField.OPERATOR_LAST_NAME, SortOrder.ASC);
        return sortCriteria;
    }

    public static LinkedHashMap<ActivitySortField, SortOrder> sortByOperatorLastNameDecending() {
        final LinkedHashMap<ActivitySortField, SortOrder> sortCriteria = new LinkedHashMap<>();
        sortCriteria.put(ActivitySortField.OPERATOR_LAST_NAME, SortOrder.DESC);
        return sortCriteria;
    }

    public static LinkedHashMap<ActivitySortField, SortOrder> sortByOperatorFullNameAscending() {
        final LinkedHashMap<ActivitySortField, SortOrder> sortCriteria = new LinkedHashMap<>();
        sortCriteria.put(ActivitySortField.OPERATOR, SortOrder.ASC);
        return sortCriteria;
    }

    public static LinkedHashMap<ActivitySortField, SortOrder> emptySortCriteria() {
        return new LinkedHashMap<>();
    }
}

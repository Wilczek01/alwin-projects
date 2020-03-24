package com.codersteam.alwin.model.order;

import com.codersteam.alwin.common.sort.SortOrder;

import java.util.Collections;
import java.util.Map;

/**
 * Klasa budująca mapę kolumn, po której nastapi sortowanie wraz z wyznaczoną kolejnością
 */
public class SortUtil {

    private SortUtil() {
    }

    public static Map<String, SortOrder> sortBy(String column) {
        if (column == null) {
            return Collections.emptyMap();
        }

        if (column.charAt(0) == '-') {
            return Collections.singletonMap(column.substring(1), SortOrder.DESC);
        }
        return Collections.singletonMap(column, SortOrder.ASC);
    }
}


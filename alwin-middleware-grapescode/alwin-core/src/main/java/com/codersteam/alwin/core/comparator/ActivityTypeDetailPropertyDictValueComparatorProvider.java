package com.codersteam.alwin.core.comparator;

import com.codersteam.alwin.jpa.activity.ActivityTypeDetailPropertyDictValue;

import java.util.Comparator;

/**
 * @author Tomasz Sliwinski
 */
public final class ActivityTypeDetailPropertyDictValueComparatorProvider {

    /**
     * Komparator do porównywania wartości słownikowych dla cechy dodatkowej czynności zlecenia
     */
    public static final Comparator<ActivityTypeDetailPropertyDictValue> ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_COMPARATOR =
            Comparator.comparing(ActivityTypeDetailPropertyDictValue::getId);

    private ActivityTypeDetailPropertyDictValueComparatorProvider() {
    }
}

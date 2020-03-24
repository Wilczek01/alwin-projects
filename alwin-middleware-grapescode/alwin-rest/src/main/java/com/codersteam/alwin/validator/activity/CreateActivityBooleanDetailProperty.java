package com.codersteam.alwin.validator.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;

/**
 * Klasa potrafiąca utworzyć obiekt typu {@link java.lang.Boolean} z wartością dla szczegółów czynności windykacyjnych
 *
 * @author Michal Horowic
 */
final class CreateActivityBooleanDetailProperty implements CreateActivityDetailProperty {
    @Override
    public Object parseDetailProperty(ActivityDetailDto activityDetail) {
        final String value = activityDetail.getValue();
        if (value == null) {
            return null;
        } else if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
            return true;
        } else if (Boolean.FALSE.toString().equalsIgnoreCase(value)) {
            return false;
        }

        throw new IllegalArgumentException("The String did not match either specified value");
    }
}

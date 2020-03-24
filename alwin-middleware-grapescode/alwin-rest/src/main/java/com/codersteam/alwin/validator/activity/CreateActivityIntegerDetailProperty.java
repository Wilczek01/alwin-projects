package com.codersteam.alwin.validator.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;

/**
 * Klasa potrafiąca utworzyć obiekt typu {@link java.lang.Integer} z wartością dla szczegółów czynności windykacyjnych
 *
 * @author Michal Horowic
 */
final class CreateActivityIntegerDetailProperty implements CreateActivityDetailProperty {
    @Override
    public Object parseDetailProperty(ActivityDetailDto activityDetail) {
        return Integer.valueOf(activityDetail.getValue());
    }
}

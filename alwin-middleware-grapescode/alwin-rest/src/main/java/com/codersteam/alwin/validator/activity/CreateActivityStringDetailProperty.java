package com.codersteam.alwin.validator.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;

/**
 * Klasa potrafiąca utworzyć obiekt typu {@link java.lang.String} z wartością dla szczegółów czynności windykacyjnych
 *
 * @author Michal Horowic
 */
final class CreateActivityStringDetailProperty implements CreateActivityDetailProperty {
    @Override
    public Object parseDetailProperty(ActivityDetailDto activityDetail) {
        return String.valueOf(activityDetail.getValue());
    }
}

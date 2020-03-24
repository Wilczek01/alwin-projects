package com.codersteam.alwin.validator.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;

/**
 * Interfejs dla klas potrafiących utworzyć obiekt z wartością dla szczegółów czynności windykacyjnych
 *
 * @author Michal Horowic
 */
public interface CreateActivityDetailProperty {

    Object parseDetailProperty(final ActivityDetailDto activityDetail);
}

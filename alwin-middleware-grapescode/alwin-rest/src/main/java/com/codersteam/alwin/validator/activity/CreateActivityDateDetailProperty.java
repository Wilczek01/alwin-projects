package com.codersteam.alwin.validator.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codersteam.alwin.common.AlwinConstants.SYSTEM_DEFAULT_TIME_ZONE;

/**
 * Klasa potrafiąca utworzyć obiekt typu {@link java.util.Date} z wartością dla szczegółów czynności windykacyjnych
 *
 * @author Michal Horowic
 */
final class CreateActivityDateDetailProperty implements CreateActivityDetailProperty {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public Object parseDetailProperty(final ActivityDetailDto activityDetail) {
        final LocalDateTime localDateTime = LocalDateTime.parse(activityDetail.getValue(), FORMATTER);
        return Date.from(localDateTime.atZone(SYSTEM_DEFAULT_TIME_ZONE).toInstant());
    }
}

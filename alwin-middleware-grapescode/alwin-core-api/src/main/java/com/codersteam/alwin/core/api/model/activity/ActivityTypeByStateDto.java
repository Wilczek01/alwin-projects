package com.codersteam.alwin.core.api.model.activity;

import java.util.List;

/**
 * @author Michal Horowic
 */
public class ActivityTypeByStateDto {

    private final List<ActivityTypeDto> planned;
    private final List<ActivityTypeDto> executed;

    public ActivityTypeByStateDto(final List<ActivityTypeDto> planned, final List<ActivityTypeDto> executed) {
        this.planned = planned;
        this.executed = executed;
    }

    public List<ActivityTypeDto> getPlanned() {
        return planned;
    }

    public List<ActivityTypeDto> getExecuted() {
        return executed;
    }
}
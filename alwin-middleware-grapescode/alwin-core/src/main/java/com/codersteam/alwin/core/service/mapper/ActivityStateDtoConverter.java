package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.jpa.type.ActivityState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Piotr Naroznik
 */
public class ActivityStateDtoConverter extends CustomConverter<ActivityState, ActivityStateDto> {

    @Override
    public ActivityStateDto convert(final ActivityState activityState, final Type<? extends ActivityStateDto> type, final MappingContext mappingContext) {
        return ActivityStateDto.valueOf(activityState.name());
    }
}

package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.jpa.type.ActivityState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Piotr Naroznik
 */
public class ActivityStateConverter extends CustomConverter<ActivityStateDto, ActivityState> {

    @Override
    public ActivityState convert(final ActivityStateDto activityState, final Type<? extends ActivityState> type, final MappingContext mappingContext) {
        return ActivityState.valueOf(activityState.getKey());
    }
}

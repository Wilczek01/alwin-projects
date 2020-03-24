package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerTaskTypeDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class SchedulerTaskTypeDtoConverter extends CustomConverter<SchedulerTaskType, SchedulerTaskTypeDto> {

    @Override
    public SchedulerTaskTypeDto convert(final SchedulerTaskType schedulerType, final Type<? extends SchedulerTaskTypeDto> type, final MappingContext mappingContext) {
        return SchedulerTaskTypeDto.valueOf(schedulerType.name());
    }
}

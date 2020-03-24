package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerTaskTypeDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class SchedulerTaskTypeConverter extends CustomConverter<SchedulerTaskTypeDto, SchedulerTaskType> {

    @Override
    public SchedulerTaskType convert(final SchedulerTaskTypeDto schedulerType, final Type<? extends SchedulerTaskType> type, final MappingContext mappingContext) {
        return SchedulerTaskType.valueOf(schedulerType.getKey());
    }
}

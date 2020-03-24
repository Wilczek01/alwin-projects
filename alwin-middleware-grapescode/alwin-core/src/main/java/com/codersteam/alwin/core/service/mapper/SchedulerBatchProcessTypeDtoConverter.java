package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Dariusz Rackowski
 */
public class SchedulerBatchProcessTypeDtoConverter extends CustomConverter<SchedulerBatchProcessType, SchedulerBatchProcessTypeDto> {

    @Override
    public SchedulerBatchProcessTypeDto convert(final SchedulerBatchProcessType schedulerBatchProcessType,
                                                final Type<? extends SchedulerBatchProcessTypeDto> type,
                                                final MappingContext mappingContext) {
        return SchedulerBatchProcessTypeDto.valueOf(schedulerBatchProcessType.name());
    }
}

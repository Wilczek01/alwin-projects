package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Dariusz Rackowski
 */
public class SchedulerBatchProcessTypeConverter extends CustomConverter<SchedulerBatchProcessTypeDto, SchedulerBatchProcessType> {

    @Override
    public SchedulerBatchProcessType convert(final SchedulerBatchProcessTypeDto schedulerBatchProcessType,
                                             final Type<? extends SchedulerBatchProcessType> type,
                                             final MappingContext mappingContext) {
        return SchedulerBatchProcessType.valueOf(schedulerBatchProcessType.getKey());
    }
}

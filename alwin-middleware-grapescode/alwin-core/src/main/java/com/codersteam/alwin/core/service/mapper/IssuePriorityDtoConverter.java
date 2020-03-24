package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.issue.IssuePriorityDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class IssuePriorityDtoConverter extends CustomConverter<Integer, IssuePriorityDto> {

    @Override
    public IssuePriorityDto convert(Integer priority, Type<? extends IssuePriorityDto> type, MappingContext mappingContext) {
        return IssuePriorityDto.valueOf(priority);
    }
}

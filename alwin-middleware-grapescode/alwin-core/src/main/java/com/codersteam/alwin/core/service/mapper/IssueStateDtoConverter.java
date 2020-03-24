package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.issue.IssueStateDto;
import com.codersteam.alwin.jpa.type.IssueState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Piotr Naroznik
 */
public class IssueStateDtoConverter extends CustomConverter<IssueState, IssueStateDto> {

    @Override
    public IssueStateDto convert(final IssueState issueState, final Type<? extends IssueStateDto> type, final MappingContext mappingContext) {
        return IssueStateDto.valueOf(issueState.name());
    }
}
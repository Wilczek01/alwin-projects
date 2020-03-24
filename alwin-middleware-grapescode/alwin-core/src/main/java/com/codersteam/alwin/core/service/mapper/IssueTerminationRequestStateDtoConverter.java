package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestStateDto;
import com.codersteam.alwin.jpa.type.IssueTerminationRequestState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestStateDtoConverter extends CustomConverter<IssueTerminationRequestState, IssueTerminationRequestStateDto> {

    @Override
    public IssueTerminationRequestStateDto convert(final IssueTerminationRequestState state, final Type<? extends IssueTerminationRequestStateDto> type,
                                                   final MappingContext mappingContext) {
        return IssueTerminationRequestStateDto.valueOf(state.name());
    }
}
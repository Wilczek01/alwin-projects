package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestStateDto;
import com.codersteam.alwin.jpa.type.IssueTerminationRequestState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestStateConverter extends CustomConverter<IssueTerminationRequestStateDto, IssueTerminationRequestState> {

    @Override
    public IssueTerminationRequestState convert(final IssueTerminationRequestStateDto stateDto, final Type<? extends IssueTerminationRequestState> type,
                                                final MappingContext mappingContext) {
        return IssueTerminationRequestState.valueOf(stateDto.getKey());
    }
}
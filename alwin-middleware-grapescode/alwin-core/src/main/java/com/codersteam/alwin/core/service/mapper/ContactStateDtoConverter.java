package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.customer.ContactStateDto;
import com.codersteam.alwin.jpa.type.ContactState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class ContactStateDtoConverter extends CustomConverter<ContactState, ContactStateDto> {

    @Override
    public ContactStateDto convert(final ContactState contactState, final Type<? extends ContactStateDto> type, final MappingContext mappingContext) {
        return ContactStateDto.valueOf(contactState.name());
    }
}

package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.customer.ContactStateDto;
import com.codersteam.alwin.jpa.type.ContactState;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class ContactStateConverter extends CustomConverter<ContactStateDto, ContactState> {

    @Override
    public ContactState convert(final ContactStateDto contactStateDto, final Type<? extends ContactState> type, final MappingContext mappingContext) {
        return ContactState.valueOf(contactStateDto.getKey());
    }
}

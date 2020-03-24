package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.customer.ContactTypeDto;
import com.codersteam.alwin.jpa.type.ContactType;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class ContactTypeDtoConverter extends CustomConverter<ContactType, ContactTypeDto> {

    @Override
    public ContactTypeDto convert(final ContactType contactType, final Type<? extends ContactTypeDto> type, final MappingContext mappingContext) {
        return ContactTypeDto.valueOf(contactType.name());
    }
}

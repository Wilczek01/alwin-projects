package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.customer.ContactTypeDto;
import com.codersteam.alwin.jpa.type.ContactType;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class ContactTypeConverter extends CustomConverter<ContactTypeDto, ContactType> {

    @Override
    public ContactType convert(final ContactTypeDto contactTypeDto, final Type<? extends ContactType> type, final MappingContext mappingContext) {
        return ContactType.valueOf(contactTypeDto.getKey());
    }
}

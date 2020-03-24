package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.customer.AddressTypeDto;
import com.codersteam.alwin.jpa.type.AddressType;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class AddressTypeConverter extends CustomConverter<AddressTypeDto, AddressType> {

    @Override
    public AddressType convert(final AddressTypeDto addressTypeDto, final Type<? extends AddressType> type, final MappingContext mappingContext) {
        return AddressType.valueOf(addressTypeDto.getKey());
    }
}

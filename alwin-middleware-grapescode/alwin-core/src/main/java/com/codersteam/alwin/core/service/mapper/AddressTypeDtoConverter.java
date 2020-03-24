package com.codersteam.alwin.core.service.mapper;

import com.codersteam.alwin.core.api.model.customer.AddressTypeDto;
import com.codersteam.alwin.jpa.type.AddressType;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class AddressTypeDtoConverter extends CustomConverter<AddressType, AddressTypeDto> {

    @Override
    public AddressTypeDto convert(final AddressType addressType, final Type<? extends AddressTypeDto> type, final MappingContext mappingContext) {
        return AddressTypeDto.valueOf(addressType.name());
    }
}

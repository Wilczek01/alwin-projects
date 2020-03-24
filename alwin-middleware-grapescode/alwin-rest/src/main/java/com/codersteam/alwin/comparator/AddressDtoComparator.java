package com.codersteam.alwin.comparator;

import com.codersteam.alwin.core.api.model.customer.AddressDto;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.codersteam.alwin.core.api.model.customer.AddressTypeDto.ADDRESS_TYPES_WITH_ORDER;

/**
 * Komparator adresów, sortuje wyniki aby adresy tego samego typu były obok siebie
 */
public class AddressDtoComparator implements Comparator<AddressDto> {

    private static final Map<String, Integer> TYPE_ORDER = new HashMap<>(ADDRESS_TYPES_WITH_ORDER.size());

    static {
        final AtomicInteger value = new AtomicInteger();
        ADDRESS_TYPES_WITH_ORDER.forEach(contactTypeDto -> TYPE_ORDER.put(contactTypeDto.getKey(), value.incrementAndGet()));
    }

    @Override
    public int compare(final AddressDto address1, final AddressDto address2) {
        final int result = TYPE_ORDER.get(address1.getAddressType().getKey()) - TYPE_ORDER.get(address2.getAddressType().getKey());
        if (result != 0) {
            return result;
        }
        return Long.compare(address1.getId(), address2.getId());
    }
}
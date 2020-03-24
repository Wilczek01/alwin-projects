package com.codersteam.alwin.comparator;

import com.codersteam.alwin.core.api.model.customer.ContactDetailDto;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.codersteam.alwin.core.api.model.customer.ContactStateDto.ALL_CONTACT_STATES_WITH_ORDER;
import static com.codersteam.alwin.core.api.model.customer.ContactTypeDto.ALL_CONTACT_TYPES_WITH_ORDER;

/**
 * Komparator kontaktów, sortuje wyniki w następującej kolejności: status, typ, identyfikator
 */
public final class ContactDetailDtoComparatorProvider {

    private ContactDetailDtoComparatorProvider() {
    }

    private static final Map<String, Integer> TYPE_ORDER = new HashMap<>(ALL_CONTACT_TYPES_WITH_ORDER.size());
    private static final Map<String, Integer> STATE_ORDER = new HashMap<>(ALL_CONTACT_STATES_WITH_ORDER.size());

    static {
        final AtomicInteger value = new AtomicInteger();
        ALL_CONTACT_TYPES_WITH_ORDER.forEach(contactTypeDto -> TYPE_ORDER.put(contactTypeDto.getKey(), value.incrementAndGet()));
        ALL_CONTACT_STATES_WITH_ORDER.forEach(contactStateDto -> STATE_ORDER.put(contactStateDto.getKey(), value.incrementAndGet()));
    }

    public static final Comparator<ContactDetailDto> CONTACT_DETAIL_DTO_COMPARATOR =
            Comparator.comparing(ContactDetailDto::getState, Comparator.comparingInt(state -> STATE_ORDER.get(state.getKey())))
                    .thenComparing(ContactDetailDto::getContactType, Comparator.comparingInt(type -> TYPE_ORDER.get(type.getKey())))
                    .thenComparingLong(ContactDetailDto::getId);
}

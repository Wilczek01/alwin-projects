package com.codersteam.alwin.core.service.impl.issue.preparator;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.type.ContactImportedType;
import com.codersteam.alwin.jpa.type.ContactState;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codersteam.alwin.jpa.type.ContactType.forImportedType;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Tomasz Sliwinski
 */
public final class ContactDetailPreparator {

    private ContactDetailPreparator() {
    }

    public static Set<ContactDetail> prepareContactDetails(final AidaCompanyDto aidaCompany) {
        return stream(ContactImportedType.values())
                .map(contactImportedType -> prepareContactDetail(contactImportedType.getPropertyValue(aidaCompany), contactImportedType))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }

    private static ContactDetail prepareContactDetail(final String contact, final ContactImportedType type) {
        if (isBlank(contact)) {
            return null;
        }
        return new ContactDetail(contact, forImportedType(type), ContactState.ACTIVE, null, true, type);
    }
}

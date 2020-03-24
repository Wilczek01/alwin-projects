package com.codersteam.alwin.core.service.impl.issue.preparator;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.type.AddressType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tomasz Sliwinski
 */
public final class AddressPreparator {

    private AddressPreparator() {
    }

    public static Set<Address> prepareAddresses(final AidaCompanyDto aidaCompany) {
        final Address residenceAddress = prepareAddress(aidaCompany.getCity(), aidaCompany.getPostalCode(), aidaCompany.getStreet(), aidaCompany.getPrefix(),
                AddressType.RESIDENCE);

        final Address correspondenceAddress = prepareAddress(aidaCompany.getCorrespondenceCity(), aidaCompany.getCorrespondencePostalCode(),
                aidaCompany.getCorrespondenceStreet(), aidaCompany.getCorrespondencePrefix(), AddressType.CORRESPONDENCE);

        final Set<Address> addresses = new HashSet<>(2);
        addresses.add(correspondenceAddress);
        addresses.add(residenceAddress);
        return addresses;
    }

    private static Address prepareAddress(final String correspondenceCity, final String correspondencePostalCode, final String correspondenceStreet,
                                          final String correspondencePrefix, final AddressType addressType) {
        final Address address = new Address();
        address.setCity(correspondenceCity);
        address.setPostalCode(correspondencePostalCode);
        address.setStreetName(correspondenceStreet);
        address.setStreetPrefix(correspondencePrefix);
        address.setAddressType(addressType);
        address.setImportedFromAida(true);
        address.setImportedType(addressType);
        return address;
    }

}

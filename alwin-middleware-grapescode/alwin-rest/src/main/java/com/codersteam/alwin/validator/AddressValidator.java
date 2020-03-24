package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.customer.AddressDto;
import com.codersteam.alwin.core.api.service.customer.AddressService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AddressValidator {

    public static final String ADDRESS_FETCHED_FROM_AIDA_COULD_NOT_BE_UPDATED = "Nie można zaktualizować adresu, który został pobrany z AIDA";

    private AddressService addressService;

    public AddressValidator() {}

    @Inject
    public AddressValidator(final AddressService addressService) {
        this.addressService = addressService;
    }

    public void validate(AddressDto address) {
        final AddressDto existingAddress = addressService.findAddress(address.getId());

        if (existingAddress.isImportedFromAida()) {
            throw new AlwinValidationException(ADDRESS_FETCHED_FROM_AIDA_COULD_NOT_BE_UPDATED);
        }

    }
}

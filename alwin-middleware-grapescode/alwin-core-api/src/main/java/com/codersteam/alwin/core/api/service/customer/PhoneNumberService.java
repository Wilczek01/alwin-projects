package com.codersteam.alwin.core.api.service.customer;

import com.codersteam.alwin.core.api.model.customer.PhoneNumberDto;

import java.util.List;

/**
 * @author Adam Stepnowski
 */
public interface PhoneNumberService {

    /**
     * Pobiera listę sugerowanych telefonów dla podanej firmy i osób z nią powiązanych
     *
     * @param companyId - identyfikator firmy
     * @return lista sugerowanych telefonów dla podanej firmy i osób z nią powiązanych
     */
    List<PhoneNumberDto> findSuggestedPhoneNumbers(final long companyId);

}

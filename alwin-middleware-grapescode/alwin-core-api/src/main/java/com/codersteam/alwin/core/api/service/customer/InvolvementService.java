package com.codersteam.alwin.core.api.service.customer;

import javax.ejb.Local;

/**
 * @author Piotr Naroznik
 */
@Local
public interface InvolvementService {

    /**
     * Aktualizacja zaangażowania firmy
     *
     * @param companyId - identyfikator firmy
     */
    void updateCompanyInvolvement(final Long companyId);
}
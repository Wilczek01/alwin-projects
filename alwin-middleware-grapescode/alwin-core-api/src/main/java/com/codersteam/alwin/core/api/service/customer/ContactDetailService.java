package com.codersteam.alwin.core.api.service.customer;

import com.codersteam.alwin.core.api.model.customer.ContactDetailDto;

import java.util.List;

/**
 * @author Piotr Naroznik
 */
public interface ContactDetailService {

    /**
     * Pobiera listę kontaktów dla podanej firmy
     *
     * @param companyId - identyfikator firmy
     * @return lista kontaktów
     */
    List<ContactDetailDto> findAllContactDetailsForCompany(final long companyId);

    /**
     * Tworzy nowy kontakt dla podanej firmy
     *
     * @param companyId     - identyfikator firmy
     * @param contactDetail - kontakt do stworzenia
     */
    void createNewContactDetailForCompany(final long companyId, final ContactDetailDto contactDetail);

    /**
     * Pobiera listę kontaktów dla podanej osoby
     *
     * @param personId - identyfikator osoby
     * @return lista kontaktów
     */
    List<ContactDetailDto> findAllContactDetailsForPerson(final long personId);

    /**
     * Tworzy nowy kontakt dla podanej osoby
     *
     * @param personId     - identyfikator osoby
     * @param contactDetail - konakt do stworzenia
     */
    void createNewContactDetailForPerson(final long personId, final ContactDetailDto contactDetail);


    /**
     * Aktualizuje dane kontaktu
     *
     * @param contactDetail - kontakt do aktualizacji
     */
    void updateContactDetail(final ContactDetailDto contactDetail);
}

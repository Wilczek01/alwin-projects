package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.customer.PersonDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Serwis do obsługi osób uprawnionych firmy
 *
 * @author Piotr Naroznik
 */
@Local
public interface PersonService {

    /**
     * Oznaczanie/usuwanie oznacznia osoby jak osoba do kontaktu
     *
     * @param personId      - identyfikator osoby
     * @param companyId     - identyfikator firmy
     * @param contactPerson - czy oznaczyć jako osobę do kontaktu true/false
     */
    void setContactPerson(Long personId, Long companyId, boolean contactPerson);

    /**
     * Dodanie osoby uprawnionej firmy
     *
     * @param companyId - identyfikator firmy
     * @param personDto - dane osoby uprawionej
     */
    void createPerson(Long companyId, PersonDto personDto);


    /**
     * Pobieranie osób uprawnionych firmy
     *
     * @param companyId - identyfikator firmy
     * @return osoby uprawnione
     */
    List<PersonDto> getCompanyPersonsByCompanyId(long companyId);
}
package com.codersteam.alwin.core.api.service.customer;

import com.codersteam.alwin.core.api.model.customer.AddressDto;

import java.util.List;

/**
 * @author Piotr Naronznik
 */
public interface AddressService {

    /**
     * Pobiera listę adresów dla podanej firmy
     * @param companyId - identyfikator firmy
     * @return
     */
    List<AddressDto> findAllAddressesForCompany(final long companyId);

    /**
     * Tworzy nowy adres dla podanej firmy
     * @param companyId - identyfikator firmy
     * @param address - adres do stworzenia
     */
    void createNewAddressForCompany(final long companyId, final AddressDto address);

    /**
     * Pobiera listę adresów dla podanej osoby
     * @param personId - identyfikator osoby
     * @return - lista adresów
     */
    List<AddressDto> findAllAddressesForPerson(final long personId);

    /**
     * Tworzy nowy adres dla podanej osoby
     * @param personId - identyfikator osoby
     * @param address - adres do stworzenia
     */
    void createNewAddressForPerson(final long personId, final AddressDto address);


    /**
     * Aktualizuje dane adresowe
     * @param address - adres do aktualizacji
     */
    void updateAddress(final AddressDto address);

    /**
     * Pobiera adres po podanym identyfikatorze
     * @param addressId - identyfikator adresu
     * @return - szukany adres
     */
    AddressDto findAddress(final long addressId);

}

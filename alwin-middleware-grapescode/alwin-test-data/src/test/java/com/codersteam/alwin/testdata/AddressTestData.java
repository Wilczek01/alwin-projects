package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.customer.AddressDto;
import com.codersteam.alwin.core.api.model.customer.AddressTypeDto;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.type.AddressType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings("WeakerAccess")
public class AddressTestData {

    public static final Long NON_EXISTING_ADDRESS_ID = -1L;
    public static final Long ADDRESS_ID_1 = 1L;
    public static final String ADDRESS_CITY_1 = "Kraków";
    private static final String ADDRESS_REMARK_1 = "Test remark";
    private static final String ADDRESS_COUNTRY_1 = "Polska";
    public static final AddressType ADDRESS_TYPE_1 = AddressType.CORRESPONDENCE;
    private static final AddressTypeDto ADDRESS_TYPE_DTO_1 = AddressTypeDto.CORRESPONDENCE;
    private static final String ADDRESS_STREET_PREFIX_1 = "ul.";
    private static final String ADDRESS_STREET_NAME_1 = "Kalwaryjska";
    private static final String ADDRESS_FLAT_NUMBER_1 = "10";
    public static final String ADDRESS_POSTAL_CODE_1 = "02-567";
    private static final String ADDRESS_HOUSE_NUMBER_1 = "3";
    public static final boolean ADDRESS_IMPORTED_FROM_AIDA_1 = true;
    public static final AddressType ADDRESS_IMPORTED_TYPE_1 = ADDRESS_TYPE_1;

    public static final Long ADDRESS_ID_2 = 2L;
    public static final String ADDRESS_CITY_2 = "Warszawa";
    private static final String ADDRESS_REMARK_2 = "Test remark";
    private static final String ADDRESS_COUNTRY_2 = "Polska";
    private static final AddressType ADDRESS_TYPE_2 = AddressType.RESIDENCE;
    private static final AddressTypeDto ADDRESS_TYPE_DTO_2 = AddressTypeDto.RESIDENCE;
    private static final String ADDRESS_STREET_PREFIX_2 = "aleje";
    private static final String ADDRESS_STREET_NAME_2 = "Jerozolimskie";
    private static final String ADDRESS_FLAT_NUMBER_2 = "19";
    public static final String ADDRESS_POSTAL_CODE_2 = "01-234";
    private static final String ADDRESS_HOUSE_NUMBER_2 = "8";
    private static final boolean ADDRESS_IMPORTED_FROM_AIDA_2 = true;
    private static final AddressType ADDRESS_IMPORTED_TYPE_2 = ADDRESS_TYPE_2;

    public static final Long ADDRESS_ID_3 = 3L;
    private static final String ADDRESS_CITY_3 = "Warszawa";
    private static final String ADDRESS_REMARK_3 = "Test remark";
    private static final String ADDRESS_COUNTRY_3 = "Polska";
    private static final AddressType ADDRESS_TYPE_3 = AddressType.RESIDENCE;
    private static final AddressTypeDto ADDRESS_TYPE_DTO_3 = AddressTypeDto.RESIDENCE;
    private static final String ADDRESS_STREET_PREFIX_3 = "ul.";
    private static final String ADDRESS_STREET_NAME_3 = "Marszałkowska";
    private static final String ADDRESS_FLAT_NUMBER_3 = "19";
    private static final String ADDRESS_POSTAL_CODE_3 = "04-890";
    private static final String ADDRESS_HOUSE_NUMBER_3 = "7";
    private static final boolean ADDRESS_IMPORTED_FROM_AIDA_3 = false;
    private static final AddressType ADDRESS_IMPORTED_TYPE_3 = null;

    public static final Long ADDRESS_ID_4 = 4L;
    private static final String ADDRESS_CITY_4 = "Warszawa";
    private static final String ADDRESS_REMARK_4 = "Test remark";
    private static final String ADDRESS_COUNTRY_4 = "Polska";
    private static final AddressType ADDRESS_TYPE_4 = AddressType.RESIDENCE;
    private static final AddressTypeDto ADDRESS_TYPE_DTO_4 = AddressTypeDto.RESIDENCE;
    private static final String ADDRESS_STREET_PREFIX_4 = "ul.";
    private static final String ADDRESS_STREET_NAME_4 = "Broniewskiego";
    private static final String ADDRESS_FLAT_NUMBER_4 = "321";
    private static final String ADDRESS_POSTAL_CODE_4 = "01-756";
    private static final String ADDRESS_HOUSE_NUMBER_4 = "9";
    private static final boolean ADDRESS_IMPORTED_FROM_AIDA_4 = false;
    private static final AddressType ADDRESS_IMPORTED_TYPE_4 = null;

    public static final Long ADDRESS_ID_5 = 5L;
    private static final String ADDRESS_CITY_5 = "Warszawa";
    private static final String ADDRESS_REMARK_5 = "Test remark";
    private static final String ADDRESS_COUNTRY_5 = "Polska";
    private static final AddressType ADDRESS_TYPE_5 = AddressType.RESIDENCE;
    private static final AddressTypeDto ADDRESS_TYPE_DTO_5 = AddressTypeDto.RESIDENCE;
    private static final String ADDRESS_STREET_PREFIX_5 = "ul.";
    private static final String ADDRESS_STREET_NAME_5 = "Kochanowskiego";
    private static final String ADDRESS_FLAT_NUMBER_5 = null;
    private static final String ADDRESS_POSTAL_CODE_5 = "01-223";
    private static final String ADDRESS_HOUSE_NUMBER_5 = "12";
    private static final boolean ADDRESS_IMPORTED_FROM_AIDA_5 = false;
    private static final AddressType ADDRESS_IMPORTED_TYPE_5 = null;

    private static final Long ADDRESS_ID_100 = 100L;

    public static Address address1() {
        return address(ADDRESS_ID_1, ADDRESS_HOUSE_NUMBER_1, ADDRESS_FLAT_NUMBER_1, ADDRESS_POSTAL_CODE_1, ADDRESS_CITY_1, ADDRESS_COUNTRY_1, ADDRESS_TYPE_1,
                ADDRESS_REMARK_1, ADDRESS_STREET_NAME_1, ADDRESS_STREET_PREFIX_1, ADDRESS_IMPORTED_FROM_AIDA_1, ADDRESS_IMPORTED_TYPE_1);
    }

    public static Address address2() {
        return address(ADDRESS_ID_2, ADDRESS_HOUSE_NUMBER_2, ADDRESS_FLAT_NUMBER_2, ADDRESS_POSTAL_CODE_2, ADDRESS_CITY_2, ADDRESS_COUNTRY_2, ADDRESS_TYPE_2,
                ADDRESS_REMARK_2, ADDRESS_STREET_NAME_2, ADDRESS_STREET_PREFIX_2, ADDRESS_IMPORTED_FROM_AIDA_2, ADDRESS_IMPORTED_TYPE_2);
    }

    public static Address address3() {
        return address(ADDRESS_ID_3, ADDRESS_HOUSE_NUMBER_3, ADDRESS_FLAT_NUMBER_3, ADDRESS_POSTAL_CODE_3, ADDRESS_CITY_3, ADDRESS_COUNTRY_3, ADDRESS_TYPE_3,
                ADDRESS_REMARK_3, ADDRESS_STREET_NAME_3, ADDRESS_STREET_PREFIX_3, ADDRESS_IMPORTED_FROM_AIDA_3, ADDRESS_IMPORTED_TYPE_3);
    }

    public static Address address4() {
        return address(ADDRESS_ID_4, ADDRESS_HOUSE_NUMBER_4, ADDRESS_FLAT_NUMBER_4, ADDRESS_POSTAL_CODE_4, ADDRESS_CITY_4, ADDRESS_COUNTRY_4, ADDRESS_TYPE_4,
                ADDRESS_REMARK_4, ADDRESS_STREET_NAME_4, ADDRESS_STREET_PREFIX_4, ADDRESS_IMPORTED_FROM_AIDA_4, ADDRESS_IMPORTED_TYPE_4);
    }

    public static Address address5() {
        return address(ADDRESS_ID_5, ADDRESS_HOUSE_NUMBER_5, ADDRESS_FLAT_NUMBER_5, ADDRESS_POSTAL_CODE_5, ADDRESS_CITY_5, ADDRESS_COUNTRY_5, ADDRESS_TYPE_5,
                ADDRESS_REMARK_5, ADDRESS_STREET_NAME_5, ADDRESS_STREET_PREFIX_5, ADDRESS_IMPORTED_FROM_AIDA_5, ADDRESS_IMPORTED_TYPE_5);
    }

    public static Address addressToUpdate() {
        return address(ADDRESS_ID_2, ADDRESS_HOUSE_NUMBER_1, ADDRESS_FLAT_NUMBER_1, ADDRESS_POSTAL_CODE_1, ADDRESS_CITY_1, ADDRESS_COUNTRY_1, ADDRESS_TYPE_1,
                ADDRESS_REMARK_1, ADDRESS_STREET_NAME_1, ADDRESS_STREET_PREFIX_1, ADDRESS_IMPORTED_FROM_AIDA_1, ADDRESS_IMPORTED_TYPE_1);
    }

    public static Address addressToCreate() {
        return address(ADDRESS_ID_100, ADDRESS_HOUSE_NUMBER_1, ADDRESS_FLAT_NUMBER_1, ADDRESS_POSTAL_CODE_1, ADDRESS_CITY_1, ADDRESS_COUNTRY_1, ADDRESS_TYPE_1,
                ADDRESS_REMARK_1, ADDRESS_STREET_NAME_1, ADDRESS_STREET_PREFIX_1, ADDRESS_IMPORTED_FROM_AIDA_1, ADDRESS_IMPORTED_TYPE_1);
    }

    public static AddressDto addressDto1() {
        return addressDto(ADDRESS_ID_1, ADDRESS_HOUSE_NUMBER_1, ADDRESS_FLAT_NUMBER_1, ADDRESS_POSTAL_CODE_1, ADDRESS_CITY_1, ADDRESS_COUNTRY_1,
                ADDRESS_TYPE_DTO_1, ADDRESS_REMARK_1, ADDRESS_STREET_NAME_1, ADDRESS_STREET_PREFIX_1, ADDRESS_IMPORTED_FROM_AIDA_1);
    }

    public static AddressDto addressDto2() {
        return addressDto(ADDRESS_ID_2, ADDRESS_HOUSE_NUMBER_2, ADDRESS_FLAT_NUMBER_2, ADDRESS_POSTAL_CODE_2, ADDRESS_CITY_2, ADDRESS_COUNTRY_2,
                ADDRESS_TYPE_DTO_2, ADDRESS_REMARK_2, ADDRESS_STREET_NAME_2, ADDRESS_STREET_PREFIX_2, ADDRESS_IMPORTED_FROM_AIDA_2);
    }

    public static AddressDto addressDto3() {
        return addressDto(ADDRESS_ID_3, ADDRESS_HOUSE_NUMBER_3, ADDRESS_FLAT_NUMBER_3, ADDRESS_POSTAL_CODE_3, ADDRESS_CITY_3, ADDRESS_COUNTRY_3,
                ADDRESS_TYPE_DTO_3, ADDRESS_REMARK_3, ADDRESS_STREET_NAME_3, ADDRESS_STREET_PREFIX_3, ADDRESS_IMPORTED_FROM_AIDA_3);
    }

    public static AddressDto addressDto4() {
        return addressDto(ADDRESS_ID_4, ADDRESS_HOUSE_NUMBER_4, ADDRESS_FLAT_NUMBER_4, ADDRESS_POSTAL_CODE_4, ADDRESS_CITY_4, ADDRESS_COUNTRY_4,
                ADDRESS_TYPE_DTO_4, ADDRESS_REMARK_4, ADDRESS_STREET_NAME_4, ADDRESS_STREET_PREFIX_4, ADDRESS_IMPORTED_FROM_AIDA_4);
    }

    public static AddressDto addressDto5() {
        return addressDto(ADDRESS_ID_5, ADDRESS_HOUSE_NUMBER_5, ADDRESS_FLAT_NUMBER_5, ADDRESS_POSTAL_CODE_5, ADDRESS_CITY_5, ADDRESS_COUNTRY_5,
                ADDRESS_TYPE_DTO_5, ADDRESS_REMARK_5, ADDRESS_STREET_NAME_5, ADDRESS_STREET_PREFIX_5, ADDRESS_IMPORTED_FROM_AIDA_5);
    }

    public static Set<Address> correspondenceAndResidenceAddresses() {
        return new HashSet<>(asList(address1(), address2()));
    }

    public static Set<Address> onlyResidenceAddress() {
        return new HashSet<>(Collections.singletonList(address2()));
    }

    public static Set<Address> onlyOtherAddress() {
        final Address address = address2();
        address.setAddressType(AddressType.OTHER);
        address.setImportedType(AddressType.OTHER);
        return new HashSet<>(Collections.singletonList(address));
    }

    public static Address address(final Long id, final String houseNumber, final String flatNumber, final String postalCode, final String city,
                                  final String country, final AddressType addressType, final String remark, final String streetName,
                                  final String streetPrefix, final boolean importedFromAida, final AddressType importedType) {
        final Address address = new Address();
        address.setId(id);
        address.setCity(city);
        address.setRemark(remark);
        address.setCountry(country);
        address.setFlatNumber(flatNumber);
        address.setStreetName(streetName);
        address.setPostalCode(postalCode);
        address.setHouseNumber(houseNumber);
        address.setAddressType(addressType);
        address.setStreetPrefix(streetPrefix);
        address.setImportedFromAida(importedFromAida);
        address.setImportedType(importedType);
        return address;
    }

    private static AddressDto addressDto(final Long id, final String houseNumber, final String flatNumber, final String postalCode, final String city,
                                         final String country, final AddressTypeDto addressType, final String remark, final String streetName,
                                         final String streetPrefix, final boolean importedFromAida) {
        final AddressDto addressDto = new AddressDto();
        addressDto.setId(id);
        addressDto.setCity(city);
        addressDto.setRemark(remark);
        addressDto.setCountry(country);
        addressDto.setFlatNumber(flatNumber);
        addressDto.setStreetName(streetName);
        addressDto.setPostalCode(postalCode);
        addressDto.setHouseNumber(houseNumber);
        addressDto.setAddressType(addressType);
        addressDto.setStreetPrefix(streetPrefix);
        addressDto.setImportedFromAida(importedFromAida);
        return addressDto;
    }

}

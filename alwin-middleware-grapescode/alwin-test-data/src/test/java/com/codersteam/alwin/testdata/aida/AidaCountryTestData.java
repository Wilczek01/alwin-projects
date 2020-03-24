package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.CountryDto;

/**
 * @author Michal Horowic
 */
@SuppressWarnings("WeakerAccess")
public class AidaCountryTestData {

    private static final Long ID = 1L;
    private static final String LONG_NAME = "Polska";
    public static final CountryDto TEST_COUNTRY_DTO = countryDto();

    private static CountryDto countryDto() {
        final CountryDto country = new CountryDto();
        country.setId(ID);
        country.setLongName(LONG_NAME);
        return country;
    }
}

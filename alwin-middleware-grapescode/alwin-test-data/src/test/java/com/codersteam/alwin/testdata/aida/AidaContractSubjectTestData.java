package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.ContractSubjectDto;

import java.math.BigDecimal;

import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_ID_1;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public final class AidaContractSubjectTestData {

    public static final Long SUBJECT_ID = 1090L;
    private static final String SUBJECT_INDEX = "1";
    private static final String NAME = "Samochód osobowy Opel Insignia";
    private static final String DESCRIPTION = "BiTurbo 195KM wersja COSMO";
    private static final BigDecimal START_VALUE = new BigDecimal("49593.50");
    private static final String VAT_RATE = "23";
    private static final Integer YEAR_OF_PRODUCTION = 2012;
    private static final String SERIAL_NUMBER = "W0LGT6EN5C1097092";
    private static final String REGISTRATION_NUMBER = "DW 662VY";

    public static final ContractSubjectDto TEST_CONTRACT_SUBJECT_DTO_1090 = contractSubjectDto();

    private AidaContractSubjectTestData() {
    }

    private static ContractSubjectDto contractSubjectDto() {
        final ContractSubjectDto contractSubjectDto = new ContractSubjectDto();
        contractSubjectDto.setSubjectId(SUBJECT_ID);
        contractSubjectDto.setContractId(CONTRACT_ID_1);
        contractSubjectDto.setIndex(SUBJECT_INDEX);
        contractSubjectDto.setName(NAME);
        contractSubjectDto.setDescription(DESCRIPTION);
        contractSubjectDto.setStartValue(START_VALUE);
        contractSubjectDto.setVatRate(VAT_RATE);
        contractSubjectDto.setYearOfProduction(YEAR_OF_PRODUCTION);
        contractSubjectDto.setSerialNumber(SERIAL_NUMBER);
        contractSubjectDto.setRegistrationNumber(REGISTRATION_NUMBER);
        contractSubjectDto.setSubjectSupplier(aidaCompanyDto10());

        return contractSubjectDto;
    }
}

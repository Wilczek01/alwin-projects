package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.aida.core.api.model.AidaContractWithSubjectsAndSchedulesDto;
import com.codersteam.aida.core.api.service.ContractService;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.simpleAidaContractDto1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.simpleAidaContractDto2;
import static com.codersteam.alwin.testdata.aida.AidaContractWithSubjectsAndSchedulesTestData.contractWithSubjectsAndSchedulesDto1;
import static com.codersteam.alwin.testdata.aida.AidaContractWithSubjectsAndSchedulesTestData.contractWithSubjectsAndSchedulesDto2;
import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public class ContractServiceMock implements ContractService {

    /**
     * Kontrakty w zewnętrznym systemie dostępne po numerze kontraktu
     */
    public static final Map<String, AidaContractDto> CONTRACTS_BY_CONTRACT_NO = new HashMap<>();

    /**
     * Czy przedmiot umowy ma zainstalowany GPS po numerze umowy
     */
    public static final Map<String, Boolean> GPS_INSTALLED_BY_CONTRACT_NO = new HashMap<>();

    /**
     * Kontrakty z przedmiotami i harmonogramami w zewnętrznym systemie dostępne po numerze kontraktu
     */
    public static final Map<String, AidaContractWithSubjectsAndSchedulesDto> CONTRACTS_WITH_SUBJECTS_AND_SCHEDULES_BY_CONTRACT_NO = new HashMap<>();

    public static final Map<String, Long> CONTRACT_TO_EXT_COMPANY_ID = new HashMap<>();

    @Override
    public AidaContractDto findActiveContractByContractNo(final String contractNo) {
        return CONTRACTS_BY_CONTRACT_NO.get(contractNo);
    }

    @Override
    public List<AidaContractWithSubjectsAndSchedulesDto> findContractsWithSubjectsAndSchedulesByCompanyId(final Long companyId) {
        if (COMPANY_ID_10.equals(companyId)) {
            return asList(contractWithSubjectsAndSchedulesDto1(), contractWithSubjectsAndSchedulesDto2());
        }
        return Collections.emptyList();
    }

    @Override
    public AidaContractWithSubjectsAndSchedulesDto findContractWithSubjectAndSchedulesByContractNo(final String contractNo) {
        return CONTRACTS_WITH_SUBJECTS_AND_SCHEDULES_BY_CONTRACT_NO.get(contractNo);
    }

    @Override
    public List<AidaContractDto> findContractsByCompanyId(final Long companyId) {
        if (COMPANY_ID_10.equals(companyId)) {
            return asList(simpleAidaContractDto1(), simpleAidaContractDto2());
        }
        return Collections.emptyList();
    }

    @Override
    public Long findCompanyIdByContractNo(final String contractNo) {
        return CONTRACT_TO_EXT_COMPANY_ID.get(contractNo);
    }

    @Override
    public boolean checkIfContractSubjectHasInstalledGps(final String contractNo) {
        return BooleanUtils.isTrue(GPS_INSTALLED_BY_CONTRACT_NO.get(contractNo));
    }

    public static void reset() {
        CONTRACTS_BY_CONTRACT_NO.clear();
        CONTRACTS_WITH_SUBJECTS_AND_SCHEDULES_BY_CONTRACT_NO.clear();
        GPS_INSTALLED_BY_CONTRACT_NO.clear();
        CONTRACT_TO_EXT_COMPANY_ID.clear();
    }
}

package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.model.ContractSubjectDto;
import com.codersteam.aida.core.api.service.ContractSubjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Horowic
 */
public class ContractSubjectServiceMock implements ContractSubjectService {

    /**
     * Kontrakty w zewnętrzym systemie dostępne po numerze kontraktu
     */
    public static Map<String, List<ContractSubjectDto>> SUBJECTS_BY_CONTRACT_NO = new HashMap<>();

    public static void reset() {
        SUBJECTS_BY_CONTRACT_NO = new HashMap<>();
    }

    @Override
    public List<ContractSubjectDto> findContractSubjectByContractNo(final String contractNo) {
        return SUBJECTS_BY_CONTRACT_NO.get(contractNo);
    }
}

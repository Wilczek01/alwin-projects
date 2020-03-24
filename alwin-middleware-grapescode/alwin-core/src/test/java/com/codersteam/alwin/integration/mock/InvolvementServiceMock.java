package com.codersteam.alwin.integration.mock;


import com.codersteam.aida.core.api.model.CustomerInvolvementDto;
import com.codersteam.aida.core.api.service.InvolvementService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvolvementServiceMock implements InvolvementService {

    /**
     * Zangażowanie firmy po zewnętrznym identyfikatorze firmy
     */
    public static Map<Long, BigDecimal> COMPANIES_INVOLVEMENT = new HashMap<>();

    /**
     * Zangażowanie firmy po zewnętrznym identyfikatorze firmy
     */
    public static Map<Long, List<CustomerInvolvementDto>> COMPANIES_INVOLVEMENTS = new HashMap<>();

    @Override
    public BigDecimal calculateCompanyInvolvement(final Long companyId) {
        return COMPANIES_INVOLVEMENT.get(companyId);
    }

    @Override
    public List<CustomerInvolvementDto> getInvolvements(final Long companyId) {
        return COMPANIES_INVOLVEMENTS.get(companyId);
    }

    public static void reset() {
        COMPANIES_INVOLVEMENT = new HashMap<>();
        COMPANIES_INVOLVEMENTS = new HashMap<>();
    }
}
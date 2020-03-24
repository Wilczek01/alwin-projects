package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.model.ExcessPaymentDto;
import com.codersteam.aida.core.api.service.ExcessPaymentService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Horowic
 */
public class ExcessPaymentServiceMock implements ExcessPaymentService {

    /**
     * Nadpłaty klientów
     */
    public static Map<Long, List<ExcessPaymentDto>> EXCESS_PAYMENTS = new HashMap<>();

    public static void reset() {
        EXCESS_PAYMENTS = new HashMap<>();
    }

    @Override
    public List<ExcessPaymentDto> findCompanyExcessPayments(final Long companyId) {
        return EXCESS_PAYMENTS.getOrDefault(companyId, Collections.emptyList());
    }
}

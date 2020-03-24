package com.codersteam.alwin.integration.mock;


import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.search.criteria.AidaCompanySearchCriteria;
import com.codersteam.aida.core.api.service.CompanyService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.AIDA_COMPANY_2_COMPANIES_NAME_PREFIX;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.EXCEPTION_CAUSING_AIDA_COMPANY_ID;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto11;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * @author Michal Horowic
 */
@SuppressWarnings("ALL")
public class CompanyServiceMock implements CompanyService {

    /**
     * Klienci w zewnętrzym systemie dostępni po identyfikatorze firmy
     */
    public static Map<Long, AidaCompanyDto> COMPANIES_BY_COMPANY_ID = new HashMap<>();

    /**
     * Klienci w zewnętrznym systemie dostępni po dacie
     */
    public static Map<Date, List<AidaCompanyDto>> COMPANIES_BY_DATE_FROM = new HashMap<>();

    /**
     * Wpłaty klienta w zewnętrzym systemie po identyfikatorze firmy
     */
    public static Map<Long, BigDecimal> COMPANIES_SUM_OF_PAYMENTS = new HashMap<>();

    /**
     * Wpłaty klienta w zewnętrzym systemie podzielone na daty po identyfikatorze firmy
     */
    public static Map<Long, Map<Date, BigDecimal>> PAYMENTS_BY_DATE = new HashMap<>();

    @Override
    public AidaCompanyDto findCompanyByCompanyId(Long companyId) {
        if (EXCEPTION_CAUSING_AIDA_COMPANY_ID.equals(companyId)) {
            throw new RuntimeException(format("Exception thrown when finding company %d", EXCEPTION_CAUSING_AIDA_COMPANY_ID));
        }
        return COMPANIES_BY_COMPANY_ID.get(companyId);
    }

    @Override
    public List<AidaCompanyDto> findModifiedCompaniesExclusiveFromDate(final Date fromDate, final Date toDate) {
        return COMPANIES_BY_DATE_FROM.get(fromDate);
    }

    @Override
    public List<AidaCompanyDto> findCompanies(AidaCompanySearchCriteria searchCriteria) {
        if (AIDA_COMPANY_2_COMPANIES_NAME_PREFIX.equalsIgnoreCase(searchCriteria.getName()) && searchCriteria.getFirstResult() == 0 && searchCriteria
                .getMaxResults() == 1) {
            return asList(aidaCompanyDto10());
        }
        if (AIDA_COMPANY_2_COMPANIES_NAME_PREFIX.equalsIgnoreCase(searchCriteria.getName()) && searchCriteria.getFirstResult() == 1 && searchCriteria
                .getMaxResults() == 1) {
            return asList(aidaCompanyDto11());
        }
        return emptyList();
    }

    @Override
    public long findCompaniesCount(AidaCompanySearchCriteria searchCriteria) {
        if (AIDA_COMPANY_2_COMPANIES_NAME_PREFIX.equalsIgnoreCase(searchCriteria.getName())) {
            return 2L;
        }
        return 0L;
    }

    @Override
    public Map<Date, BigDecimal> findCompanyPayments(final Long companyId, final Date fromDate, final Date endDate, final String currency) {
        return PAYMENTS_BY_DATE.get(companyId);
    }

    public static void reset() {
        COMPANIES_BY_COMPANY_ID = new HashMap<>();
        COMPANIES_BY_DATE_FROM = new HashMap<>();
        COMPANIES_SUM_OF_PAYMENTS = new HashMap<>();
        PAYMENTS_BY_DATE = new HashMap<>();
    }
}

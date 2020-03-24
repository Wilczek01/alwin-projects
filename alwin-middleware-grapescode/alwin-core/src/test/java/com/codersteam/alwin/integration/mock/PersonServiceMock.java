package com.codersteam.alwin.integration.mock;


import com.codersteam.aida.core.api.model.AidaPersonDto;
import com.codersteam.aida.core.api.service.PersonService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Horowic
 */
public class PersonServiceMock implements PersonService {

    /**
     * Osoby w zewnętrzym systemie dostępni po identyfikatorze firmy
     */
    public final static Map<Long, List<AidaPersonDto>> PERSONS_BY_COMPANY_ID = new HashMap<>();

    /**
     * Osoby w zewnętrznym systemie dostępni po dacie
     */
    public final static Map<Date, List<AidaPersonDto>> PERSONS_BY_DATE_FROM = new HashMap<>();

    @Override
    public List<AidaPersonDto> findPersonByCompanyId(final Long companyId) {
        return PERSONS_BY_COMPANY_ID.get(companyId);
    }

    @Override
    public List<AidaPersonDto> findModifiedPersonsExclusiveFromDate(final Date fromDate, final Date toDate) {
        return PERSONS_BY_DATE_FROM.get(fromDate);
    }
}

package com.codersteam.alwin.integration.mock;


import com.codersteam.aida.core.api.model.CompanySegment;
import com.codersteam.aida.core.api.service.SegmentService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings("ALL")
public class SegmentServiceMock implements SegmentService {

    /**
     * Segment firmy po zewnętrznym identyfikatorze firmy
     */
    public static Map<Long, CompanySegment> COMPANIES_SEGMENT = new HashMap<>();

    @Override
    public CompanySegment findCompanySegment(final Long companyId) {
        return COMPANIES_SEGMENT.get(companyId);
    }

    public static void reset(){
        COMPANIES_SEGMENT = new HashMap<>();
    }
}
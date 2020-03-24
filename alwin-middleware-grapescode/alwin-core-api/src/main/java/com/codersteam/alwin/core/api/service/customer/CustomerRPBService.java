package com.codersteam.alwin.core.api.service.customer;

import javax.ejb.Local;
import java.math.BigDecimal;

/**
 * Serwis do wyznaczania RPB
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface CustomerRPBService {

    /**
     * Obliczenie RPB firmy
     *
     * @param extCompanyId - identyfikator firmy w systemie AIDA
     * @return wartość RPB
     */
    BigDecimal calculateCompanyRPB(Long extCompanyId);
}

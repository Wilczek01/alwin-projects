package com.codersteam.alwin.validator;

import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Logika walidująca poprawność danych dla czynności windykacyjnych
 *
 * @author Michal Horowic
 */
@Stateless
public class ContractValidator {

    private static final String MISSING_CONTRACT_NO_MESSAGE = "Numer umowy jest wymagany";

    /**
     * Sprawdza czy numer umowy został przekazany
     *
     * @param contractNo - numer umowy
     */
    public void validateContractNo(final String contractNo) {
        if (isEmpty(contractNo)) {
            throw new AlwinValidationException(MISSING_CONTRACT_NO_MESSAGE);
        }
    }


}

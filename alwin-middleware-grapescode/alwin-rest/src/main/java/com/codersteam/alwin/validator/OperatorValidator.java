package com.codersteam.alwin.validator;

import com.codersteam.alwin.auth.model.ChangePasswordRequest;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Logika walidująca poprawność danych dla operatorów
 *
 * @author Michal Horowic
 */
@Stateless
public class OperatorValidator {

    private static final String NOT_FOUND_MESSAGE = "Operator o podanym identyfikatorze nie istnieje";
    private static final String EMPTY_PASSWORD_MESSAGE = "Hasło nie może być puste";
    private static final String NOT_FORCED_TO_CHANGE_PASSWORD_MESSAGE = "Nie można zmienić hasła ponieważ nie było ono resetowane";

    private OperatorService operatorService;

    public OperatorValidator() {
    }

    @Inject
    public OperatorValidator(final OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    /**
     * Sprawdza czy istnieje operator o podanym identyfikatorze
     *
     * @param operatorId - identyfikator operatora
     */
    public void checkIfOperatorExists(final Long operatorId) {
        if (operatorId == null || operatorService.findOperatorById(operatorId) == null) {
            throw new AlwinValidationException(NOT_FOUND_MESSAGE);
        }
    }

    public void validateChangePassword(final ChangePasswordRequest passwordRequest) {
        if (passwordRequest == null || isBlank(passwordRequest.getPassword())) {
            throw new AlwinValidationException(EMPTY_PASSWORD_MESSAGE);
        }
    }

    public void validateOperatorForcedToChangePassword(final long operatorId) {
        final OperatorDto operator = operatorService.findOperatorById(operatorId);
        if (!operator.isForceUpdatePassword()) {
            throw new AlwinValidationException(NOT_FORCED_TO_CHANGE_PASSWORD_MESSAGE);
        }
    }
}

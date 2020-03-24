package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;

/**
 * Walidacja komunikatu aktualizacji konfiguracji typu zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Stateless
public class IssueConfigurationValidator {

    protected static final String POSITIVE_OR_ZERO_MIN_BALANCE_START_MESSAGE = "Minimalny próg zadłużenia nie może być większy lub równy 0";
    protected static final String NEGATIVE_OR_ZER0_DPD_START_MESSAGE = "DPD początkowe nie może być wartością zerową lub ujemną";
    protected static final String NEGATIVE_OR_ZER0_DURATION_MESSAGE = "Czas trwania nie może być wartością zerową lub ujemną";

    public void validate(final IssueTypeConfigurationDto issueTypeConfigurationDto) {
        if (issueTypeConfigurationDto.getMinBalanceStart() == null || issueTypeConfigurationDto.getMinBalanceStart().signum() >= 0) {
            throw new AlwinValidationException(POSITIVE_OR_ZERO_MIN_BALANCE_START_MESSAGE);
        }
        if (issueTypeConfigurationDto.getDpdStart() == null || issueTypeConfigurationDto.getDpdStart() <= 0) {
            throw new AlwinValidationException(NEGATIVE_OR_ZER0_DPD_START_MESSAGE);
        }
        if (issueTypeConfigurationDto.getDuration() == null || issueTypeConfigurationDto.getDuration() <= 0) {
            throw new AlwinValidationException(NEGATIVE_OR_ZER0_DURATION_MESSAGE);
        }
    }
}

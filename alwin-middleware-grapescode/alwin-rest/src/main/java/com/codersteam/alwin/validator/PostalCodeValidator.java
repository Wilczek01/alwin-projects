package com.codersteam.alwin.validator;

import com.codersteam.alwin.common.search.util.PostalCodeUtils;
import com.codersteam.alwin.core.api.model.postal.PostalCodeInputDto;
import com.codersteam.alwin.core.api.postalcode.PostalCodeService;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Logika walidująca poprawność danych dla masek kodów pocztowych
 *
 * @author Michal Horowic
 */
@Stateless
public class PostalCodeValidator {

    private static final String MASK_ALREADY_EXISTS = "Podana maska [%s] jest już przypisana do jednego z operatorów.";
    private static final String INVALID_MASK_FORMAT = "Nieporawny format maski [%s].";
    private static final String MISSING_MASK_DETAILS = "Nie podano szczegółów maski.";
    private static final String MISSING_OPERATOR = "Nie podano operatora dla maski.";
    private static final String MISSING_MASK = "Nie podano maski dla operatora.";
    private static final String OPERATOR_NOT_EXISTS = "Operator o podanym identyfikatorze [%s] nie istnieje.";
    private static final String MASK_NOT_EXISTS = "Maska o podanym identyfikatorze [%s] nie istnieje.";

    private PostalCodeService postalCodeService;
    private OperatorService operatorService;

    public PostalCodeValidator() {
    }

    @Inject
    public PostalCodeValidator(final PostalCodeService postalCodeService, final OperatorService operatorService) {
        this.postalCodeService = postalCodeService;
        this.operatorService = operatorService;
    }

    /**
     * Sprawdza czy maska kodu pocztowego o podanym identyfikatorze istnieje i ma poprawny format
     *
     * @param postalCodeId - identyfikator maski kodu pocztowego
     * @param postalCode   - szczegóły maski dla operatora
     * @throws AlwinValidationException - w przypadku błędu walidacji
     */
    public void validate(final long postalCodeId, final PostalCodeInputDto postalCode) throws AlwinValidationException {
        validate(postalCode);
        validate(postalCodeId);
    }

    /**
     * Sprawdza czy maska kodu pocztowego o podanym identyfikatorze istnieje
     *
     * @param postalCodeId - identyfikator maski kodu pocztowego
     * @throws AlwinValidationException - w przypadku błędu walidacji
     */
    public void validate(final long postalCodeId) throws AlwinValidationException {
        if (!postalCodeService.checkIfPostalCodeExists(postalCodeId)) {
            throw new AlwinValidationException(String.format(MASK_NOT_EXISTS, postalCodeId));
        }
    }

    /**
     * Sprawdza czy maska kodu pocztowego ma poprawny format
     *
     * @param postalCode - szczegóły maski dla operatora
     * @throws AlwinValidationException - w przypadku błędu walidacji
     */
    public void validate(final PostalCodeInputDto postalCode) throws AlwinValidationException {
        if (postalCode == null) {
            throw new AlwinValidationException(MISSING_MASK_DETAILS);
        }

        if (postalCode.getOperator() == null || postalCode.getOperator().getId() == null) {
            throw new AlwinValidationException(MISSING_OPERATOR);
        }
        if (!operatorService.checkIfOperatorExists(postalCode.getOperator().getId())) {
            throw new AlwinValidationException(String.format(OPERATOR_NOT_EXISTS, postalCode.getOperator().getId()));
        }
        validateInputMask(postalCode.getMask());
    }

    /**
     * Sprawdza czy maska kodu pocztowego została podana, ma poprawny format i jest unikalna
     *
     * @param mask - maska kodu pocztowego
     * @throws AlwinValidationException - w przypadku błędu walidacji
     */
    private void validateInputMask(final String mask) {
        if (isEmpty(mask)) {
            throw new AlwinValidationException(MISSING_MASK);
        }
        if (postalCodeService.checkIfPostalCodeMaskExists(mask)) {
            throw new AlwinValidationException(String.format(MASK_ALREADY_EXISTS, mask));
        }
        validate(mask);
    }

    /**
     * Sprawdza czy maska kodu pocztowego ma poprawny format
     *
     * @param mask - maska kodu pocztowego
     * @throws AlwinValidationException - w przypadku błędu walidacji
     */
    public void validate(final String mask) throws AlwinValidationException {
        if (isNotEmpty(mask) && PostalCodeUtils.isPostalCodeMaskInvalid(mask)) {
            throw new AlwinValidationException(String.format(INVALID_MASK_FORMAT, mask));
        }
    }

}

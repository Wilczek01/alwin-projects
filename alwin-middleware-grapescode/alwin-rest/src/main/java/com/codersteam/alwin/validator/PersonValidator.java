package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.customer.DocTypeDto;
import com.codersteam.alwin.core.api.model.customer.MaritalStatusDto;
import com.codersteam.alwin.core.api.model.customer.PersonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.length;

/**
 * Logika walidująca poprawność danych osoby uprawionej
 *
 * @author Piotr Naroznik
 */
@Stateless
public class PersonValidator {

    protected static final String LAST_NAME = "lastName";
    protected static final String LAST_NAME_IS_MISSING_MESSAGE = "Podaj nazwisko, bo pole nie może być puste.";
    protected static final String LAST_NAME_IS_TOO_LONG_MESSAGE = "Podane nazwisko jest za długie.";

    protected static final String FIRST_NAME = "firstName";
    protected static final String FIRST_NAME_IS_MISSING_MESSAGE = "Podaj imię, bo pole nie może być puste.";
    protected static final String FIRST_NAME_IS_TOO_LONG_MESSAGE = "Podane imie jest za długie.";

    protected static final String ID_DOC_TYPE = "idDocType";
    protected static final String ID_DOC_TYPE_WRONG_KEY_MESSAGE = "Niepoprawna wartość typu dokumentu tożsamości.";

    protected static final String MARITAL_STATUS = "maritalStatus";
    protected static final String MARITAL_STATUS_WRONG_KEY_MESSAGE = "Niepoprawna wartość stanu cywilnego osoby.";

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PersonValidator() {
        // For deployment
    }

    public Map<String, String> validatePerson(final PersonDto person) {
        removeIds(person);

        final Map<String, String> validation = new HashMap<>();
        validateFirstName(person.getFirstName(), validation);
        validateLastName(person.getLastName(), validation);
        validateMaritalStatus(person.getMaritalStatus(), validation);
        validateIdDocType(person.getIdDocType(), validation);

        return validation;
    }

    private void validateIdDocType(final DocTypeDto idDocType, final Map<String, String> validation) {
        if (idDocType == null) {
            return;
        }
        try {
            DocTypeDto.valueOf(idDocType.getKey());
        } catch (final IllegalArgumentException e) {
            LOG.error(e.getMessage(), e);
            validation.put(ID_DOC_TYPE, ID_DOC_TYPE_WRONG_KEY_MESSAGE);
        }
    }

    private void validateMaritalStatus(final MaritalStatusDto maritalStatus, final Map<String, String> validation) {
        if (maritalStatus == null) {
            return;
        }
        try {
            MaritalStatusDto.valueOf(maritalStatus.getKey());
        } catch (final IllegalArgumentException e) {
            LOG.error(e.getMessage(), e);
            validation.put(MARITAL_STATUS, MARITAL_STATUS_WRONG_KEY_MESSAGE);
        }
    }


    private void validateLastName(final String lastName, final Map<String, String> validation) {
        if (isBlank(lastName)) {
            validation.put(LAST_NAME, LAST_NAME_IS_MISSING_MESSAGE);
            return;
        }
        if (length(lastName) > 100) {
            validation.put(LAST_NAME, LAST_NAME_IS_TOO_LONG_MESSAGE);
        }
    }

    private void validateFirstName(final String firstName, final Map<String, String> validation) {
        if (isBlank(firstName)) {
            validation.put(FIRST_NAME, FIRST_NAME_IS_MISSING_MESSAGE);
            return;
        }
        if (length(firstName) > 100) {
            validation.put(FIRST_NAME, FIRST_NAME_IS_TOO_LONG_MESSAGE);
        }
    }

    private void removeIds(final PersonDto person) {
        person.setId(null);
        person.setPersonId(null);
    }
}

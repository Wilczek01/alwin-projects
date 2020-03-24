package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.operator.EditableOperatorDto;
import com.codersteam.alwin.core.api.model.user.EditableUserDto;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.core.api.service.user.UserService;
import com.codersteam.alwin.exception.AlwinValidationException;
import org.apache.commons.collections.CollectionUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Logika walidująca poprawność danych dla użytkowników
 *
 * @author Michal Horowic
 */
@Stateless
public class UserValidator {

    private static final String MESSED_UP_MESSAGE = "Niezgadzające się identyfikatory użytkowników";
    private static final String NOT_FOUND_MESSAGE = "Użytkownik o podanym identyfikatorze nie istnieje";
    private static final String LOGIN_ALREADY_EXISTS_MESSAGE = "Operator z loginem %s już istnieje";
    private static final String LOGINS_ALREADY_EXISTS_MESSAGE = "Operatorzy z loginami %s już istnieją";
    private static final String MISSING_USER_DATA_MESSAGE = "Nie podano wartości dla wymaganego atrybutu użytkownika [%s]";
    private static final String MISSING_OPERATOR_DATA_MESSAGE = "Nie podano wartości dla wymaganego atrybutu operatora [%s]";

    private UserService userService;
    private OperatorService operatorService;

    public UserValidator() {
    }

    @Inject
    public UserValidator(final UserService userService, final OperatorService operatorService) {
        this.userService = userService;
        this.operatorService = operatorService;
    }

    public void validate(final EditableUserDto user, final Long userId) throws AlwinValidationException {
        if (userIdsMatch(user, userId)) {
            throw new AlwinValidationException(MESSED_UP_MESSAGE);
        }

        validateIfUserExists(userId);
        validateLogins(user);
        validateUserMandatoryFields(user);
        validateOperatorMandatoryFields(user.getOperators());
    }

    public void validateIfUserExists(final Long userId) {
        if (userService.doesUserExist(userId)) {
            return;
        }
        throw new EntityNotFoundException(userId, NOT_FOUND_MESSAGE);
    }

    private void validateOperatorMandatoryFields(final List<EditableOperatorDto> operators) {
        if (CollectionUtils.isEmpty(operators)) {
            return;
        }

        for (final EditableOperatorDto operator : operators) {
            checkOperatorDataPresence(operator.getLogin(), "Login");
            checkOperatorDataPresence(operator.getType(), "Typ");
            if (operator.getId() == null) {
                checkOperatorDataPresence(operator.getPassword(), "Hasło");
            }
        }
    }

    private void validateUserMandatoryFields(final EditableUserDto user) {
        checkUserDataPresence(user.getFirstName(), "Imię");
        checkUserDataPresence(user.getLastName(), "Nazwisko");
        checkUserDataPresence(user.getEmail(), "Adres email");
    }

    private void checkUserDataPresence(final Object value, final String name) {
        if (value == null) {
            throw new AlwinValidationException(String.format(MISSING_USER_DATA_MESSAGE, name));
        }
    }

    private void checkOperatorDataPresence(final Object value, final String name) {
        if (value == null) {
            throw new AlwinValidationException(String.format(MISSING_OPERATOR_DATA_MESSAGE, name));
        }
    }

    private void validateLogins(final EditableUserDto user) {
        if (CollectionUtils.isEmpty(user.getOperators())) {
            return;
        }

        final List<String> logins = user.getOperators().stream()
                .filter(operator -> operator.getId() == null)
                .map(EditableOperatorDto::getLogin)
                .collect(Collectors.toList());

        validateDuplicates(logins);

        if (logins.isEmpty()) {
            return;
        }

        final List<String> existingOperators = operatorService.checkIfOperatorsExist(logins);
        if (existingOperators.isEmpty()) {
            return;
        }

        if (existingOperators.size() == 1) {
            throw new AlwinValidationException(String.format(LOGIN_ALREADY_EXISTS_MESSAGE, existingOperators));
        } else {
            throw new AlwinValidationException(String.format(LOGINS_ALREADY_EXISTS_MESSAGE, existingOperators));
        }
    }

    private void validateDuplicates(final List<String> logins) {
        final Set<String> uniqueLogins = new HashSet<>();
        logins.forEach(login -> {
            if (!uniqueLogins.add(login)) {
                throw new AlwinValidationException(String.format(LOGIN_ALREADY_EXISTS_MESSAGE, login));
            }
        });
    }

    private boolean userIdsMatch(final EditableUserDto user, final Long userId) {
        return userId == null || !userId.equals(user.getId());
    }

}

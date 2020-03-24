package com.codersteam.alwin.validator;

import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.demand.ProcessDemandsForPaymentDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentService;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.ObjectUtils.notEqual;

/**
 * Logika walidująca poprawność danych dla wezwań do zapłaty
 *
 * @author Piotr Naroznik
 */
@Stateless
public class DemandForPaymentValidator {

    private static final String INVALID_DEMAND_FOR_PAYMENT_STATE_VALUE_MESSAGE = "Nieprawidłowa wartość statusu [%s] wezwania do zapłaty. Możliwe wartości to [%s]";
    private static final String INVALID_DEMAND_FOR_PAYMENT_TYPE_VALUE_MESSAGE = "Nieprawidłowa wartość typu [%s] wezwania do zapłaty. Możliwe wartości to [%s]";
    private static final String INVALID_DEMAND_FOR_PAYMENT_STATE_MESSAGE = "Nieprawidłowy status wezwania do zapłaty";
    private static final String MISSING_DEMAND_FOR_PAYMENT_MESSAGE = "Wezwanie do zapłaty o podanym identyfikatorze [%s] nie istnieje";
    private static final String MISSING_DEMANDS_FOR_PAYMENT_MESSAGE = "Wezwania do zapłaty o podanych identyfikatorach [%s] nie istnieją";
    private static final String ALREADY_ISSUED_DEMANDS_FOR_PAYMENT_MESSAGE = "Wezwania do zapłaty o podanych identyfikatorach [%s] zostały już wysłane";
    private static final String MISSING_OPERATOR_MESSAGE = "Operator o podanym identyfikatorze nie istnieje";
    private static final String MISSING_PERMISSION_TO_MANAGE_DEMAND_FOR_PAYMENT_MESSAGE = "Operator nie ma uprawnień do zarządzania wezwaniami do zapłaty";

    private DemandForPaymentService demandForPaymentService;
    private OperatorService operatorService;

    public DemandForPaymentValidator() {
    }

    @Inject
    public DemandForPaymentValidator(final DemandForPaymentService demandForPaymentService, final OperatorService operatorService) {
        this.demandForPaymentService = demandForPaymentService;
        this.operatorService = operatorService;
    }

    /**
     * Sprawdza czy operator o podanym identyfikatorze ma uprawnienia do zarządzania wezwaniami do zapłaty
     *
     * @param loggedOperatorId - identyfikator operatora
     */
    public void validateOperatorsPermission(final Long loggedOperatorId) {
        final OperatorDto operator = this.operatorService.findOperatorById(loggedOperatorId);
        if (operator == null) {
            throw new AlwinValidationException(MISSING_OPERATOR_MESSAGE);
        }
        if (operator.getPermission() == null || !operator.getPermission().isAllowedToManageDemandsForPayment()) {
            throw new AlwinValidationException(MISSING_PERMISSION_TO_MANAGE_DEMAND_FOR_PAYMENT_MESSAGE);
        }
    }

    public List<DemandForPaymentState> validateState(final List<String> state) throws AlwinValidationException {
        if (state == null) {
            return emptyList();
        }

        try {
            final List<DemandForPaymentState> mappedStates = new ArrayList<>(state.size());
            for (final String s : state) {
                mappedStates.add(DemandForPaymentState.valueOf(s));
            }
            return mappedStates;
        } catch (final IllegalArgumentException e) {
            throw new AlwinValidationException(String.format(INVALID_DEMAND_FOR_PAYMENT_STATE_VALUE_MESSAGE, state, Arrays.toString(DemandForPaymentState.values())));
        }
    }

    public List<DemandForPaymentTypeKey> validateType(final List<String> type) throws AlwinValidationException {
        if (isEmpty(type)) {
            return emptyList();
        }

        final List<DemandForPaymentTypeKey> types = new ArrayList<>(type.size());
        String typeToCheck = null;
        try {
            for (final String selectedType : type) {
                typeToCheck = selectedType;
                types.add(DemandForPaymentTypeKey.valueOf(selectedType));
            }
        } catch (final IllegalArgumentException e) {
            throw new AlwinValidationException(String.format(INVALID_DEMAND_FOR_PAYMENT_TYPE_VALUE_MESSAGE, typeToCheck,
                    Arrays.toString(DemandForPaymentTypeKey.values())));
        }
        return types;
    }

    public void validate(final ProcessDemandsForPaymentDto demandsToProcess) {
        final List<Long> demandsIds = new ArrayList<>();
        if (isNotEmpty(demandsToProcess.getDemandsToReject())) {
            demandsIds.addAll(demandsToProcess.getDemandsToReject().stream().map(DemandForPaymentDto::getId).collect(Collectors.toList()));
        }
        if (isNotEmpty(demandsToProcess.getDemandsToSend())) {
            demandsIds.addAll(demandsToProcess.getDemandsToSend().stream().map(DemandForPaymentDto::getId).collect(Collectors.toList()));
        }
        final List<Long> unexistingDemandsIds = demandForPaymentService.findNotExistingDemandsForPayment(demandsIds);
        if (isNotEmpty(unexistingDemandsIds)) {
            throw new AlwinValidationException(format(MISSING_DEMANDS_FOR_PAYMENT_MESSAGE, unexistingDemandsIds));
        }

        final List<Long> issuedDemandsForPayment = demandForPaymentService.findIssuedDemandsForPayment(demandsIds);
        if (isNotEmpty(issuedDemandsForPayment)) {
            throw new AlwinValidationException(format(ALREADY_ISSUED_DEMANDS_FOR_PAYMENT_MESSAGE, issuedDemandsForPayment));
        }
    }

    public void validateIssuedDemandForPayment(final long demandForPaymentId) {
        final DemandForPaymentDto demandForPaymentDto = demandForPaymentService.find(demandForPaymentId)
                .orElseThrow(() -> new AlwinValidationException(format(MISSING_DEMAND_FOR_PAYMENT_MESSAGE, demandForPaymentId)));

        if (notEqual(demandForPaymentDto.getState(), DemandForPaymentState.ISSUED)) {
            throw new AlwinValidationException(format(INVALID_DEMAND_FOR_PAYMENT_STATE_MESSAGE, demandForPaymentId));
        }
    }
}

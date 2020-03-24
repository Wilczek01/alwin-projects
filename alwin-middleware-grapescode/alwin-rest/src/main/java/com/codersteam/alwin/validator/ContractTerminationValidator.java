package com.codersteam.alwin.validator;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.core.api.model.termination.ActivateContractTerminationDto;
import com.codersteam.alwin.core.api.model.termination.ProcessContractsTerminationDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.termination.ContractTerminationType.CONDITIONAL;
import static com.codersteam.alwin.common.termination.ContractTerminationType.IMMEDIATE;
import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.ObjectUtils.notEqual;

/**
 * Logika sprawdzająca poprawność danych dla wypowiedzeń umów
 *
 * @author Dariusz Raćkowski
 */
@Stateless
public class ContractTerminationValidator {
    private static final String CONTRACT_TERMINATION_MUST_BE_ONLY_IN_ONE_OPERATION = "Wypowiedzenie umowy musi występować co najwyżej w jednym operacji";
    private static final String MISSING_CONTRACT_TERMINATION_MESSAGE = "Wypowiedzenia umów o podanych identyfikatorach %s nie istnieją";
    private static final String ALREADY_ISSUED_CONTRACT_TERMINATIONS_MESSAGE = "Wypowiedzenia umów o podanych identyfikatorach %s zostały już obsłużone";
    private static final String COMPANY_ID_NOT_MATCH_FOR_CONTRACT_TERMINATION_ID = "Nr klienta nie jest prawidłowy dla wypowiedzenia umowy o id %s";
    private static final String COULD_NOT_CHANGE_IMMEDIATE_TYPE_TO_CONDITIONAL = "Natychmiastowe wypowiedzenie umowy nie może zostać zmienione na warunkowe (umowa o id %s)";
    private static final String WRONG_NEW_TERMINATION_DATE_FOR_TERMINATION_IDS = "Wybrano nieprawidłowe daty odroczenia wypowiedzenia umów dla wypowiedzeń %s";
    private static final String SELECTED_MORE_THAN_ONE_TERMINATION_TYPE_IN_ONE_GROUP = "Wybrano więcej niż jeden typ wypowiedzenia dla klienta o id %s";
    private static final String TYPE_IN_GROUP_SHOULD_MUCH_TYPES_IN_CONTRACTS = "Typ wypowiedzenia w grupie musi się zgadzać z typem w każdym kontrakcie dla klienta o id %s";
    private static final String CONTRACT_TERMINATION_NOT_FOUND = "Wypowiedzenie umowy o podanym id (%s) nie istnieje";
    private static final String CONTRACT_TERMINATION_IS_NOT_IN_ISSUED_STATE = "Wypowiedzenie umowy o id %s nie jest wysłane";
    private static final String ACTIVATION_DATE_IS_MISSING = "Nie wybrano daty aktywacji dla wypowiedzenia o id %s";
    private static final String ACTIVATION_DATE_IS_BEFORE_TERMINATION_DATE = "Daty aktywacji dla wypowiedzenia o id %s nie może być starsza niż data wypowiedzenia";
    private static final String RESUMPTION_COST_IS_MISSING = "Nie podano wartości opłaty za wznowienie dla wypowiedzenia o id %s";

    private ContractTerminationService contractTerminationService;
    private DateProvider dateProvider;

    public ContractTerminationValidator() {
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public ContractTerminationValidator(final ContractTerminationService contractTerminationService, final DateProvider dateProvider) {
        this.contractTerminationService = contractTerminationService;
        this.dateProvider = dateProvider;
    }

    public void validate(final ProcessContractsTerminationDto terminations) {
        final Set<Long> contractTerminationIds = new HashSet<>();

        if (isNotEmpty(terminations.getTerminationsToReject())) {
            contractTerminationIds.addAll(extractContractTerminationIds(terminations.getTerminationsToReject()));
        }

        if (isNotEmpty(terminations.getTerminationsToPostpone())) {
            final Set<Long> ids = extractContractTerminationIds(terminations.getTerminationsToPostpone());
            if (ids.stream().anyMatch(contractTerminationIds::contains)) {
                throw new AlwinValidationException(CONTRACT_TERMINATION_MUST_BE_ONLY_IN_ONE_OPERATION);
            }
            contractTerminationIds.addAll(ids);
        }

        if (isNotEmpty(terminations.getTerminationsToSend())) {
            final Set<Long> ids = extractContractTerminationIds(terminations.getTerminationsToSend());
            if (ids.stream().anyMatch(contractTerminationIds::contains)) {
                throw new AlwinValidationException(CONTRACT_TERMINATION_MUST_BE_ONLY_IN_ONE_OPERATION);
            }
            contractTerminationIds.addAll(ids);
        }

        final List<Long> unexistingContractTerminationIds = contractTerminationService.findNotExistingContractTerminationIds(contractTerminationIds);
        if (isNotEmpty(unexistingContractTerminationIds)) {
            throw new AlwinValidationException(format(MISSING_CONTRACT_TERMINATION_MESSAGE, unexistingContractTerminationIds));
        }

        final List<Long> closedTerminationIds = contractTerminationService.findNotReadyToProcessContractTerminations(contractTerminationIds);
        if (isNotEmpty(closedTerminationIds)) {
            throw new AlwinValidationException(format(ALREADY_ISSUED_CONTRACT_TERMINATIONS_MESSAGE, closedTerminationIds));
        }

        validateNewTerminationDateIsGreaterThanCurrentDate(terminations.getTerminationsToPostpone());

        final Map<Long, Long> contractIdToCompanyId = contractTerminationService.findContractIdToCompanyIds(contractTerminationIds);
        validateCompanyIdForContractId(terminations.getTerminationsToReject(), contractIdToCompanyId);
        validateCompanyIdForContractId(terminations.getTerminationsToPostpone(), contractIdToCompanyId);
        validateCompanyIdForContractId(terminations.getTerminationsToSend(), contractIdToCompanyId);

        final Map<Long, ContractTerminationType> contractIdToContractType = contractTerminationService.findContractIdToContractType(contractTerminationIds);
        validateTerminationType(terminations.getTerminationsToSend(), contractIdToContractType);
    }

    public void validate(final long contractTerminationId, final ActivateContractTerminationDto activateContractTerminationDto) {
        final TerminationContractDto termination = contractTerminationService.findTermination(contractTerminationId)
                .orElseThrow(() -> new AlwinValidationException(format(CONTRACT_TERMINATION_NOT_FOUND, contractTerminationId)));

        if (notEqual(termination.getState(), ContractTerminationState.ISSUED)) {
            throw new AlwinValidationException(format(CONTRACT_TERMINATION_IS_NOT_IN_ISSUED_STATE, contractTerminationId));
        }

        if (activateContractTerminationDto.getActivationDate() == null) {
            throw new AlwinValidationException(format(ACTIVATION_DATE_IS_MISSING, contractTerminationId));
        }

        if (activateContractTerminationDto.getActivationDate().before(termination.getTerminationDate())) {
            throw new AlwinValidationException(format(ACTIVATION_DATE_IS_BEFORE_TERMINATION_DATE, contractTerminationId));
        }

        if (activateContractTerminationDto.getResumptionCost() == null) {
            throw new AlwinValidationException(format(RESUMPTION_COST_IS_MISSING, contractTerminationId));
        }
    }

    void validateNewTerminationDateIsGreaterThanCurrentDate(final List<TerminationDto> terminationsToPostpone) {
        final Date currentDateEndOfDay = dateProvider.getCurrentDateEndOfDay();
        final List<Long> terminationIdsWithWrongTerminationDate = terminationsToPostpone.stream()
                .flatMap(termination -> termination.getContracts().stream())
                .filter(termination -> termination.getTerminationDate() == null || !termination.getTerminationDate().after(currentDateEndOfDay))
                .map(TerminationContractDto::getContractTerminationId)
                .collect(Collectors.toList());
        if (!terminationIdsWithWrongTerminationDate.isEmpty()) {
            throw new AlwinValidationException(format(WRONG_NEW_TERMINATION_DATE_FOR_TERMINATION_IDS, terminationIdsWithWrongTerminationDate));
        }
    }

    void validateTerminationType(final List<TerminationDto> terminations, final Map<Long, ContractTerminationType> contractIdToContractType) {
        for (final TerminationDto termination : terminations) {
            validatePossibilityToChangeTerminationType(contractIdToContractType, termination);
            validateThatTerminationHasOnlyOneTypeSelected(termination);
            validateMatchingTerminationTypeWithTypeInGroup(termination);
        }
    }

    private void validateMatchingTerminationTypeWithTypeInGroup(final TerminationDto termination) {
        final ContractTerminationType typeInGroup = termination.getType();
        for (final TerminationContractDto contract : termination.getContracts()) {
            if (!Objects.equals(typeInGroup, contract.getType())) {
                throw new AlwinValidationException(format(TYPE_IN_GROUP_SHOULD_MUCH_TYPES_IN_CONTRACTS, termination.getExtCompanyId()));
            }
        }
    }

    private void validateThatTerminationHasOnlyOneTypeSelected(final TerminationDto termination) {
        final Set<ContractTerminationType> differentTypes = termination.getContracts().stream()
                .map(TerminationContractDto::getType)
                .collect(Collectors.toSet());
        if (differentTypes.size() > 1) {
            throw new AlwinValidationException(format(SELECTED_MORE_THAN_ONE_TERMINATION_TYPE_IN_ONE_GROUP, termination.getExtCompanyId()));
        }
    }

    private void validatePossibilityToChangeTerminationType(final Map<Long, ContractTerminationType> contractIdToContractType, final TerminationDto termination) {
        for (final TerminationContractDto contract : termination.getContracts()) {
            final ContractTerminationType currentTerminationType = contractIdToContractType.get(contract.getContractTerminationId());
            if (IMMEDIATE.equals(currentTerminationType) && CONDITIONAL.equals(contract.getType())) {
                throw new AlwinValidationException(format(COULD_NOT_CHANGE_IMMEDIATE_TYPE_TO_CONDITIONAL, contract.getContractTerminationId()));
            }
        }
    }

    void validateCompanyIdForContractId(final List<TerminationDto> terminationsToReject, final Map<Long, Long> contractIdToCompanyId) {
        for (final TerminationDto terminationDto : terminationsToReject) {
            for (final TerminationContractDto contract : terminationDto.getContracts()) {
                if (!terminationDto.getExtCompanyId().equals(contractIdToCompanyId.get(contract.getContractTerminationId()))) {
                    throw new AlwinValidationException(format(COMPANY_ID_NOT_MATCH_FOR_CONTRACT_TERMINATION_ID, contract.getContractTerminationId()));
                }
            }
        }
    }

    Set<Long> extractContractTerminationIds(final List<TerminationDto> terminations) {
        return terminations.stream()
                .flatMap(termination -> termination.getContracts().stream())
                .map(TerminationContractDto::getContractTerminationId)
                .collect(Collectors.toSet());
    }
}

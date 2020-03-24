package com.codersteam.alwin.validator;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.service.CompanyService;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.operator.PermissionDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.issue.IssueTerminationService;
import com.codersteam.alwin.core.api.service.issue.IssueTypeService;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.exception.AlwinValidationException;
import com.codersteam.alwin.util.IdsDto;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Logika walidująca poprawność danych dla zlecenia
 *
 * @author Adam Stepnowski
 */
@Stateless
public class IssueValidator {

    public static final String MISSING_EXT_COMPANY_ID_MESSAGE = "Brak identyfikatora firmy w systemie AIDA";
    public static final String MISSING_ISSUE_TYPE_ID_MESSAGE = "Brak identyfikatora typu zlecenia";
    public static final String MISSING_COMPANY_MESSAGE = "Firma o podanym identyfikatorze nie istnieje w systemie AIDA";
    public static final String MISSING_ISSUE_TYPE_MESSAGE = "Typ zlecenia o podanym identyfikatorze nie istnieje";
    public static final String COMPANY_EXCLUDED_MESSAGE = "Firma o podanym identyfikatorze jest wyłączona z obsługi windykacyjnej";
    public static final String COMPANY_HAS_ACTIVE_ISSUE_MESSAGE = "Firma o podanym identyfikatorze posiada aktywne zlecenie windykacyjne";
    public static final String MISSING_OPERATOR_MESSAGE = "Operator o podanym identyfikatorze nie istnieje";
    public static final String NOT_SUBORDINATE_OPERATOR_MESSAGE = "Operator nie jest podwładnym managera";
    public static final String WRONG_ISSUE_EXPIRATION_MESSAGE = "Data zakończenia zlecenia nie może być rowna/wcześniejsza niż data dzisiejsza";
    public static final String WRONG_ASSIGNEE_TYPE_FOR_ISSUE_MESSAGE = "Operator o podanym identyfikatorze nie obsługuje zleceń danego typu";
    public static final String WRONG_ISSUE_STATE_KEY_MESSAGE = "Nieprawidłowy status zlecenia [%s]";
    public static final String MISSING_ISSUE_MESSAGE = "Zlecenie o podanym identyfikatorze [%s] nie istnieje";
    public static final String ISSUE_ALREADY_CLOSED_MESSAGE = "Zlecenie o podanym identyfikatorze zostało juz zakończone";
    public static final String MISSING_ISSUE_TERMINATION_CAUSE_MESSAGE = "Brak przyczyny przerwania";
    public static final String TOO_LONG_ISSUE_TERMINATION_CAUSE_MESSAGE = "Przyczyna przerwania jest za długa (max to 1000 znaków)";
    public static final String TOO_LONG_ISSUE_EXCLUDED_FROM_STATS_CAUSE_MESSAGE = "Przyczyna wykluczenia ze statystyk jest za długa (max to 500 znaków)";
    public static final String MISSING_ISSUE_TERMINATION_REQUEST_MESSAGE = "Brak żądania o przedterminowe zakończenia zlecenia";
    public static final String ISSUE_TERMINATION_REQUEST_ALREADY_CLOSED_MESSAGE = "Żądanie o przedterminowe zakończenie zlecenia zostało juz obsłużone";
    public static final String TOO_LONG_COMMENT_MESSAGE = "Komentarz jest za długa (max to 500 znaków)";
    public static final String MISSING_PERMISSION_TO_MANAGE_TERMINATION_REQUEST_MESSAGE = "Operator nie ma uprawnień do zarządzania żądaniami o " +
            "przeterminowane zakończenie zlecenia";
    public static final String MISSING_PERMISSION_TO_EXCLUDE_INVOICE_REQUEST_MESSAGE = "Operator nie ma uprawnień do wykluczania faktur z obsługi zlecenia";
    public static final int MAX_COMMENT_LENGTH = 500;
    private static final String WRONG_BALANCE_VALUE_MESSAGE = "Niepoprawna wartość salda '%s'";
    private static final String NOT_MANAGED_ISSUES_MESSAGE = "Zlecenia '%s' nie mogą być zarządzane przez zalogowanego operatora";
    private static final String MISSING_ISSUES_MESSAGE = "Brak zleceń do przypisania";
    private IssueService issueService;
    private CompanyService companyService;
    private IssueTypeService issueTypeService;
    private CustomerVerifierService customerVerifierService;
    private OperatorService operatorService;
    private DateProvider dateProvider;
    private IssueTerminationService issueTerminationService;

    public IssueValidator() {
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    public IssueValidator(final IssueService issueService, final AidaService aidaService, final IssueTypeService issueTypeService,
                          final CustomerVerifierService customerVerifierService, final OperatorService operatorService, final DateProvider dateProvider,
                          final IssueTerminationService issueTerminationService) {
        this.issueService = issueService;
        this.companyService = aidaService.getCompanyService();
        this.issueTypeService = issueTypeService;
        this.customerVerifierService = customerVerifierService;
        this.operatorService = operatorService;
        this.dateProvider = dateProvider;
        this.issueTerminationService = issueTerminationService;
    }

    /**
     * Sprawdza czy przypisanie powinno nastąpić tylko dla wybranych identyfikatorów, czy dla wszystkich zleceń
     *
     * @param updateAll - czy zaktualizować wszystkie (nie tylko te przekazane w ids)
     * @return true, jeśli tylko dla wybranych, false w każdym innym wypadku
     */
    public static boolean shouldAssignGivenIssueIds(final Boolean updateAll) {
        return !Boolean.TRUE.equals(updateAll);
    }

    /**
     * Sprawdza czy wartość salda jest poprawną liczbą
     * {@link AlwinValidationException}
     *
     * @param balance saldo
     */
    public void validateBalanceWithDotAndTwoDecimalValues(final String balance) {
        if (balance == null) {
            return;
        }
        try {
            new BigDecimal(balance);
        } catch (final NumberFormatException e) {
            throw new AlwinValidationException(String.format(WRONG_BALANCE_VALUE_MESSAGE, balance));
        }
    }

    /**
     * Sprawdza czy podane identyfikatory zleceń podlegają zalogowanemu operatorowi,
     * jeśli nie to rzuca wyjątek {@link AlwinValidationException}
     *
     * @param loggedOperatorType - typ zalogowanego operatora
     * @param issueIds           - identyfikatory zleceń
     */
    public void validateManagedIssues(final OperatorType loggedOperatorType, final IdsDto issueIds) {
        if (issueIds == null || isEmpty(issueIds.getIds())) {
            throw new AlwinValidationException(MISSING_ISSUES_MESSAGE);
        }

        final List<Long> notManagedIssues = issueService.findNotManagedIssues(loggedOperatorType, issueIds.getIds());
        if (notManagedIssues.isEmpty()) {
            return;
        }

        throw new AlwinValidationException(String.format(NOT_MANAGED_ISSUES_MESSAGE, notManagedIssues.toString()));
    }

    /**
     * Walidacja danych wejściowych przy manualnym tworzeniu zlecenia
     *
     * @param extCompanyId        - identyfikator firmy AIDA
     * @param issueTypeId         - identyfikator typu zlecenia
     * @param loggerOperatorId    - identyfikator zalogowanego managera
     * @param assigneeId          - identyfikator przypisywanego operatora
     * @param issueExpirationDate - data zakończenia zlecenia
     */
    public void validateIssueCreate(final Long extCompanyId, final Long issueTypeId, final Long loggerOperatorId, final Long assigneeId,
                                    final Date issueExpirationDate) {

        validateIdNotNull(extCompanyId, MISSING_EXT_COMPANY_ID_MESSAGE);
        validateIdNotNull(issueTypeId, MISSING_ISSUE_TYPE_ID_MESSAGE);
        validateCompanyExists(extCompanyId);
        final IssueTypeWithOperatorTypesDto issueType = issueTypeService.findIssueTypeById(issueTypeId);
        validateIssueTypeExists(issueType);
        validateCompanyProcessingExclusion(extCompanyId);
        validateCompanyActiveIssue(extCompanyId);
        validateAssignee(loggerOperatorId, assigneeId, issueType);
        validateIssueExpirationDate(issueExpirationDate);
    }

    public void validateStates(final List<String> states) {
        if (isEmpty(states)) {
            return;
        }

        states.forEach(state -> {
            try {
                IssueStateDto.valueOf(state);
            } catch (final IllegalArgumentException e) {
                throw new AlwinValidationException(String.format(WRONG_ISSUE_STATE_KEY_MESSAGE, state));
            }
        });
    }

    private void validateAssignee(final Long loggerOperatorId, final Long assigneeId, final IssueTypeWithOperatorTypesDto issueType) {
        if (assigneeId != null) {
            final OperatorDto assignee = operatorService.findOperatorById(assigneeId);
            validateAssigneeExists(assignee);
            validateAssigneeSubordinate(loggerOperatorId, assignee);
            validateAssigneeIssueWithIssueType(issueType, assignee);
        }
    }

    private void validateAssigneeIssueWithIssueType(final IssueTypeWithOperatorTypesDto issueType, final OperatorDto assignee) {
        if (!issueType.getOperatorTypes().contains(assignee.getType())) {
            throw new AlwinValidationException(WRONG_ASSIGNEE_TYPE_FOR_ISSUE_MESSAGE);
        }
    }

    private void validateAssigneeSubordinate(final Long loggerOperatorId, final OperatorDto assignee) {
        if (assignee.getParentOperator() == null || !loggerOperatorId.equals(assignee.getParentOperator().getId())) {
            throw new AlwinValidationException(NOT_SUBORDINATE_OPERATOR_MESSAGE);
        }
    }

    private void validateAssigneeExists(final OperatorDto assignee) {
        if (assignee == null) {
            throw new AlwinValidationException(MISSING_OPERATOR_MESSAGE);
        }
    }

    private void validateIssueExpirationDate(final Date issueExpirationDate) {
        if (issueExpirationDate != null && !dateProvider.getCurrentDateStartOfDay().before(issueExpirationDate)) {
            throw new AlwinValidationException(WRONG_ISSUE_EXPIRATION_MESSAGE);
        }
    }

    private void validateCompanyActiveIssue(final Long extCompanyId) {
        if (issueService.doesCompanyHaveActiveIssue(extCompanyId)) {
            throw new AlwinValidationException(COMPANY_HAS_ACTIVE_ISSUE_MESSAGE);
        }
    }

    private void validateCompanyProcessingExclusion(final Long extCompanyId) {
        if (customerVerifierService.isCompanyExcludedFromDebtCollection(extCompanyId)) {
            throw new AlwinValidationException(COMPANY_EXCLUDED_MESSAGE);
        }
    }

    private void validateIssueTypeExists(final IssueTypeWithOperatorTypesDto issueType) {
        if (issueType == null) {
            throw new AlwinValidationException(MISSING_ISSUE_TYPE_MESSAGE);
        }
    }

    private void validateCompanyExists(final Long extCompanyId) {
        final AidaCompanyDto company = companyService.findCompanyByCompanyId(extCompanyId);
        if (company == null) {
            throw new AlwinValidationException(MISSING_COMPANY_MESSAGE);
        }
    }

    private void validateIdNotNull(final Long id, final String missingIdMessage) {
        if (id == null) {
            throw new AlwinValidationException(missingIdMessage);
        }
    }

    /**
     * Walidacja danych wejściowych przy przedterminowym zakończeniu zlecenia
     *
     * @param issueId                 - identyfikator zlecenia
     * @param terminationCause        - przyczyna przedwczesnego zamknięcia zlecenia
     * @param exclusionFromStatsCause - przyczyna pominięcia w statystykach
     */
    public void validateTerminateIssue(final Long issueId, final String terminationCause, final String exclusionFromStatsCause) {
        validateIssueTermination(issueId);
        validateTerminateCause(terminationCause);
        validateExclusionFromStatsCause(exclusionFromStatsCause);
    }

    private void validateIssueTermination(final Long issueId) {
        final IssueDto issue = checkIfIssueExists(issueId);
        if (!IssueStateDto.ACTIVE_STATES.contains(issue.getIssueState())) {
            throw new AlwinValidationException(ISSUE_ALREADY_CLOSED_MESSAGE);
        }
    }

    public IssueDto checkIfIssueExists(final Long issueId) {
        final IssueDto issue = issueService.findIssue(issueId);
        if (issue == null) {
            throw new AlwinValidationException(String.format(MISSING_ISSUE_MESSAGE, issueId));
        }
        return issue;
    }

    private void validateTerminateCause(final String terminationCause) {
        if (StringUtils.isBlank(terminationCause)) {
            throw new AlwinValidationException(MISSING_ISSUE_TERMINATION_CAUSE_MESSAGE);
        }
        if (StringUtils.length(terminationCause) > 1000) {
            throw new AlwinValidationException(TOO_LONG_ISSUE_TERMINATION_CAUSE_MESSAGE);
        }
    }

    private void validateExclusionFromStatsCause(final String exclusionFromStatsCause) {
        if (StringUtils.length(exclusionFromStatsCause) > 500) {
            throw new AlwinValidationException(TOO_LONG_ISSUE_EXCLUDED_FROM_STATS_CAUSE_MESSAGE);
        }
    }

    /**
     * Walidacja danych wejściowych przy odrzucaniu żądania przedterminowego zakończenia zlecenia
     *
     * @param terminationRequestId - identyfikator żądania o przedterminowe zakończenie zlecenia
     * @param loggedOperatorId     - identyfikaotr operatora
     * @param comment              - komentarz
     */
    public void validateRejectIssueTerminateRequest(final Long terminationRequestId, final Long loggedOperatorId, final String comment) {
        final IssueTerminationRequestDto terminationRequest = issueTerminationService.findTerminationRequestById(terminationRequestId);
        validateTerminationRequest(terminationRequest);
        validateIssueTermination(terminationRequest.getIssueId());
        validateOperatorPermission(loggedOperatorId);
        validateCommentLength(comment);
    }


    /**
     * Walidacja danych wejściowych przy akceptacji żądania przedterminowego zakończenia zlecenia
     *
     * @param terminationRequestId - identyfikator żądania o przedterminowe zakończenie zlecenia
     * @param loggedOperatorId     - identyfikaotr operatora
     * @param requestDto           - dane do zamknięcia żądania
     */
    public void validateAcceptIssueTerminateRequest(final Long terminationRequestId, final Long loggedOperatorId, final IssueTerminationRequestDto requestDto) {
        final IssueTerminationRequestDto terminationRequest = issueTerminationService.findTerminationRequestById(terminationRequestId);
        validateTerminationRequest(terminationRequest);
        validateIssueTermination(terminationRequest.getIssueId());
        validateOperatorPermission(loggedOperatorId);
        validateTerminateCause(requestDto.getRequestCause());
        validateExclusionFromStatsCause(requestDto.getExclusionFromStatsCause());
        validateCommentLength(requestDto.getComment());
    }

    private void validateCommentLength(final String comment) {
        if (StringUtils.length(comment) > MAX_COMMENT_LENGTH) {
            throw new AlwinValidationException(TOO_LONG_COMMENT_MESSAGE);
        }
    }

    private void validateOperatorPermission(final Long loggedOperatorId) {
        final OperatorDto operator = operatorService.findOperatorById(loggedOperatorId);
        if (operator == null) {
            throw new AlwinValidationException(MISSING_OPERATOR_MESSAGE);
        }
        final PermissionDto permission = operator.getPermission();
        if (permission == null || !permission.isAllowedToTerminateIssue()) {
            throw new AlwinValidationException(MISSING_PERMISSION_TO_MANAGE_TERMINATION_REQUEST_MESSAGE);
        }
    }

    public void validateOperatorIsAllowedToExcludeInvoicePermission(final Long loggedOperatorId) {
        final OperatorDto operator = operatorService.findOperatorById(loggedOperatorId);
        final PermissionDto permission = operator.getPermission();
        if (permission == null || !permission.isAllowedToExcludeInvoice()) {
            throw new AlwinValidationException(MISSING_PERMISSION_TO_EXCLUDE_INVOICE_REQUEST_MESSAGE);
        }
    }

    private void validateTerminationRequest(final IssueTerminationRequestDto terminationRequest) {
        if (terminationRequest == null) {
            throw new AlwinValidationException(MISSING_ISSUE_TERMINATION_REQUEST_MESSAGE);
        }
        if (!IssueTerminationRequestStateDto.NEW.equals(terminationRequest.getState())) {
            throw new AlwinValidationException(ISSUE_TERMINATION_REQUEST_ALREADY_CLOSED_MESSAGE);
        }
    }
}
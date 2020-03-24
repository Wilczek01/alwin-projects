package com.codersteam.alwin.validator;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;
import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.core.api.model.declaration.DeclarationDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.ActivityDetailPropertyService;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.exception.AlwinValidationException;
import com.codersteam.alwin.validator.activity.ActivityDetailPropertyFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.PLANNED;
import static io.jsonwebtoken.lang.Collections.isEmpty;
import static java.lang.String.format;

/**
 * Logika walidująca poprawność danych dla czynności windykacyjnych
 *
 * @author Michal Horowic
 */
@Stateless
public class ActivityValidator {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Maksymalna ilość dni w przód, dla której można ustawić datę deklarację spłaty
     */
    private static final Integer MAX_DECLARATION_DAY_END = 7;

    /**
     * Maksymalna ilość dni w przód, dla której można ustawić datę deklarację spłaty w przypadku,
     * gdy zlecenie ma aktywne czynności windykacyjne na najbliższe dni
     */
    private static final Integer MAX_DECLARATION_DAY_END_WITH_ACTIVITY = 7;

    private static final String REQUIRED_MESSAGE = "Wartość dla '%s' jest wymagana";
    private static final String WRONG_TYPE_MESSAGE = "Niepoprawny typ dla wartości '%s'";
    private static final String WRONG_VALUE_MESSAGE = "Niepoprawna wartość dla '%s'";
    private static final String WRONG_PLANNED_DATE_MESSAGE = "Data zaplanowania nie może być późniejsza niż data zakończenia zlecenia";
    private static final String PLANNED_DATE_TOO_LATE_MESSAGE = format("Czynność może być zaplanowana maksymalnie %d dni w przód", MAX_DECLARATION_DAY_END);
    private static final String WRONG_DECLARED_PAYMENT_DATE_MESSAGE = "Data zapłaty deklaracji nie może być późniejsza niż data zakończenia zlecenia";
    private static final String WRONG_DECLARATION_DATE_MESSAGE = "Data stworzenia deklaracji nie może być wcześniejsza niż poprzedni dzień roboczy";
    private static final String WRONG_DECLARATION_AMOUNT_MESSAGE = "Kwota spłaty w deklaracji nie może być ujemna";
    private static final String DECLARATION_DATE_TOO_LATE_MESSAGE = "Maksymalna data zapłaty deklaracji to %1$td.%1$tm.%1$tY";
    private static final String WRONG_ACTIVITY_ID = "Niepoprawna czynność windykacyjna do aktualizacji";
    private static final String WRONG_ACTIVITY_STATE = "Niepoprawny status czynności windykacyjnej";
    private static final String WRONG_ACTIVITY_TYPE = "Niepoprawny typ czynności windykacyjnej";
    private static final String WRONG_ISSUE_TYPE = "Niepoprawny typ zlecenia windykacyjnego [%s]";
    private static final String WRONG_OPERATOR_TYPE_MESSAGE = "Niepoprawny typ operatora: '%s'";
    private static final String ILLEGAL_ISSUE_TYPE_ACTIVITY_TYPE = "Nieprawidłowy typ czynności dla typu zlecenia";
    private static final String MISSING_DETAIL_PROPERTY = "Cecha o podanym identyfikatorze [%s] nie istnieje";

    private static final ActivityDetailPropertyFactory DETAIL_FACTORY = new ActivityDetailPropertyFactory();
    private IssueService issueService;
    private ActivityService activityService;
    private DateProvider dateProvider;
    private ActivityTypeService activityTypeService;
    private ActivityDetailPropertyService activityDetailPropertyService;

    public ActivityValidator() {
    }

    @Inject
    public ActivityValidator(final IssueService issueService, final ActivityService activityService, final DateProvider dateProvider,
                             final ActivityTypeService activityTypeService, final ActivityDetailPropertyService activityDetailPropertyService) {
        this.issueService = issueService;
        this.activityService = activityService;
        this.dateProvider = dateProvider;
        this.activityTypeService = activityTypeService;
        this.activityDetailPropertyService = activityDetailPropertyService;
    }

    /**
     * Sprawdza czy uzupełniona wartość ma dobry typ oraz odpowiadającą mu wartość, jeśli nie to rzuca wyjątek {@link AlwinValidationException}
     *
     * @param detail szczegół czynności windykacyjnej do sprawdzenia
     */
    private static void validateValue(final ActivityDetailDto detail) {
        final ActivityDetailPropertyDto property = detail.getDetailProperty();
        try {
            DETAIL_FACTORY.parseDetailProperty(detail);
        } catch (final ClassNotFoundException e) {
            LOG.error("Activity validation failed due to missing/wrong class", e);
            throw new AlwinValidationException(format(WRONG_TYPE_MESSAGE, property.getName()));
        } catch (final Exception e) {
            LOG.error("Activity validation failed", e);
            throw new AlwinValidationException(format(WRONG_VALUE_MESSAGE, property.getName()));
        }
    }

    /**
     * Sprawdza czy wartość jest uzupełniona, jeśli nie to rzuca wyjątek {@link AlwinValidationException}
     *
     * @param detail szczegół czynności windykacyjnej do sprawdzenia
     */
    private static void validateValuePresence(final ActivityDetailDto detail) {
        if (detail.getValue() == null) {
            throw new AlwinValidationException(format(REQUIRED_MESSAGE, detail.getDetailProperty().getName()));
        }
    }

    public void validate(final ActivityDto activity) throws BadRequestException {
        if (activity == null) {
            return;
        }

        if (activity.getActivityDetails() != null) {
            for (final ActivityDetailDto detail : activity.getActivityDetails()) {
                //TODO walidacja na podstawień konfiguracji w bazie (aktualnie walidacja odbywa się na podstawie przychodzącego requesta)
                if (Boolean.TRUE.equals(detail.getRequired())) {
                    validateValuePresence(detail);
                }
                if (detail.getValue() != null) {
                    validateValue(detail);
                }
            }
        }

        validateActivityStateAndType(activity);
        final IssueDto issue = issueService.findIssue(activity.getIssueId());
        validatePlannedDate(activity, issue.getExpirationDate());
        validateMaxPlannedDate(activity.getPlannedDate(), activity.getState());
        validateDeclaredPaymentDate(activity, issue.getExpirationDate());
        validateDeclarationData(activity);
        validateIssueTypeForActivityType(issue.getIssueType().getId(), activity.getActivityType().getId());
    }

    private void validateIssueTypeForActivityType(final Long issueTypeId, final Long activityTypeId) {
        if (!activityTypeService.checkIfActivityTypeIsAllowedForIssueType(issueTypeId, activityTypeId)) {
            throw new AlwinValidationException(ILLEGAL_ISSUE_TYPE_ACTIVITY_TYPE);
        }
    }

    public void validateManagedActivity(final ActivityDto activity, final OperatorType loggedOperatorType) {
        validate(activity);
        validateOperatorType(loggedOperatorType);
    }

    /**
     * Sprawdza, czy uzupełniona wartość ma poprawny typ operatora, jeśli nie to rzuca wyjątek {@link AlwinValidationException}
     *
     * @param - loggedOperatorType typ operatora do sprawdzenia
     */
    private void validateOperatorType(final OperatorType loggedOperatorType) {
        if (!loggedOperatorType.equals(OperatorType.PHONE_DEBT_COLLECTOR_MANAGER)) {
            throw new AlwinValidationException(format(WRONG_OPERATOR_TYPE_MESSAGE, loggedOperatorType.name()));
        }
    }

    /**
     * Sprawdza czy data zaplanowania jest wcześniejsza niż data zakończenia zlecenia, jeśli nie to rzuca wyjątek
     * {@link AlwinValidationException}
     *
     * @param activity       - czynność windykacyjna do sprawdzenia
     * @param expirationDate - data zakończenia zlecenia
     */
    private void validatePlannedDate(final ActivityDto activity, final Date expirationDate) {
        if (activity.getPlannedDate() == null) {
            return;
        }
        if (activity.getPlannedDate().after(expirationDate)) {
            throw new AlwinValidationException(WRONG_PLANNED_DATE_MESSAGE);
        }
    }

    /**
     * Sprawdza czy data spłaty deklaracji jest wcześniejsza niż data zakończenia zlecenia oraz czy jest w poprawnym przedziale czasowym,
     * jeśli nie to rzuca wyjątek {@link AlwinValidationException}
     *
     * @param activity       - czynność windykacyjna do sprawdzenia
     * @param expirationDate - data zakończenia zlecenia
     */
    private void validateDeclaredPaymentDate(final ActivityDto activity, final Date expirationDate) {
        if (isEmpty(activity.getDeclarations())) {
            return;
        }
        final Date maxDeclarationDate = determineMaxDeclarationDate(activity, expirationDate);
        for (final DeclarationDto declaration : activity.getDeclarations()) {
            if (declaration.getDeclaredPaymentDate().after(expirationDate)) {
                throw new AlwinValidationException(WRONG_DECLARED_PAYMENT_DATE_MESSAGE);
            }
            if (declaration.getDeclaredPaymentDate().after(maxDeclarationDate)) {
                throw new AlwinValidationException(format(DECLARATION_DATE_TOO_LATE_MESSAGE, maxDeclarationDate));
            }
        }
    }

    /**
     * Sprawdza czy data stworzenia deklaracji jest późniejsza niż początek poprzedniego dnia roboczego,
     * jeśli nie to rzuca wyjątek {@link AlwinValidationException}
     *
     * @param activity - czynność windykacyjna do sprawdzenia
     */
    private void validateDeclarationData(final ActivityDto activity) {
        final Date startOfPreviousWorkingDay = dateProvider.getStartOfPreviousWorkingDay();
        if (isEmpty(activity.getDeclarations())) {
            return;
        }
        for (final DeclarationDto declaration : activity.getDeclarations()) {
            if (declaration.getDeclarationDate().before(startOfPreviousWorkingDay)) {
                throw new AlwinValidationException(WRONG_DECLARATION_DATE_MESSAGE);
            }
            if (declaration.getDeclaredPaymentAmountPLN() != null && declaration.getDeclaredPaymentAmountPLN().compareTo(BigDecimal.ZERO) < 0) {
                throw new AlwinValidationException(WRONG_DECLARATION_AMOUNT_MESSAGE);
            }
            if (declaration.getDeclaredPaymentAmountEUR() != null && declaration.getDeclaredPaymentAmountEUR().compareTo(BigDecimal.ZERO) < 0) {
                throw new AlwinValidationException(WRONG_DECLARATION_AMOUNT_MESSAGE);
            }
        }
    }

    /**
     * Wyznaczanie maksymalnej dozwolonej daty spłaty deklaracji
     *
     * @param activity       - czynność windykacyjna do sprawdzenia
     * @param expirationDate - data zakończenia zlecenia
     * @return maksymalna dozwolona data spłaty deklaracji
     */
    private Date determineMaxDeclarationDate(final ActivityDto activity, final Date expirationDate) {
        final List<ActivityDto> activities = findActiveActivitiesWithoutUpdated(activity, expirationDate);
        final Date maxDeclarationDate = dateProvider.getDateStartOfDayPlusDays(MAX_DECLARATION_DAY_END);
        if (isEmpty(activities)) {
            return maxDeclarationDate;
        }

        final Date nextActivityPlannedDate = activities.get(0).getPlannedDate();
        if (nextActivityPlannedDate.after(maxDeclarationDate)) {
            return maxDeclarationDate;
        }

        final Date maxDeclarationDateWithActivity = dateProvider.getDateStartOfDayPlusDays(MAX_DECLARATION_DAY_END_WITH_ACTIVITY);
        if (nextActivityPlannedDate.before(maxDeclarationDateWithActivity)) {
            return maxDeclarationDateWithActivity;
        }

        return nextActivityPlannedDate;
    }

    /**
     * Pobieranie anktywnych czynności z datą zaplanowania mniejszą lub równą od podanej.
     * Pomijana jest aktualizowana czynność windykacyjna.
     * Czynności posortowane są po dacie zaplanowania.
     *
     * @param activity       - czynność windykacyjna do sprawdzenia
     * @param expirationDate - data zakończenia zlecenia
     * @return lista czynności windykacyjnych
     */
    private List<ActivityDto> findActiveActivitiesWithoutUpdated(final ActivityDto activity, final Date expirationDate) {
        final List<ActivityDto> activities = activityService.findActiveIssueActivities(activity.getIssueId(), expirationDate);
        if (activity.getId() != null) {
            activities.removeIf(activityDto -> activityDto.getId().equals(activity.getId()));
        }
        return activities;
    }

    /**
     * Sprawdza czy data zaplanowania jest wcześniejsza niż maksymalna data zaplanowania czynności zaplanowanej, jeśli nie to rzuca wyjątek
     * {@link AlwinValidationException}
     *
     * @param plannedDate - data zaplanowania
     * @param state       - status czynności
     */
    private void validateMaxPlannedDate(final Date plannedDate, final ActivityStateDto state) {
        if (plannedDate == null || state == null) {
            return;
        }
        if (PLANNED.getKey().equals(state.getKey())) {
            final Date maxPlannedDate = dateProvider.getDateStartOfDayPlusDays(MAX_DECLARATION_DAY_END);
            if (plannedDate.after(maxPlannedDate)) {
                throw new AlwinValidationException(PLANNED_DATE_TOO_LATE_MESSAGE);
            }
        }
    }

    /**
     * Sprawdza czy czynność nie jest zakończona, jeśli jest to rzuca wyjątek
     * Sprawdza czy typ czynności nie został zmieniony, jeśli został zmieniony to rzuca wyjątek
     * {@link AlwinValidationException}
     *
     * @param activity czynność windykacyjna do sprawdzenia
     */
    private void validateActivityStateAndType(final ActivityDto activity) {
        if (activity.getId() == null) {
            return;
        }
        final Optional<ActivityDto> activityById = activityService.findActivityById(activity.getId());
        if (!activityById.isPresent()) {
            throw new AlwinValidationException(WRONG_ACTIVITY_ID);
        }
        final ActivityDto activityDto = activityById.get();
        if (ActivityStateDto.CLOSED_STATUSES.contains(activityDto.getState())) {
            throw new AlwinValidationException(WRONG_ACTIVITY_STATE);
        }
        if (!activityDto.getActivityType().getId().equals(activity.getActivityType().getId())) {
            throw new AlwinValidationException(WRONG_ACTIVITY_TYPE);
        }
    }

    public IssueTypeName validateIssueType(final String issueType) {
        try {
            return IssueTypeName.valueOf(issueType);
        } catch (final IllegalArgumentException e) {
            throw new AlwinValidationException(format(WRONG_ISSUE_TYPE, issueType));
        }
    }

    public void validate(final List<ActivityDetailPropertyDto> detailProperties) {
        if (CollectionUtils.isEmpty(detailProperties)) {
            return;
        }

        for (final ActivityDetailPropertyDto property : detailProperties) {
            if (!activityDetailPropertyService.checkIfDetailPropertyExists(property.getId())) {
                throw new AlwinValidationException(String.format(MISSING_DETAIL_PROPERTY, property.getId()));
            }
        }
    }
}

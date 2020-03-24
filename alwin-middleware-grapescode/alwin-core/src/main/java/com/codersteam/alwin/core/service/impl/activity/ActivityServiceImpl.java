package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType;
import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.common.sort.ActivitySortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.activity.*;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.*;
import com.codersteam.alwin.jpa.activity.*;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.Tag;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey;
import com.codersteam.alwin.jpa.type.ActivityState;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;
import com.codersteam.alwin.jpa.type.IssueState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.util.DateUtils.datePlusOneDay;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.*;
import static com.codersteam.alwin.jpa.type.ActivityState.*;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.COMMENT;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.OUTGOING_SMS;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final IssueDao issueDao;
    private final ActivityDao activityDao;
    private final ActivityTypeService activityTypeService;
    private final ActivityTypeDao activityTypeDao;
    private final OperatorDao operatorDao;
    private final AlwinMapper alwinMapper;
    private final DateProvider dateProvider;
    private final DefaultIssueActivityDao defaultIssueActivityDao;
    private final TagDao tagDao;
    private final ActivityDetailPropertyDao activityDetailPropertyDao;

    @Inject
    public ActivityServiceImpl(final IssueDao issueDao, final ActivityDao activityDao, final ActivityTypeService activityTypeService,
                               final ActivityTypeDao activityTypeDao, final OperatorDao operatorDao, final AlwinMapper alwinMapper,
                               final DateProvider dateProvider, final DefaultIssueActivityDao defaultIssueActivityDao,
                               final TagDao tagDao, final ActivityDetailPropertyDao activityDetailPropertyDao) {
        this.issueDao = issueDao;
        this.activityDao = activityDao;
        this.activityTypeService = activityTypeService;
        this.activityTypeDao = activityTypeDao;
        this.operatorDao = operatorDao;
        this.alwinMapper = alwinMapper;
        this.dateProvider = dateProvider;
        this.defaultIssueActivityDao = defaultIssueActivityDao;
        this.tagDao = tagDao;
        this.activityDetailPropertyDao = activityDetailPropertyDao;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void createNewActivityForIssue(final ActivityDto activity) {
        setCurrentBalanceForDeclaration(activity);
        final Activity activityToCreate = alwinMapper.map(activity, Activity.class);
        activityDao.createNewActivityForIssue(activityToCreate);

        final Long issueId = activityToCreate.getIssue().getId();
        updatePlannedDate(issueId, activityToCreate.getDeclarations(), activityToCreate.getState());
        findActiveIssueActivitiesAndUpdatePlannedDate(issueId, dateProvider.getCurrentDateStartOfDay());
        updateIssueStateAndTags(issueId);
    }

    @Override
    @Transactional
    public void createDefaultActivitiesForIssue(final Long issueId, final Long issueTypeId) {
        createDefaultActivities(issueId, issueTypeId, null);
        LOG.info("Default activities created for issueId: {}", issueId);
    }

    @Override
    public void createAndAssignDefaultActivitiesForIssue(final Long issueId, final Long issueTypeId, final Long assigneeId) {
        final Operator operator = operatorDao.get(assigneeId).orElse(null);
        createDefaultActivities(issueId, issueTypeId, operator);
        LOG.info("Default activities created for issueId: {}, assigneeId: {}", issueId, assigneeId);
    }

    private void createDefaultActivities(final Long issueId, final Long issueTypeId, final Operator operator) {
        final Optional<Issue> issue = issueDao.get(issueId);
        if (!issue.isPresent()) {
            return;
        }
        final List<DefaultIssueActivity> defaultActivities = defaultIssueActivityDao.findDefaultActivities(issueTypeId);
        defaultActivities.forEach(defaultActivity -> activityDao.create(createNewActivity(issue.get(), defaultActivity, operator)));
    }

    @Override
    public List<ActivityDto> findAllIssueActivities(final Long issueId) {
        final List<Activity> issueActivities = activityDao.findAllIssueActivities(issueId);
        return alwinMapper.mapAsList(issueActivities, ActivityDto.class);
    }

    @Override
    public List<ActivityDto> findActiveIssueActivities(final Long issueId, final Date maxPlannedDate) {
        final List<Activity> issueActivities = activityDao.findActiveIssueActivities(issueId, maxPlannedDate);
        return alwinMapper.mapAsList(issueActivities, ActivityDto.class);
    }

    @Override
    public Page<ActivityDto> findAllIssueActivities(final Long issueId, final ActivitiesSearchCriteria searchCriteria,
                                                    Map<ActivitySortField, SortOrder> sortCriteria) {
        final List<Activity> activities = activityDao.findAllIssueActivities(issueId, searchCriteria, sortCriteria);
        final List<ActivityDto> mappedActivities = alwinMapper.mapAsList(activities, ActivityDto.class);
        fillActivityTypesInExecutedState(issueId, mappedActivities);

        markActivitiesDetailAsRequired(mappedActivities);

        return new Page<>(mappedActivities, activityDao.findAllIssueActivitiesCount(issueId, searchCriteria));
    }

    //TODO dodać testy dla wymiany obiektów ActivityTypeDto na takie ze stanem executed
    private void fillActivityTypesInExecutedState(final Long issueId, final List<ActivityDto> mappedActivities) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final Map<Long, ActivityTypeDto> activityTypesById =
                activityTypeService.findActivityTypeByIssueTypeGroupedByState(issue.getIssueType().getName(), null).getExecuted().stream()
                        .collect(Collectors.toMap(ActivityTypeDto::getId, Function.identity()));
        mappedActivities.forEach(activityDto -> activityDto.setActivityType(activityTypesById.get(activityDto.getActivityType().getId())));
    }

    private void markActivitiesDetailAsRequired(final List<ActivityDto> mappedActivities) {
        mappedActivities.forEach(this::markActivityDetailAsRequired);
    }

    private void markActivityDetailAsRequired(final ActivityDto activityDto) {
        if (activityDto.getActivityType() == null || activityDto.getActivityType().getActivityTypeDetailProperties() == null) {
            activityDto.getActivityDetails().forEach(activityDetailDto -> activityDetailDto.setRequired(Boolean.FALSE));
            return;
        }
        final Map<Long, Boolean> requiredExecutedActivities = activityDto.getActivityType().getActivityTypeDetailProperties()
                .stream()
                .filter(a -> a.getState() == ActivityTypeDetailPropertyType.EXECUTED)
                .collect(Collectors.toMap(a -> a.getActivityDetailProperty().getId(), ActivityTypeDetailPropertyDto::getRequired));

        activityDto.getActivityDetails().forEach(activityDetailDto ->
                activityDetailDto.setRequired(requiredExecutedActivities.get(activityDetailDto.getDetailProperty().getId())));
    }

    @Override
    public void closeIssueActivities(final Long issueId) {
        final List<Activity> activities = activityDao.findActiveIssueActivities(issueId);
        activities.forEach(activity -> {
            activity.setState(ActivityState.CANCELED);
            activity.setActivityDate(dateProvider.getCurrentDate());
            activityDao.update(activity);
        });
    }

    @Override
    @Transactional
    public void updateActivity(final ActivityDto activity, final boolean managed) {
        final Optional<Activity> activityOptional = activityDao.get(activity.getId());
        if (!activityOptional.isPresent()) {
            return;
        }
        final Activity activityToUpdate = activityOptional.get();
        final Optional<ActivityType> activityType = activityTypeDao.get(activity.getActivityType().getId());
        if (!activityType.isPresent()) {
            return;
        }
        updateActivity(activity, activityToUpdate, activityType.get(), managed);
        activityDao.update(activityToUpdate);
        final Long issueId = activityToUpdate.getIssue().getId();
        updatePlannedDate(issueId, activityToUpdate.getDeclarations(), activityToUpdate.getState());
        findActiveIssueActivitiesAndUpdatePlannedDate(issueId, dateProvider.getCurrentDateStartOfDay());
        updateIssueStateAndTags(issueId);
    }

    private void updateActivity(final ActivityDto activity, final Activity activityToUpdate, final ActivityType activityType, final boolean managed) {
        activityToUpdate.setActivityType(activityType);
        activityToUpdate.setActivityDate(activity.getActivityDate());
        activityToUpdate.setPlannedDate(activity.getPlannedDate());
        activityToUpdate.setCreationDate(activity.getCreationDate());
        activityToUpdate.setCharge(activity.getCharge());
        activityToUpdate.setInvoiceId(activity.getInvoiceId());
        activityToUpdate.setState(ActivityState.valueOf(activity.getState().getKey()));
        activityToUpdate.getActivityDetails().clear();
        if (isNotEmpty(activity.getActivityDetails())) {
            activityToUpdate.getActivityDetails().addAll(alwinMapper.mapAsList(activity.getActivityDetails(), ActivityDetail.class));
        }
        activityToUpdate.getDeclarations().clear();
        if (isNotEmpty(activity.getDeclarations())) {
            setCurrentBalanceForDeclaration(activity);
            activityToUpdate.getDeclarations().addAll(alwinMapper.mapAsList(activity.getDeclarations(), Declaration.class));
        }
        if (managed) {
            activityToUpdate.setOperator(alwinMapper.map(activity.getOperator(), Operator.class));
        }
    }

    private void setCurrentBalanceForDeclaration(final ActivityDto activity) {
        activity.getDeclarations().forEach(declarationDto -> {
            final Issue issue = getIssue(activity.getIssueId());
            //TODO: można się pozbyć tego mapowania, bo te same pola są w encji co tutaj używamy niżej i nie potrzebujemy dto
            final IssueDto issueDto = alwinMapper.map(issue, IssueDto.class);

            if (declarationDto.getCurrentBalancePLN() == null) {
                declarationDto.setCurrentBalancePLN(issueDto.getCurrentBalancePLN());
            }
            if (declarationDto.getCurrentBalanceEUR() == null) {
                declarationDto.setCurrentBalanceEUR(issueDto.getCurrentBalanceEUR());
            }
        });
    }

    @Override
    public Optional<ActivityDto> findActivityById(final Long activityId) {
        return activityDao.get(activityId).map(this::mapActivity);
    }

    @Override
    public Long createOutgoingSmsActivity(final Long issueId, final Long operatorId, final String phoneNumber, final String smsMessage) {
        final Issue issue = getIssue(issueId);
        final Operator operator = getOperator(operatorId);
        final ActivityType activityType = getActivityType(OUTGOING_SMS);
        final List<ActivityDetail> activityDetails = prepareActivityDetails(phoneNumber, smsMessage, activityType);

        final Activity newActivity = createExecutedActivity(issue, operator, activityType, activityDetails);
        activityDao.create(newActivity);

        return newActivity.getId();
    }

    @Override
    public void createCommentActivity(final Long issueId, final Long operatorId, final String comment) {
        final Issue issue = getIssue(issueId);
        final Operator operator = getOperator(operatorId);
        final ActivityType activityType = getActivityType(COMMENT);
        final ActivityDetailProperty activityDetailProperty = getActivityDetailProperty(REMARK);
        final ActivityDetail activityDetail = createActivityDetail(comment, activityDetailProperty);

        final Activity newActivity = createExecutedActivity(issue, operator, activityType, singletonList(activityDetail));
        activityDao.create(newActivity);
    }

    @Override
    public Optional<ActivityDto> findOldestPlannedActivityForIssue(final Long issueId) {
        final Date today = dateProvider.getCurrentDateStartOfDay();
        return activityDao.findOldestPlannedActivityForIssue(issueId, today)
                .map(this::mapActivity)
                .map(activityDto -> fillActivityTypesInExecutedState(issueId, activityDto));
    }

    private ActivityDto fillActivityTypesInExecutedState(final Long issueId, final ActivityDto activityDto) {
        fillActivityTypesInExecutedState(issueId, singletonList(activityDto));
        return activityDto;
    }


    private ActivityDto mapActivity(final Activity activity) {
        final ActivityDto activityDto = alwinMapper.map(activity, ActivityDto.class);
        markActivityDetailAsRequired(activityDto);
        return activityDto;
    }

    private ActivityDetailProperty getActivityDetailProperty(final ActivityDetailPropertyKey remark) {
        return activityDetailPropertyDao.findActivityDetailProperty(remark).orElseThrow(() -> new
                EntityNotFoundException(remark));
    }

    @Override
    public void updateDefaultIssueActivity(final long defaultIssueActivityId, final DefaultIssueActivityDto updatedDefaultIssueActivityDto) {
        final DefaultIssueActivity updatedDefaultIssueActivity = alwinMapper.map(updatedDefaultIssueActivityDto, DefaultIssueActivity.class);
        final DefaultIssueActivity currentDefaultIssueActivity = defaultIssueActivityDao.findDefaultIssueActivity(defaultIssueActivityId);

        final DefaultIssueActivity newDefaultIssueActivity = alwinMapper.map(currentDefaultIssueActivity, DefaultIssueActivity.class);
        newDefaultIssueActivity.setId(null);
        newDefaultIssueActivity.setVersion(currentDefaultIssueActivity.getVersion() + 1);
        newDefaultIssueActivity.setDefaultDay(updatedDefaultIssueActivity.getDefaultDay());

        defaultIssueActivityDao.create(newDefaultIssueActivity);
    }

    @Override
    public void updateActivityType(final long activityTypeId, final ActivityTypeWithDetailPropertiesDto activityTypeDto) {
        final ActivityType activityType = alwinMapper.map(activityTypeDto, ActivityType.class);
        activityType.setId(activityTypeId);
        if (isNotEmpty(activityType.getActivityTypeDetailProperties())) {
            activityType.getActivityTypeDetailProperties().forEach(prop -> prop.setActivityType(activityType));
        }
        if (isNotEmpty(activityTypeDto.getDetailProperties())) {
            activityTypeDto.getDetailProperties().forEach(prop -> {
                final ActivityDetailProperty detailProperty = alwinMapper.map(prop, ActivityDetailProperty.class);
                if (isNotEmpty(detailProperty.getDictionaryValues())) {
                    detailProperty.getDictionaryValues().forEach(dict -> dict.setActivityDetailProperty(detailProperty));
                }
                activityDetailPropertyDao.update(detailProperty);
            });
        }
        activityTypeDao.update(activityType);
    }

    private void updateIssueStateAndTags(final Long issueId) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final Tag overdueTag = tagDao.findOverdueTag();
        if (IssueState.NEW.equals(issue.getIssueState())) {
            issue.setIssueState(IssueState.IN_PROGRESS);
        }
        issue.getTags().remove(overdueTag);
        issueDao.update(issue);
    }

    private Issue getIssue(final Long issueId) {
        return issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
    }

    private Operator getOperator(final Long operatorId) {
        return operatorDao.get(operatorId).orElseThrow(() -> new EntityNotFoundException(operatorId));
    }

    private ActivityType getActivityType(final ActivityTypeKey key) {
        return activityTypeDao.findByKey(key).orElseThrow(() -> new EntityNotFoundException(key));
    }

    private List<ActivityDetail> prepareActivityDetails(final String phoneNumber, final String smsMessage, final ActivityType activityType) {
        final Set<ActivityTypeDetailProperty> activityTypeDetailProperties = activityType.getActivityTypeDetailProperties();
        final Map<ActivityDetailPropertyKey, ActivityDetailProperty> propertyMap = new LinkedHashMap<>();

        activityTypeDetailProperties.forEach(activityTypeDetailProperty -> propertyMap.put(activityTypeDetailProperty.getActivityDetailProperty().getKey(),
                activityTypeDetailProperty.getActivityDetailProperty()));

        final ActivityDetail phoneNumberActivityDetail = createActivityDetail(phoneNumber, propertyMap.get(PHONE_NUMBER));
        final ActivityDetail smsMessageActivityDetail = createActivityDetail(smsMessage, propertyMap.get(MESSAGE_CONTENT));
        return asList(phoneNumberActivityDetail, smsMessageActivityDetail);
    }

    private ActivityDetail createActivityDetail(final String value, final ActivityDetailProperty detailProperty) {
        final ActivityDetail phoneNumberActivityDetail = new ActivityDetail();
        phoneNumberActivityDetail.setDetailProperty(detailProperty);
        phoneNumberActivityDetail.setValue(value);
        return phoneNumberActivityDetail;
    }

    private Activity createNewActivity(final Issue issue, final DefaultIssueActivity defaultActivity, final Operator operator) {
        final Date currentDate = dateProvider.getCurrentDate();
        final Date plannedDate = dateProvider.getDateStartOfDayPlusDays(defaultActivity.getDefaultDay());
        final ActivityType activityType = defaultActivity.getActivityType();
        return createNewActivity(issue, operator, activityType, null, plannedDate, currentDate, PLANNED, null);
    }

    private Activity createExecutedActivity(final Issue issue, final Operator operator, final ActivityType activityType, final List<ActivityDetail> activityDetails) {
        final Date currentDate = dateProvider.getCurrentDate();
        return createNewActivity(issue, operator, activityType, currentDate, currentDate, currentDate, EXECUTED, activityDetails);
    }

    private Activity createNewActivity(final Issue issue, final Operator operator, final ActivityType activityType, final Date activityDate,
                                       final Date plannedDate, final Date creatingDate, final ActivityState state, final List<ActivityDetail> activityDetails) {
        final Activity activity = new Activity();
        activity.setIssue(issue);
        activity.setOperator(operator);
        activity.setActivityType(activityType);
        activity.setActivityDate(activityDate);
        activity.setPlannedDate(plannedDate);
        activity.setCreationDate(creatingDate);
        activity.setState(state);
        activity.setActivityDetails(activityDetails);
        activity.setCharge(BigDecimal.ZERO);
        return activity;
    }

    /**
     * Przesunięcie daty zaplanowania otwartych czynności windykacyjnych, które są zaplanowane przed terminem spłaty deklaracji.
     * Daty przesuwane są na dzień po terminie spłaty deklaracji.
     * Daty są przesuwane tylko dla wykonanej czynności windykacyjnej.
     *
     * @param issueId      - identyfikator zelcenia
     * @param declarations - deklaracje spłaty
     * @param state        - status czynności
     */
    private void updatePlannedDate(final Long issueId, final List<Declaration> declarations, final ActivityState state) {
        if (OPEN_STATES.contains(state) && isEmpty(declarations)) {
            return;
        }
        getMinDeclarationDate(declarations).ifPresent(minDeclarationDate -> findActiveIssueActivitiesAndUpdatePlannedDate(issueId, minDeclarationDate));
    }

    /**
     * Przesunięcie daty zaplanowania otwartych czynności windykacyjnych, które są zaplanowane przed podanym terminem.
     * Daty przesuwane są na dzień po podanym terminie.
     *
     * @param issueId        - identyfikator zelcenia
     * @param maxPlannedDate - data spłaty deklaracji
     */
    private void findActiveIssueActivitiesAndUpdatePlannedDate(final Long issueId, final Date maxPlannedDate) {
        final List<Activity> issueActivities = activityDao.findActiveIssueActivities(issueId, maxPlannedDate);
        if (isEmpty(issueActivities)) {
            return;
        }

        final Date newPlannedDate = datePlusOneDay(maxPlannedDate);
        issueActivities.forEach(issueActivity -> {
            updateActivityPlannedDate(issueActivity, newPlannedDate);
            clearPlannedActivityOperator(issueActivity);
        });
    }

    /**
     * Wyczyszczenie operatora przypisanego do czynności zaplanowanej
     *
     * @param activity - czynność windykacyjna
     */
    private void clearPlannedActivityOperator(final Activity activity) {
        activity.setOperator(null);
        activityDao.update(activity);
    }

    /**
     * Wyznaczanie najwcześniejszej daty spłaty deklracji
     *
     * @param declarations - deklaracje spłat
     * @return najwcześniejsza data deklaracji
     */
    private Optional<Date> getMinDeclarationDate(final List<Declaration> declarations) {
        return declarations.stream().map(Declaration::getDeclaredPaymentDate).filter(Objects::nonNull).min(Date::compareTo);
    }

    /**
     * Aktualizacja daty zaplanowania czynnośći windykacyjnej
     *
     * @param activity    - czynność windykacyjna
     * @param plannedDate - data zaplanowania czynności
     */
    private void updateActivityPlannedDate(final Activity activity, final Date plannedDate) {
        activity.setPlannedDate(plannedDate);
        activityDao.update(activity);
    }
}

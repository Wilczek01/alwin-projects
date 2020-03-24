package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.search.util.PostalCodeUtils;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.postalcode.PostalCodeService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService;
import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService;
import com.codersteam.alwin.core.api.service.issue.IssueConfigurationService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.issue.TerminateIssueResult;
import com.codersteam.alwin.core.local.DPDService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.core.util.DateUtils;
import com.codersteam.alwin.core.util.IssueUtils;
import com.codersteam.alwin.db.dao.*;
import com.codersteam.alwin.jpa.OperatorsQueue;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueTerminationRequest;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeTransition;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.operator.Permission;
import com.codersteam.alwin.jpa.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.issue.IssueTypeName.DIRECT_DEBT_COLLECTION;
import static com.codersteam.alwin.core.api.model.common.Page.emptyPage;
import static com.codersteam.alwin.core.api.service.issue.TerminateIssueResult.ISSUE_TERMINATION_REQUEST_CREATED;
import static com.codersteam.alwin.core.api.service.issue.TerminateIssueResult.ISSUE_WAS_TERMINATED;
import static com.codersteam.alwin.core.service.impl.issue.preparator.BalancePreparator.fillBalance;
import static com.codersteam.alwin.core.service.impl.issue.preparator.DatePreparator.fillDates;
import static com.codersteam.alwin.core.util.DateUtils.isBefore;
import static com.codersteam.alwin.core.util.IssueUtils.getCompanySegmentFromIssue;
import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;
import static com.codersteam.alwin.jpa.type.IssueState.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Stateless
@LocalBean
@Local(IssueService.class)
public class IssueServiceImpl implements IssueService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int FIND_WORK_FOR_USER_REPEAT_COUNT = 10;

    private IssueTypeTransitionDao issueTypeTransitionDao;
    private OperatorDao operatorDao;
    private DateProvider dateProvider;
    private ActivityService activityService;
    private IssueTerminationRequestDao issueTerminationRequestDao;
    private IssueInvoiceDao issueInvoiceDao;
    private BalanceCalculatorService balanceCalculatorService;
    private DPDService dpdService;
    private IssueBalanceUpdaterService issueBalanceUpdaterService;
    private IssueConfigurationService issueConfigurationService;
    private OperatorsQueueDao operatorsQueueDao;
    private IssueDao issueDao;
    private ActivityDao activityDao;
    private PostalCodeService postalCodeService;
    private AlwinMapper mapper;

    public IssueServiceImpl() {
    }

    @Inject
    public IssueServiceImpl(IssueDao issueDao,
                            IssueTypeTransitionDao issueTypeTransitionDao,
                            OperatorDao operatorDao,
                            DateProvider dateProvider,
                            ActivityService activityService,
                            IssueTerminationRequestDao issueTerminationRequestDao,
                            IssueInvoiceDao issueInvoiceDao,
                            ActivityDao activityDao,
                            BalanceCalculatorService balanceCalculatorService,
                            DPDService dpdService,
                            IssueBalanceUpdaterService issueBalanceUpdaterService,
                            IssueConfigurationService issueConfigurationService,
                            PostalCodeService postalCodeService,
                            OperatorsQueueDao operatorsQueueDao,
                            AlwinMapper mapper) {
        this.issueTypeTransitionDao = issueTypeTransitionDao;
        this.operatorDao = operatorDao;
        this.dateProvider = dateProvider;
        this.activityService = activityService;
        this.issueTerminationRequestDao = issueTerminationRequestDao;
        this.issueInvoiceDao = issueInvoiceDao;
        this.balanceCalculatorService = balanceCalculatorService;
        this.dpdService = dpdService;
        this.issueBalanceUpdaterService = issueBalanceUpdaterService;
        this.issueConfigurationService = issueConfigurationService;
        this.operatorsQueueDao = operatorsQueueDao;
        this.issueDao = issueDao;
        this.activityDao = activityDao;
        this.postalCodeService = postalCodeService;
        this.mapper = mapper;
    }

    @Override
    public Page<IssueDto> findOperatorIssues(final Long assigneeId, final IssueSearchCriteria searchCriteria) {
        final List<Issue> issues = issueDao.findOperatorIssues(assigneeId, searchCriteria);
        if (isEmpty(issues)) {
            return emptyPage();
        }
        final List<IssueDto> mappedIssues = mapIssues(issues);
        return new Page<>(mappedIssues, issueDao.findOperatorIssuesCount(assigneeId, searchCriteria));
    }

    @Override
    public Page<IssueDto> findAllIssues(final OperatorType operatorType, final IssuesSearchCriteria criteria,
                                        Map<IssueSortField, SortOrder> sortCriteria) {
        final List<Issue> issues = issueDao.findAllFilteredIssues(operatorType.name(), criteria, NONE, emptyList(), sortCriteria);
        if (isEmpty(issues)) {
            return emptyPage();
        }
        final List<IssueDto> mappedIssues = mapIssues(issues);
        return new Page<>(mappedIssues, issueDao.findAllFilteredIssuesCount(operatorType.name(), criteria, NONE, emptyList()));
    }

    @Override
    public Page<IssueForFieldDebtCollectorDto> findAllFieldDebtIssues(final Long operatorId, final OperatorType operatorType,
                                                                      final IssuesSearchCriteria criteria,
                                                                      Map<IssueSortField, SortOrder> sortCriteria) {
        final List<Issue> issues = issueDao.findAllFilteredIssues(operatorType.name(), criteria, NONE, emptyList(), sortCriteria);
        if (isEmpty(issues)) {
            return emptyPage();
        }
        final List<IssueForFieldDebtCollectorDto> mappedIssues = mapper.mapAsList(issues, IssueForFieldDebtCollectorDto.class);
        fillReminderMark(operatorId, issues, mappedIssues);
        return new Page<>(mappedIssues, issueDao.findAllFilteredIssuesCount(operatorType.name(), criteria, NONE, emptyList()));
    }

    private void fillReminderMark(final Long operatorId, final List<Issue> issues,
                                  final List<IssueForFieldDebtCollectorDto> mappedIssues) {
        final Map<Long, IssueForFieldDebtCollectorDto.ReminderMark> issueIdToReminderMark = issues.stream()
                .collect(Collectors.toMap(
                        Issue::getId, iss -> {
                            final Date currentDate = dateProvider.getCurrentDate();
                            return iss.getActivities()
                                    .stream()
                                    .filter(activity -> ActivityState.OPEN_STATES.contains(activity.getState()))
                                    .filter(activity -> activity.getOperator() != null && Objects.equals(operatorId, activity.getOperator().getId()))
                                    .map(Activity::getPlannedDate)
                                    .min(Date::compareTo)
                                    .filter(plannedDate -> DateUtils.isBeforeOrSameDate(plannedDate, currentDate))
                                    .map(oldestPlannedDate -> {
                                        if (isBefore(oldestPlannedDate, currentDate)) {
                                            return IssueForFieldDebtCollectorDto.ReminderMark.HIGH;
                                        } else {
                                            return IssueForFieldDebtCollectorDto.ReminderMark.NORMAL;
                                        }
                                    })
                                    .orElse(IssueForFieldDebtCollectorDto.ReminderMark.NONE);
                        }
                ));

        mappedIssues
                .forEach(issue -> issue.setReminderMark(issueIdToReminderMark.get(issue.getId())));
    }

    @Override
    public IssueDto findIssue(final Long issueId) {
        return issueDao.get(issueId)
                .map(this::mapIssue)
                .orElse(null);
    }

    @Override
    public OperatedIssueDto findOperatedIssueAndUpdateBalance(final Long issueId, final OperatorType operatorType) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final boolean editable = isIssueTypeMatchOperatorType(operatorType, issue.getIssueType());
        final IssueDto issueDto = issueBalanceUpdaterService.getIssueWithUpdatedBalance(issueId);
        return new OperatedIssueDto(issueDto, editable);
    }

    private boolean isIssueTypeMatchOperatorType(final OperatorType operatorType, final IssueType issueType) {
        final OperatorNameType operatorNameType = mapper.map(operatorType, OperatorNameType.class);
        final List<OperatorNameType> operatorNameTypes = issueType.getOperatorTypes()
                .stream().map(com.codersteam.alwin.jpa.operator.OperatorType::getTypeName).collect(toList());
        return operatorNameTypes.contains(operatorNameType);
    }

    @Override
    public boolean doesCompanyHaveActiveIssue(final Long extCustomerId) {
        return issueDao.findCompanyActiveIssueId(extCustomerId).isPresent();
    }

    @Override
    public Optional<Long> findCompanyActiveIssueId(final Long extCompanyId) {
        return issueDao.findCompanyActiveIssueId(extCompanyId);
    }

    @Override
    public List<IssueDto> findAllActiveIssues() {
        return mapIssues(issueDao.findAllActiveIssues());
    }

    /**
     * Przeliczenie salda zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return zlecenie w przypadku gdy ciągle jest w obsłudze (nie zostało zamknięte na skutek odpowiedniego salda)
     */
    public Optional<IssueDto> getRecalculatedIssueIfNotClosed(final Long issueId) {
        LOG.info("getRecalculatedIssueIfNotClosed: " + issueId);
        IssueDto issue = issueBalanceUpdaterService.getIssueWithUpdatedBalance(issueId);
        if (!IssueStateDto.CLOSED_ISSUE_STATES.contains(issue.getIssueState())) {
            return Optional.of(issue);
        }
        return Optional.empty();
    }

    public Optional<IssueDto> findIssueToWorkOn(final Long operatorId) {
        final List<IssueTypeName> operatorIssueTypes = operatorDao.findOperatorIssueTypes(operatorId);
        if (operatorIssueTypes.isEmpty()) {
            return Optional.empty();
        }
        final Date currentDate = dateProvider.getCurrentDate();
        int repeatCount = FIND_WORK_FOR_USER_REPEAT_COUNT;
        while (repeatCount > 0) {
            LOG.info("findIssueToWorkOn repeatCount: " + repeatCount);

            Optional<Activity> activityOp = findOldOrCurrentIssueForUserToWorkOn(operatorId, operatorIssueTypes);
            if (!activityOp.isPresent()) {
                activityOp = issueDao.findFutureActivityForUserToWorkOn(operatorId, operatorIssueTypes, currentDate);
            }

            if (!activityOp.isPresent()) {
                return Optional.empty();
            }

            Activity activity = activityOp.get();

            if (activity.getOperator() != null) {
                return Optional.of(mapIssue(activity.getIssue()));
            }

            final boolean assignedIssueToUser = assignNotAssignedIssueWithActivitiesToUser(activity.getId(), operatorId);
            if (assignedIssueToUser) {
                return Optional.of(mapIssue(activity.getIssue()));
            }

            repeatCount--;
        }
        return Optional.empty();
    }

    @Override
    public Page<CompanyIssueDto> findAllIssuesForCompanyExcludingGivenIssue(final Long companyId, final Long excludedIssueId,
                                                                            final IssueForCompanySearchCriteria searchCriteria) {

        final List<Issue> issues = issueDao.findAllIssuesForCompanyExcludingGivenIssue(companyId, excludedIssueId, searchCriteria);
        if (isEmpty(issues)) {
            return emptyPage();
        }
        final List<CompanyIssueDto> mappedIssues = mapper.mapAsList(issues, CompanyIssueDto.class);
        return new Page<>(mappedIssues, issueDao.findAllIssuesForCompanyExcludingGivenIssueCount(companyId, excludedIssueId, searchCriteria));
    }

    @Override
    public Page<IssueDto> findAllFilteredManagedIssues(final OperatorType operatorType, final IssuesSearchCriteria criteria,
                                                       Map<IssueSortField, SortOrder> sortCriteria) {
        final List<Issue> issues = issueDao.findAllFilteredIssues(operatorType.name(), criteria, CLOSED_ISSUE_STATES, OPEN_STATES, sortCriteria);
        if (isEmpty(issues)) {
            return emptyPage();
        }
        final List<IssueDto> mappedIssues = mapIssues(issues);
        return new Page<>(mappedIssues, issueDao.findAllFilteredIssuesCount(operatorType.name(), criteria, CLOSED_ISSUE_STATES, OPEN_STATES));
    }

    /**
     * Mapuje listę zleceń z encji do dto
     *
     * @param issues - lista zleceń
     * @return lista dto zleceń
     */
    private List<IssueDto> mapIssues(final List<Issue> issues) {
        return mapper.mapAsList(issues, IssueDto.class);
    }

    /**
     * Mapuje zlecenie z encji do dto
     *
     * @param issue - zlecenie
     * @return dto zlecenia
     */
    private IssueDto mapIssue(final Issue issue) {
        return mapper.map(issue, IssueDto.class);
    }

    @Override
    public List<Long> findNotManagedIssues(final OperatorType operatorType, final List<Long> issueIds) {
        return issueDao.findNotManagedIssues(operatorType.name(), issueIds);
    }

    @Override
    public void updateIssuesAssignment(final Long operatorId, final List<Long> issueIds) {
        activityDao.updateActivityOperator(operatorId, issueIds);
        issueDao.updateIssuesAssignment(operatorId, issueIds);
    }

    @Override
    public void updateIssuesAssignment(final Long operatorId, final OperatorType operatorType, final IssuesSearchCriteria criteria) {
        activityDao.updateActivityOperator(operatorId, operatorType.name(), criteria);
        issueDao.updateIssuesAssignment(operatorId, operatorType.name(), criteria);
    }

    @Override
    public void updateIssuesPriority(final Integer priority, final OperatorType operatorType, final IssuesSearchCriteria criteria) {
        issueDao.updateIssuesPriority(priority, operatorType.name(), criteria);
    }

    @Override
    public void updateIssuesPriority(final Integer priority, final List<Long> issueIds) {
        issueDao.updateIssuesPriority(priority, issueIds);
    }

    @Override
    public void updateIssuesTags(final Set<Long> tagIds, final OperatorType operatorType, final IssuesSearchCriteria criteria) {
        issueDao.assignIssuesTags(tagIds, operatorType.name(), criteria, CLOSED_ISSUE_STATES, OPEN_STATES);
    }

    @Override
    public void updateIssuesTags(final Set<Long> tagIds, final List<Long> issueIds) {
        issueDao.assignIssuesTags(tagIds, issueIds);
    }

    @Override
    public TerminateIssueResult terminateIssue(final Long issueId, final Long operatorId, final String terminationCause, final boolean excludedFromStats,
                                               final String exclusionCause) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        final Operator operator = operatorDao.get(operatorId).orElseThrow(() -> new EntityNotFoundException(operatorId));

        final Permission permission = operator.getPermission();
        final Date currentDate = dateProvider.getCurrentDate();
        if (permission != null && permission.isAllowedToTerminateIssue()) {
            terminateIssue(terminationCause, excludedFromStats, exclusionCause, issue, operator, currentDate);
            activityService.createCommentActivity(issueId, operatorId, terminationCause);
            return ISSUE_WAS_TERMINATED;
        }
        createIssueTerminationRequest(terminationCause, excludedFromStats, exclusionCause, issue, operator, currentDate);
        issue.setIssueState(WAITING_FOR_TERMINATION);
        issueDao.update(issue);
        activityService.createCommentActivity(issueId, operatorId, terminationCause);
        return ISSUE_TERMINATION_REQUEST_CREATED;
    }

    @Override
    public void updateIssueInvoicesExclusion(final Long issueId, final Long invoiceId, final boolean excluded) {
        issueInvoiceDao.updateIssueInvoicesExclusion(issueId, invoiceId, excluded);
    }

    @Override
    public void assignTags(final long issueId, final List<Long> tags) {
        issueDao.assignTags(issueId, tags);
    }

    private void createIssueTerminationRequest(final String terminationCause, final boolean excludedFromStats, final String exclusionCause, final Issue issue,
                                               final Operator operator, final Date currentDate) {
        final IssueTerminationRequest issueTerminationRequest = new IssueTerminationRequest();
        issueTerminationRequest.setRequestCause(terminationCause);
        issueTerminationRequest.setRequestOperator(operator);
        issueTerminationRequest.setExcludedFromStats(excludedFromStats);
        issueTerminationRequest.setExclusionFromStatsCause(exclusionCause);
        issueTerminationRequest.setState(IssueTerminationRequestState.NEW);
        issueTerminationRequest.setCreated(currentDate);
        issueTerminationRequest.setUpdated(currentDate);
        issueTerminationRequest.setIssue(issue);
        issueTerminationRequestDao.create(issueTerminationRequest);
    }

    private void terminateIssue(final String terminationCause, final boolean excludedFromStats, final String exclusionCause, final Issue issue,
                                final Operator operator, final Date currentDate) {
        issue.setExcludedFromStats(excludedFromStats);
        issue.setExclusionCause(exclusionCause);
        issue.setTerminationCause(terminationCause);
        issue.setTerminationDate(currentDate);
        issue.setTerminationOperator(operator);
        issue.setIssueState(CANCELED);

        issueInvoiceDao.updateIssueInvoicesFinalBalance(issue.getId());
        issueDao.update(issue);
    }

    private Date calculateExpirationDate(IssueTypeName issueTypeName, Segment segment, Date dpdStartDate) {
        IssueTypeConfigurationDto issueConfiguration = issueConfigurationService.findIssueTypeConfiguration(issueTypeName, segment);
        return DateUtils.datePlusDays(dpdStartDate, issueConfiguration.getDuration() + issueConfiguration.getDpdStart());
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void closeIssueWithActivitiesAndCreateChildIssueIfNeeded(final Long issueId) {
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        issue.setIssueState(DONE);
        issueDao.update(issue);
        activityService.closeIssueActivities(issue.getId());
        createChildIssueIfNeeded(issue);
    }

    @Override
    public List<Long> findIssueIdsWithUnpaidDeclarations(final Date declaredPaymentDateStart, final Date declaredPaymentDateEnd) {
        return issueDao.findIssueIdsWithUnpaidDeclarations(declaredPaymentDateStart, declaredPaymentDateEnd);
    }

    @Override
    public Set<OperatorTypeDto> findIssuesOperatorTypes(final List<Long> issuesIds) {
        final List<IssueType> issueTypes = issueDao.findOperatorTypesForIssues(issuesIds);
        return getCommonIssueOperatorType(issueTypes);
    }

    private Set<OperatorTypeDto> getCommonIssueOperatorType(final List<IssueType> issueTypes) {
        if (issueTypes.isEmpty()) {
            return emptySet();
        }
        final Set<com.codersteam.alwin.jpa.operator.OperatorType> operatorTypes = new HashSet<>(issueTypes.get(0).getOperatorTypes());
        for (final IssueType issueType : issueTypes) {
            operatorTypes.retainAll(issueType.getOperatorTypes());
        }

        return mapper.mapAsSet(operatorTypes, OperatorTypeDto.class);
    }

    @Override
    public Set<OperatorTypeDto> findIssuesOperatorTypes(final OperatorType operatorType, final IssuesSearchCriteria criteria) {
        final List<IssueType> issueTypes = issueDao.findOperatorTypesForIssues(operatorType.name(), criteria, NONE, emptyList());
        return getCommonIssueOperatorType(issueTypes);
    }

    private void createChildIssueIfNeeded(final Issue issue) {
        final List<IssueTypeTransition> issueTypeTransitions = issueTypeTransitionDao.findByClosedIssueType(issue.getIssueType());
        if (isEmpty(issueTypeTransitions)) {
            return;
        }
        for (final IssueTypeTransition transition : issueTypeTransitions) {
            if (meetCondition(transition)) {
                createIssueWithActivities(issue, transition);
                return;
            }
        }
    }

    private boolean meetCondition(final IssueTypeTransition transition) {
        return transition.getCondition() == IssueTypeTransitionCondition.NONE;
    }

    private void createIssueWithActivities(final Issue issue, final IssueTypeTransition transition) {
        final Issue childIssue = createIssue(issue, transition);
        issueDao.create(childIssue);
        final Map<Currency, Balance> balances = balanceCalculatorService.recalculateBalanceForIssue(childIssue.getId());
        fillBalance(childIssue, balances);
        issueDao.update(childIssue);
        activityService.createDefaultActivitiesForIssue(childIssue.getId(), childIssue.getIssueType().getId());
        assignOperatorByPostalCodeForDirectDebtCollection(childIssue);
        LOG.info("New issue id={} type={} automatically created from expired issue id={} type={}",
                childIssue.getId(),
                childIssue.getIssueType().getName(),
                issue.getId(),
                issue.getIssueType().getName());
    }

    private Issue createIssue(final Issue issue, final IssueTypeTransition transition) {
        Issue childIssue = mapper.map(issue, Issue.class);
        IssueType childIssueType = transition.getChildIssueType();
        IssueTypeConfigurationDto issueTypeConfiguration = issueConfigurationService.findIssueTypeConfiguration(childIssueType.getName(),
                getCompanySegmentFromIssue(issue));
        Date dpdStartDate = dpdService.calculateDPDStartDateForChildIssue(childIssue.getIssueInvoices(), IssueUtils.getExtCompanyId(childIssue),
                issueTypeConfiguration.getMinBalanceStart());
        Date expirationDate = calculateExpirationDate(childIssueType.getName(), findSegment(issue), dpdStartDate);
        childIssue = fillDates(childIssue, dateProvider.getCurrentDateStartOfDay(), expirationDate, dpdStartDate);
        childIssue.setParentIssue(issue);
        childIssue.setIssueState(NEW);
        childIssue.setIssueType(childIssueType);
        childIssue.getTags().clear();
        childIssue.getTags().addAll(issue.getTags());
        return childIssue;
    }

    private Segment findSegment(final Issue issue) {
        if (issue.getCustomer() != null && issue.getCustomer().getCompany() != null && issue.getCustomer().getCompany().getSegment() != null) {
            return issue.getCustomer().getCompany().getSegment();
        }
        LOG.error("Cannot find segment for issue with id {}", issue.getId());
        return Segment.A;
    }

    private Optional<Activity> findOldOrCurrentIssueForUserToWorkOn(Long userId,
                                                                    List<IssueTypeName> operatorIssueTypes) {
        return operatorsQueueDao.getOperatorsQueue(userId, operatorIssueTypes)
                .map(OperatorsQueue::getActivityId)
                .flatMap(activityDao::get);
    }

    @Transactional
    public boolean assignNotAssignedIssueWithActivitiesToUser(final Long activityId, final Long operatorId) {
        return issueDao.assignNotAssignedActivityToOperator(activityId, operatorId);
    }

    /**
     * Dla zlecenia typu Windykacja bezpośrednia, pobiera adres siedziby klienta
     * i jeśli występuje operator pasujący do zdefiniowanej maski przypisuje zlecenie i czynność do tego operatora.
     */
    public void assignOperatorByPostalCodeForDirectDebtCollection(final Issue issue) {
        if (DIRECT_DEBT_COLLECTION.equals(issue.getIssueType().getName())) {
            getCorrectPostalCodeOfCustomersResidenceAddress(issue)
                    .flatMap(postalCodeService::findOperatorForPostalCode)
                    .ifPresent(operator -> updateIssuesAssignment(operator.getId(), Collections.singletonList(issue.getId())));
        }
    }

    private Optional<String> getCorrectPostalCodeOfCustomersResidenceAddress(final Issue issue) {
        return issue.getCustomer().getCompany().getAddresses().stream()
                .filter(address -> address.getAddressType().equals(AddressType.RESIDENCE))
                .map(Address::getPostalCode)
                .findFirst()
                .flatMap(PostalCodeUtils::toCorrectPostalCode);
    }
}

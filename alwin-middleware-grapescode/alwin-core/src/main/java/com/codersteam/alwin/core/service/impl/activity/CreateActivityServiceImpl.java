package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDto;
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto;
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.activity.CreateActivityService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.activity.Declaration;
import com.codersteam.alwin.jpa.issue.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.util.IssueUtils.isPaid;
import static com.codersteam.alwin.jpa.type.ActivityState.OPEN_STATES;
import static com.codersteam.alwin.jpa.type.ActivityTypeKey.*;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@SuppressWarnings({"EjbClassBasicInspection", "CdiInjectionPointsInspection"})
@Stateless
public class CreateActivityServiceImpl implements CreateActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final IssueDao issueDao;
    private final AlwinMapper alwinMapper;
    private final DateProvider dateProvider;
    private final ActivityService activityService;

    @Inject
    public CreateActivityServiceImpl(final IssueDao issueDao, final AlwinMapper alwinMapper, final DateProvider dateProvider,
                                     final ActivityService activityService) {
        this.issueDao = issueDao;
        this.alwinMapper = alwinMapper;
        this.dateProvider = dateProvider;
        this.activityService = activityService;
    }

    @Override
    public void createNewPhoneDebtCollectionActivity(Long issueId) {
        issueDao.get(issueId).ifPresent(issue -> {
            if (isPhoneDebtCollectionIssueValidForPlannedActivityCreation(issue)) {
                createNewActivityForIssue(issue);
            }
        });
    }

    @Override
    public void createNewDirectDebtCollectionActivity(Long issueId) {
        issueDao.get(issueId).ifPresent(issue -> {
            if (isDirectDebtCollectionIssueValidForPlannedActivityCreation(issue)) {
                createNewActivityForIssue(issue);
            }
        });
    }

    private void createNewActivityForIssue(final Issue issue) {
        try {
            activityService.createNewActivityForIssue(createNewOutgoingCallActivity(issue));
        } catch (final Exception e) {
            LOG.error("Create outgoing call activity for issue with id={} failed", issue.getId(), e);
        }
    }

    private ActivityDto createNewOutgoingCallActivity(final Issue issue) {
        final ActivityDto activityDto = new ActivityDto();
        activityDto.setIssueId(issue.getId());
        if (issue.getAssignee() != null) {
            activityDto.setOperator(alwinMapper.map(issue.getAssignee(), OperatorDto.class));
        }
        final ActivityTypeDto activityType = new ActivityTypeDto();
        activityType.setId(1L);
        activityDto.setActivityType(activityType);
        activityDto.setActivityDate(dateProvider.getCurrentDateStartOfDay());
        activityDto.setPlannedDate(dateProvider.getCurrentDateStartOfDay());
        activityDto.setCreationDate(dateProvider.getCurrentDateStartOfDay());
        activityDto.setState(ActivityStateDto.PLANNED);
        activityDto.setDeclarations(Collections.emptyList());

        return activityDto;
    }

    protected boolean isPhoneDebtCollectionIssueValidForPlannedActivityCreation(final Issue issue) {
        final Date yesterday = dateProvider.getDateStartOfDayMinusDays(1);
        final List<Activity> activities = issue.getActivities();

        if (isEmpty(activities)) {
            return false;
        }
        if (hasIssueOutgoingCallActivityAlreadyDefined(activities)) {
            return false;
        }
        if (hasIssueNotPaidOverdueDeclaration(activities)) {
            return true;
        }
        if (hasIssueOverallOverdueAndPaidDeclaration(activities, issue)) {
            return true;
        }

        return issueHasNotFutureDeclarationAndSmsActivityAndCallForPaymentActivity(yesterday, activities);

    }

    private boolean isDirectDebtCollectionIssueValidForPlannedActivityCreation(final Issue issue) {
        final Date dayAfterTomorrow = dateProvider.getDateStartOfDayPlusDays(2);
        final List<Activity> activities = issue.getActivities();

        if (hasIssueOutgoingCallActivityAlreadyDefinedBeforeDate(activities, dayAfterTomorrow)) {
            return false;
        }
        if (hasIssueNotPaidYesterdayDeclaration(activities)) {
            return true;
        }
        return hasIssueOverallOverdueAndPaidDeclaration(activities, issue);
    }

    private boolean issueHasNotFutureDeclarationAndSmsActivityAndCallForPaymentActivity(final Date yesterday, final List<Activity> activities) {
        for (final Activity activity : activities) {
            if (activity.getDeclarations() == null && activity.getActivityType().getKey() != FIRST_DEMAND_PAYMENT
                    && activity.getActivityType().getKey() != LAST_DEMAND_PAYMENT
                    && activity.getActivityType().getKey() != OUTGOING_SMS) {
                return true;
            }
            if (activity.getDeclarations() != null) {
                final long count = activity.getDeclarations().stream()
                        .filter(declaration -> declaration.getDeclaredPaymentDate().after(yesterday))
                        .count();
                if (count == 0 && activity.getActivityType().getKey() != FIRST_DEMAND_PAYMENT
                        && activity.getActivityType().getKey() != LAST_DEMAND_PAYMENT
                        && activity.getActivityType().getKey() != OUTGOING_SMS) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasIssueNotPaidOverdueDeclaration(final List<Activity> activities) {
        for (final Activity activity : activities) {
            if (activity.getDeclarations() == null) {
                continue;
            }
            final List<Declaration> declarations = activity.getDeclarations();
            if (declarations.stream().anyMatch(declaration -> dateProvider.isYesterdayOrOlder(declaration.getDeclaredPaymentDate())
                    && hasNotPaidDeclaration(declaration))) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasIssueNotPaidYesterdayDeclaration(final List<Activity> activities) {
        for (final Activity activity : activities) {
            if (activity.getDeclarations() == null) {
                continue;
            }
            final List<Declaration> declarations = activity.getDeclarations();
            if (declarations.stream().anyMatch(declaration -> dateProvider.isYesterday(declaration.getDeclaredPaymentDate())
                    && hasNotPaidDeclaration(declaration))) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasIssueOverallOverdueAndPaidDeclaration(final List<Activity> activities, final Issue issue) {
        if (isPaid(issue)) {
            return false;
        }

        for (final Activity activity : activities) {
            if (isEmpty(activity.getDeclarations())) {
                continue;
            }
            final List<Declaration> declarations = activity.getDeclarations();
            if (declarations.stream().anyMatch(declaration -> dateProvider.isYesterday(declaration.getDeclaredPaymentDate())
                    && hasPaidDeclaration(declaration))) {

                return true;
            }
        }
        return false;
    }

    private boolean hasNotPaidDeclaration(final Declaration declaration) {
        return !hasPaidDeclaration(declaration);
    }

    protected boolean hasPaidDeclaration(final Declaration declaration) {
        final BigDecimal cashPaidPLN = declaration.getCashPaidPLN() == null ? ZERO : declaration.getCashPaidPLN();
        final BigDecimal cashPaidEUR = declaration.getCashPaidEUR() == null ? ZERO : declaration.getCashPaidEUR();
        return declaration.getDeclaredPaymentAmountPLN().compareTo(cashPaidPLN) <= 0
                && declaration.getDeclaredPaymentAmountEUR().compareTo(cashPaidEUR) <= 0;
    }

    protected boolean hasIssueOutgoingCallActivityAlreadyDefined(final List<Activity> activities) {
        return activities.stream().anyMatch(activity -> isOpen(activity) && isOutgoingPhoneCall(activity));
    }

    protected boolean hasIssueOutgoingCallActivityAlreadyDefinedBeforeDate(final List<Activity> activities, final Date date) {
        return activities.stream().anyMatch(activity -> isOpen(activity) && isOutgoingPhoneCall(activity) &&
                isPlanedBefore(activity.getPlannedDate(), date));
    }

    private boolean isOutgoingPhoneCall(final Activity activity) {
        return activity.getActivityType().getKey() == OUTGOING_PHONE_CALL;
    }

    private boolean isOpen(final Activity activity) {
        return OPEN_STATES.contains(activity.getState());
    }

    protected boolean isPlanedBefore(final Date plannedDate, final Date date) {
        return plannedDate.before(date);
    }
}

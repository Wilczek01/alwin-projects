package com.codersteam.alwin.core.service.impl.audit;

import com.codersteam.alwin.core.api.model.audit.AuditChangeType;
import com.codersteam.alwin.core.api.model.audit.AuditLogEntryDto;
import com.codersteam.alwin.core.api.service.audit.AuditService;
import com.codersteam.alwin.core.util.DateUtils;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.operator.Operator;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.core.api.model.audit.AuditChangeType.*;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class AuditServiceImpl implements AuditService {

    private final IssueDao issueDao;
    private final OperatorDao operatorDao;

    @Inject
    public AuditServiceImpl(final IssueDao issueDao, final OperatorDao operatorDao) {
        this.issueDao = issueDao;
        this.operatorDao = operatorDao;
    }

    @SuppressWarnings("unchecked")
    private static <T> T initializeAndUnproxy(T entity) {

        try {
            if (entity != null) {
                Hibernate.initialize(entity);
                if (entity instanceof HibernateProxy) {
                    entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
                }
            }
            return entity;
        } catch (final EntityNotFoundException e) {
            throw new com.codersteam.alwin.core.api.exception.EntityNotFoundException(null, e.getMessage());

        }
    }

    @Override
    public List<AuditLogEntryDto> findIssueChanges(final Long issueId) {
        final List<AuditLogEntry<Issue>> auditLogEntries = getIssues(issueId);

        final List<AuditLogEntryDto> changes = new ArrayList<>();
        for (int i = 0; i < auditLogEntries.size() - 1; i++) {
            changes.addAll(findChanges(auditLogEntries, i, i + 1));
        }
        return changes;
    }

    private List<AuditLogEntry<Issue>> getIssues(final Long issueId) {
        final List<AuditLogEntry<Issue>> logEntries = issueDao.findChangesByEntityId(issueId);
        prepareIssueLogEntriesForDiff(logEntries);
        return logEntries;
    }

    private void prepareIssueLogEntriesForDiff(final List<AuditLogEntry<Issue>> logEntries) {
        for (final AuditLogEntry logEntry : logEntries) {
            final Issue entity = (Issue) logEntry.getEntity();

            entity.setCustomer(initializeAndUnproxy(entity.getCustomer()));
            entity.getCustomer().setCompany(initializeAndUnproxy(entity.getCustomer().getCompany()));
            entity.setContract(initializeAndUnproxy(entity.getContract()));
            entity.setTerminationOperator(initializeAndUnproxy(entity.getTerminationOperator()));
            entity.setIssueType(initializeAndUnproxy(entity.getIssueType()));

            final List<Activity> activities = entity.getActivities();
            if (activities != null) {
                for (final Activity activity : activities) {
                    activity.setActivityType(null);
                }
            }
        }
    }

    private List<AuditLogEntryDto> findChanges(final List<AuditLogEntry<Issue>> auditLogEntries, final int before, final int after) {
        final List<AuditLogEntryDto> changes = new ArrayList<>();

        final Javers javers = JaversBuilder.javers().build();
        final Diff diff = javers.compare(auditLogEntries.get(before).getEntity(), auditLogEntries.get(after).getEntity());

        final Long operatorId = auditLogEntries.get(after).getRevEntity().getOperator();
        final Date revisionDate = auditLogEntries.get(after).getRevEntity().getRevisionDate();
        final String operatorLogin = operatorId != null ? operatorDao.get(operatorId).map(Operator::getLogin).orElse(null) : null;

        checkValueChange(changes, diff, operatorLogin, revisionDate);
        checkReferenceChange(changes, diff, operatorLogin, revisionDate);
        checkObjectRemove(changes, diff, operatorLogin, revisionDate);
        checkNewObject(changes, diff, operatorLogin, revisionDate);

        return changes;
    }

    private void checkNewObject(final List<AuditLogEntryDto> changes, final Diff diff, final String operatorLogin, final Date revisionDate) {
        for (final NewObject change : diff.getChangesByType(NewObject.class)) {
            createAuditLogEntry(changes, operatorLogin, revisionDate, NEW_OBJECT, change.getAffectedObject());
        }
    }

    private void checkObjectRemove(final List<AuditLogEntryDto> changes, final Diff diff, final String operatorLogin, final Date revisionDate) {
        for (final ObjectRemoved change : diff.getChangesByType(ObjectRemoved.class)) {
            createAuditLogEntry(changes, operatorLogin, revisionDate, OBJECT_REMOVED, change.getAffectedObject());
        }
    }

    private void checkReferenceChange(final List<AuditLogEntryDto> changes, final Diff diff, final String operatorLogin, final Date revisionDate) {
        for (final ReferenceChange change : diff.getChangesByType(ReferenceChange.class)) {

            final AuditLogEntryDto auditLogEntry = createAuditLogEntry(operatorLogin, revisionDate, REFERENCE_CHANGE, change.getPropertyName(), change.getLeft(),
                    change.getRight(), change.getAffectedObject());
            if (!change.getLeftObject().equals(Optional.empty()) && !change.getRightObject().equals(Optional.empty())) {
                changes.add(auditLogEntry);
            }
        }
    }

    private void checkValueChange(final List<AuditLogEntryDto> changes, final Diff diff, final String operatorLogin, final Date revisionDate) {
        for (final ValueChange change : diff.getChangesByType(ValueChange.class)) {
            final AuditLogEntryDto auditLogEntry = createAuditLogEntry(operatorLogin, revisionDate, VALUE_CHANGE, change.getPropertyName(), change.getLeft(),
                    change.getRight(), change.getAffectedObject());
            changes.add(auditLogEntry);
        }
    }

    private void createAuditLogEntry(final List<AuditLogEntryDto> changes, final String userLogin, final Date revisionDate, final AuditChangeType changeType,
                                     final Optional<Object> affectedObject) {
        final AuditLogEntryDto auditLogEntry = new AuditLogEntryDto();
        auditLogEntry.setChangeType(changeType);
        final String className = affectedObject.map(o -> o.getClass().getSimpleName()).orElse("unknown");
        auditLogEntry.setObjectName(className);
        auditLogEntry.setOperatorLogin(userLogin);
        auditLogEntry.setChangeDate(DateUtils.getFormattedStringFromDate(revisionDate, "yyyy-MM-dd HH:mm:ss"));
        changes.add(auditLogEntry);
    }

    private AuditLogEntryDto createAuditLogEntry(final String userLogin, final Date revisionDate, final AuditChangeType changeType, final String propertyName,
                                                 final Object left, final Object right, final Optional<Object> affectedObject) {
        final AuditLogEntryDto auditLogEntry = new AuditLogEntryDto();
        auditLogEntry.setChangeType(changeType);
        auditLogEntry.setFieldName(propertyName);
        auditLogEntry.setOldValue(getValue(left));
        auditLogEntry.setNewValue(getValue(right));
        final String className = affectedObject.map(o -> o.getClass().getSimpleName()).orElse("unknown");
        auditLogEntry.setObjectName(className);
        auditLogEntry.setOperatorLogin(userLogin);
        auditLogEntry.setChangeDate(DateUtils.getFormattedStringFromDate(revisionDate, "yyyy-MM-dd HH:mm:ss"));
        return auditLogEntry;
    }

    private String getValue(final Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) {
            return DateUtils.getFormattedStringFromDate((Date) value, "yyyy-MM-dd HH:mm:ss");
        }

        return String.valueOf(value);
    }
}

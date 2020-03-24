package com.codersteam.alwin.db.util;

import com.codersteam.alwin.jpa.UserRevEntity;
import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import org.hibernate.envers.RevisionType;

import static java.lang.String.format;

/**
 * @author Tomasz Sliwinski
 */
public final class AuditQueryResultUtils {

    private AuditQueryResultUtils() {
    }

    public static <T> AuditLogEntry<T> getAuditQueryResult(final Object[] logEntryItem, final Class<T> type) {
        if (invalidLogEntry(logEntryItem)) {
            return null;
        }
        final T entity = getEntity(logEntryItem[0], type);
        final UserRevEntity revision = getRevisionEntity(logEntryItem[1]);
        final RevisionType revisionType = getRevisionType(logEntryItem[2]);
        return new AuditLogEntry<>(entity, revision, revisionType);
    }

    private static boolean invalidLogEntry(final Object[] logEntryItem) {
        return logEntryItem == null || logEntryItem.length < 3;
    }

    private static RevisionType getRevisionType(final Object revisionType) {
        if (revisionType instanceof RevisionType) {
            return (RevisionType) revisionType;
        }
        throw new IllegalArgumentException(format("Expected RevisionType, but got: %s", getObjectClassNullSafe(revisionType)));
    }

    private static UserRevEntity getRevisionEntity(final Object revEntity) {
        if (revEntity instanceof UserRevEntity) {
            return (UserRevEntity) revEntity;
        }
        throw new IllegalArgumentException(format("Expected UserRevEntity, but got: %s", getObjectClassNullSafe(revEntity)));
    }

    private static <T> T getEntity(final Object entity, final Class<T> type) {
        if (type.isInstance(entity)) {
            return type.cast(entity);
        }
        throw new IllegalArgumentException(format("Expected %s entity, but got: %s", type, getObjectClassNullSafe(entity)));
    }

    private static Object getObjectClassNullSafe(final Object object) {
        return object != null ? object.getClass() : "null";
    }
}

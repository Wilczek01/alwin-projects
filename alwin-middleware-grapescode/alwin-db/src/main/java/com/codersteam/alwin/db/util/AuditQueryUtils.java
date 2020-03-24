package com.codersteam.alwin.db.util;

import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import org.hibernate.envers.query.AuditQuery;

import java.util.List;

import static com.codersteam.alwin.db.util.AuditQueryResultUtils.getAuditQueryResult;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Tomasz Sliwinski
 */
public final class AuditQueryUtils {

    private AuditQueryUtils() {
    }

    public static <T> List<AuditLogEntry<T>> getAuditQueryResults(final AuditQuery query, final Class<T> targetType) {
        final List<?> results = query.getResultList();
        if (isEmpty(results)) {
            return emptyList();
        }
        return results.stream()
                .filter(logEntryItem -> logEntryItem instanceof Object[])
                .map(logEntryItem -> (Object[]) logEntryItem)
                .map(logEntryItem -> getAuditQueryResult(logEntryItem, targetType))
                .collect(toList());
    }
}

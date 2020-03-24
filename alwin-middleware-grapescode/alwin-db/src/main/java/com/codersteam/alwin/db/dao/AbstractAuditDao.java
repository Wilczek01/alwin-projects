package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.audit.AuditLogEntry;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;

import java.util.List;

import static com.codersteam.alwin.db.util.AuditQueryUtils.getAuditQueryResults;
import static org.hibernate.envers.query.AuditEntity.id;

/**
 * @author Tomasz Sliwinski
 */
public abstract class AbstractAuditDao<T> extends AbstractDao<T> {

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManager);
    }

    public List<AuditLogEntry<T>> findChangesByEntityId(final Long entityId) {
        final AuditQuery query = getAuditReader().createQuery().forRevisionsOfEntity(getType(), false, true);
        query.add(id().eq(entityId));
        return getAuditQueryResults(query, getType());
    }
}

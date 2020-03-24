package com.codersteam.alwin.jpa.audit;

import com.codersteam.alwin.jpa.UserRevEntity;
import org.hibernate.envers.RevisionType;

/**
 * @author Tomasz Sliwinski
 */
public class AuditLogEntry<T> {

    private final T entity;
    private final RevisionType revisionType;
    private final UserRevEntity revEntity;

    public AuditLogEntry(final T entity, final UserRevEntity revEntity, final RevisionType revisionType) {
        this.entity = entity;
        this.revisionType = revisionType;
        this.revEntity = revEntity;
    }

    public T getEntity() {
        return entity;
    }

    public RevisionType getRevisionType() {
        return revisionType;
    }

    public UserRevEntity getRevEntity() {
        return revEntity;
    }
}

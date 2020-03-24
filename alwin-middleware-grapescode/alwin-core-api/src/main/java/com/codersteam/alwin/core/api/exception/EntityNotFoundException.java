package com.codersteam.alwin.core.api.exception;

/**
 * Klasa wyjątku rzucanego, gdy nie można znaleźć encji w bazie o podanym
 *
 * @author Piotr Naroznik
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Identyfikator obiektu
     */
    private final Object entityId;

    public EntityNotFoundException(final Object entityId) {
        this.entityId = entityId;
    }

    public EntityNotFoundException(final Object entityId, final String message) {
        super(message);
        this.entityId = entityId;
    }

    public Object getEntityId() {
        return entityId;
    }
}
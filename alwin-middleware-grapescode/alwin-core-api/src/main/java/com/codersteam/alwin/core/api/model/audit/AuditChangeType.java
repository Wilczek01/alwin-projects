package com.codersteam.alwin.core.api.model.audit;

/**
 * @author Tomasz Sliwinski
 */
public enum AuditChangeType {

    /**
     * zmiana wartości
     */
    VALUE_CHANGE,

    /**
     * zmiana referencji
     */
    REFERENCE_CHANGE,

    /**
     * usunięcie obiektu
     */
    OBJECT_REMOVED,

    /**
     * utworzenie obiektu
     */
    NEW_OBJECT
}

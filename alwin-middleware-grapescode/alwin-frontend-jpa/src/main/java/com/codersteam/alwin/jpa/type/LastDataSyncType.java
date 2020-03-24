package com.codersteam.alwin.jpa.type;

/**
 * @author Piotr Naroznik
 */
public enum LastDataSyncType {

    /**
     * typ dla zadania cyklicznie aktulaizującego dane firmy
     */
    UPDATE_COMPANY_DATA,

    /**
     * typ dla zadania cyklicznie szukającego nierozwiązanych zleceń podczas windykacji telefonicznej
     */
    FIND_UNRESOLVED_ISSUES
}

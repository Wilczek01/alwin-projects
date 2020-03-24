package com.codersteam.alwin.core.api.model.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Role uzytkownikow w systemie ALWIN
 *
 * @author Tomasz Sliwinski
 */
public enum OperatorType {
    ADMIN,
    PHONE_DEBT_COLLECTOR,
    PHONE_DEBT_COLLECTOR_1,
    PHONE_DEBT_COLLECTOR_2,
    FIELD_DEBT_COLLECTOR,
    RESTRUCTURING_SPECIALIST,
    RENUNCIATION_COORDINATOR,
    SECURITY_SPECIALIST,
    PHONE_DEBT_COLLECTOR_MANAGER,
    DIRECT_DEBT_COLLECTION_MANAGER,
    ANALYST,
    DEPARTMENT_MANAGER;

    private static final Map<String, OperatorType> lookup = new HashMap<>();

    static {
        for (final OperatorType role : OperatorType.values()) {
            lookup.put(role.name(), role);
        }
    }

    /**
     * Pobranie roli po nazwie
     *
     * @param name nazwa roli
     * @return rola
     */
    public static OperatorType forName(final String name) {
        return lookup.get(name.toUpperCase());
    }
}

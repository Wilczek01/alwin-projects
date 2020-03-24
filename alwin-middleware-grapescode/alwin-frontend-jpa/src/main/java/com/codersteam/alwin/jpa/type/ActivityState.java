package com.codersteam.alwin.jpa.type;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Stan wykonywanej czynnosci dla zlecenia:
 * PLANNED(Zaplanowana)
 * EXECUTED(Wykonana)
 * POSTPONED(Przełożona)
 * CANCELED(Anulowana)
 *
 * @author Michal Horowic
 */
public enum ActivityState {
    /**
     * Zaplanowana
     */
    PLANNED,

    /**
     * Wykonana
     */
    EXECUTED,

    /**
     * Przełożona
     */
    POSTPONED,

    /**
     * Anulowana
     */
    CANCELED;

    public static final List<ActivityState> ALL_STATES = asList(values());
    public static final List<ActivityState> OPEN_STATES = asList(PLANNED, POSTPONED);
    public static final List<ActivityState> CLOSED_STATES = asList(EXECUTED, CANCELED);

}

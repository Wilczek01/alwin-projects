package com.codersteam.alwin.core.service;

import java.math.BigDecimal;

/**
 * @author Tomasz Sliwinski
 */
public final class AlwinParameters {

    /**
     * ilość dni wstecz do synchronizacji danych, wykorzystywane do pierwszego uruchomienia
     */
    public static final int SYNC_DATA_DAY_START = 2;

    /**
     * Parametr salda do zamknięcia zlecenia - jeśli suma sald z dokumentów przekracza, to zamykamy
     */
    public static final BigDecimal CURRENT_BALANCE_VALUE_TO_CLOSE_ISSUE = new BigDecimal("-100.00");

    /**
     * Domyślny priorytet zlecenia
     */
    public static final Integer ISSUE_DEFAULT_PRIORITY = 0;

    private AlwinParameters() {
    }
}

package com.codersteam.alwin.core.api.model.customer;

import java.util.Date;

/**
 * @author Piotr Naroznik
 */
public interface DateRange {

    /**
     * Pobieranie daty rozpoczęcia blokady
     *
     * @return data rozpoczęcia blokady
     */
    Date getStartDate();

    /**
     * Pobieranie daty zakończenia blokady
     *
     * @return data zakończenia blokady
     */
    Date getEndDate();
}
package com.codersteam.alwin.core.api.service.message;

import com.codersteam.alwin.core.api.exception.SmsSendingException;

/**
 * Wysyłka wiadomości sms
 *
 * @author Piotr Naroznik
 */
public interface SmsSenderService {

    /**
     * Wysyłanie wiadomości sms
     *
     * @param phoneNumber - numer telefonu odbiorcy
     * @param message     - treść wiadomości
     * @param id          - unikalny identyfikator rozsyłki, wykorzystywany do ignorowania zduplikowanych rozsyłek
     * @throws SmsSendingException gdy wysyłka się nie powiodła
     */
    void send(final String phoneNumber, final String message, final int id) throws SmsSendingException;
}
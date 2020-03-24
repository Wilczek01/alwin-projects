package com.codersteam.alwin.core.api.service.message;

import com.codersteam.alwin.core.api.exception.SmsSendingException;
import com.codersteam.alwin.core.api.model.message.SmsMessageDto;
import com.codersteam.alwin.core.api.model.message.SmsTemplateDto;

import java.util.List;

/**
 * Serwis do obsługi wiadomości
 *
 * @author Piotr Naroznik
 */
public interface MessageService {

    /**
     * Pobieranie szablonów wiadomości sms
     *
     * @return lista szablonów wiadomości sms
     */
    List<SmsTemplateDto> findSmsMessagesTemplates();


    /**
     * Wysyłanie wiadomości sms
     *
     * @param smsMessage - wiadomość do wysłania
     * @param operatorId - identyfikator operatora
     * @throws SmsSendingException gdy wysyłka się nie powiodła
     */
    void sendSmsMessage(SmsMessageDto smsMessage, final Long operatorId) throws SmsSendingException;
}

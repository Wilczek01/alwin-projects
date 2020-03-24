package com.codersteam.alwin.integration.mock;

import com.codersteam.alwin.core.api.exception.SmsSendingException;
import com.codersteam.alwin.core.api.service.message.SmsSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author Piotr Naroznik
 */
public class SmsSenderServiceMock implements SmsSenderService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void send(String phoneNumber, String message, int id) throws SmsSendingException {
        LOG.info(String.format("invoke SmsSenderServiceMock.send(%s, %s, %s)", phoneNumber, message, id));
        //mock wysyłki smsów na potrzeby testów integracyjnych
    }
}
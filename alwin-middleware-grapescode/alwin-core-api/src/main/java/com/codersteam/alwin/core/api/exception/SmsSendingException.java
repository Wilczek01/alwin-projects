package com.codersteam.alwin.core.api.exception;

/**
 * @author Piotr Naroznik
 */
public class SmsSendingException extends Exception {

    private final String phoneNumber;
    private final String smsMessage;
    private final int id;

    public SmsSendingException(final String phoneNumber, final String smsMessage, final int id, final Throwable cause) {
        super(cause);
        this.phoneNumber = phoneNumber;
        this.smsMessage = smsMessage;
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public int getId() {
        return id;
    }
}

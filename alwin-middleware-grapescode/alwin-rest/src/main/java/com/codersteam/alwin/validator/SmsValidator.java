package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.message.SmsMessageDto;
import com.codersteam.alwin.exception.AlwinValidationException;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Stateless
public class SmsValidator {

    private static final String PHONE_NUMBER_REGEX = "48(\\d{9})";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    private static final int MAX_SMS_MESSAGE_LENGTH = 160;
    private static final String WRONG_PHONE_NUMBER_MESSAGE = "Niepoprawny numer telefonu '%s', prawidłowy format to '48xxxxxxxxx'.";
    private static final String SMS_MESSAGE_TO_LONG_MESSAGE = format("Wiadomość sms jest za długa. Maksymalna długość to %s znaków.", MAX_SMS_MESSAGE_LENGTH);
    private static final String SMS_MESSAGE_EMPTY_MESSAGE = "Wiadomość sms nie może być pusta.";

    public void validate(final SmsMessageDto smsMessage) throws AlwinValidationException {
        validatePhoneNumber(smsMessage.getPhoneNumber());
        validateSmsMessage(smsMessage.getMessage());
    }

    private void validateSmsMessage(final String smsMessage) {
        if (StringUtils.isBlank(smsMessage)) {
            throw new AlwinValidationException(SMS_MESSAGE_EMPTY_MESSAGE);
        }
        if (StringUtils.length(smsMessage) > MAX_SMS_MESSAGE_LENGTH) {
            throw new AlwinValidationException(SMS_MESSAGE_TO_LONG_MESSAGE);
        }
    }

    private void validatePhoneNumber(final String phoneNumber) {
        if (phoneNumber == null || !PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new AlwinValidationException(format(WRONG_PHONE_NUMBER_MESSAGE, phoneNumber));
        }
    }
}
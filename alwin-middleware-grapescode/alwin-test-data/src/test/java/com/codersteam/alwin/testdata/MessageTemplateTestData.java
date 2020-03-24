package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.message.SmsMessageDto;
import com.codersteam.alwin.core.api.model.message.SmsTemplateDto;
import com.codersteam.alwin.jpa.MessageTemplate;
import com.codersteam.alwin.jpa.type.MessageType;

import java.util.List;

import static com.codersteam.alwin.jpa.type.MessageType.E_MAIL;
import static com.codersteam.alwin.jpa.type.MessageType.SMS;
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1;
import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
public class MessageTemplateTestData {

    public static final String SMS_PHONE_NUMBER = "48xxxxxxxxx";
    public static final String SMS_MESSAGE = "test message";
    public static final int SMS_ID = 1;

    public static Long MESSAGE_TEMPLATE_ID_1 = 1L;
    public static String MESSAGE_TEMPLATE_NAME_1 = "testowy 1";
    public static String MESSAGE_TEMPLATE_BODY_1 = "testowa treść wiadomości 1";
    public static MessageType MESSAGE_TEMPLATE_TYPE_1 = SMS;
    public static String MESSAGE_TEMPLATE_TOPIC_1 = null;

    public static MessageTemplate messageTemplate1() {
        return messageTemplate(MESSAGE_TEMPLATE_ID_1, MESSAGE_TEMPLATE_NAME_1, MESSAGE_TEMPLATE_TYPE_1, MESSAGE_TEMPLATE_BODY_1, MESSAGE_TEMPLATE_TOPIC_1);
    }

    public static SmsTemplateDto smsTemplateDto1() {
        return smsTemplateDto(MESSAGE_TEMPLATE_ID_1, MESSAGE_TEMPLATE_NAME_1, MESSAGE_TEMPLATE_BODY_1);
    }

    public static Long MESSAGE_TEMPLATE_ID_2 = 2L;
    public static String MESSAGE_TEMPLATE_NAME_2 = "testowy 2";
    public static String MESSAGE_TEMPLATE_BODY_2 = "testowa treść wiadomości 2";
    public static MessageType MESSAGE_TEMPLATE_TYPE_2 = SMS;
    public static String MESSAGE_TEMPLATE_TOPIC_2 = null;

    public static MessageTemplate messageTemplate2() {
        return messageTemplate(MESSAGE_TEMPLATE_ID_2, MESSAGE_TEMPLATE_NAME_2, MESSAGE_TEMPLATE_TYPE_2, MESSAGE_TEMPLATE_BODY_2, MESSAGE_TEMPLATE_TOPIC_2);
    }

    public static SmsTemplateDto smsTemplateDto2() {
        return smsTemplateDto(MESSAGE_TEMPLATE_ID_2, MESSAGE_TEMPLATE_NAME_2, MESSAGE_TEMPLATE_BODY_2);
    }

    public static Long MESSAGE_TEMPLATE_ID_3 = 3L;
    public static String MESSAGE_TEMPLATE_NAME_3 = "testowy 3";
    public static String MESSAGE_TEMPLATE_BODY_3 = "testowa treść wiadomości 3";
    public static MessageType MESSAGE_TEMPLATE_TYPE_3 = E_MAIL;
    public static String MESSAGE_TEMPLATE_TOPIC_3 = "testowy tytuł 3";

    public static MessageTemplate messageTemplate3() {
        return messageTemplate(MESSAGE_TEMPLATE_ID_3, MESSAGE_TEMPLATE_NAME_3, MESSAGE_TEMPLATE_TYPE_3, MESSAGE_TEMPLATE_BODY_3, MESSAGE_TEMPLATE_TOPIC_3);
    }

    public static List<MessageTemplate> all() {
        return asList(messageTemplate1(), messageTemplate2(), messageTemplate3());
    }

    public static SmsMessageDto smsMessageDto1() {
        return smsMessageDto(ISSUE_ID_1, SMS_MESSAGE, SMS_PHONE_NUMBER);
    }

    public static MessageTemplate messageTemplate(final Long id, final String name, final MessageType type, final String body, final String topic) {
        final MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setId(id);
        messageTemplate.setName(name);
        messageTemplate.setType(type);
        messageTemplate.setBody(body);
        messageTemplate.setTopic(topic);
        return messageTemplate;
    }

    public static SmsTemplateDto smsTemplateDto(final Long id, final String name, final String body) {
        final SmsTemplateDto messageTemplate = new SmsTemplateDto();
        messageTemplate.setId(id);
        messageTemplate.setName(name);
        messageTemplate.setBody(body);
        return messageTemplate;
    }

    private static SmsMessageDto smsMessageDto(final Long issueId, final String smsMessage, final String phoneNumber) {
        final SmsMessageDto smsMessageDto = new SmsMessageDto();
        smsMessageDto.setIssueId(issueId);
        smsMessageDto.setMessage(smsMessage);
        smsMessageDto.setPhoneNumber(phoneNumber);
        return smsMessageDto;
    }
}

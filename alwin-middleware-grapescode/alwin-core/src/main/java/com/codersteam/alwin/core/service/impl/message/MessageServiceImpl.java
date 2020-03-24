package com.codersteam.alwin.core.service.impl.message;

import com.codersteam.alwin.core.api.exception.SmsSendingException;
import com.codersteam.alwin.core.api.model.message.SmsMessageDto;
import com.codersteam.alwin.core.api.model.message.SmsTemplateDto;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.message.MessageService;
import com.codersteam.alwin.core.api.service.message.SmsSenderService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.MessageTemplateDao;
import ma.glasnost.orika.impl.ConfigurableMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

import static com.codersteam.alwin.core.service.impl.message.PolishCharactersUtil.replacePolishCharacters;
import static com.codersteam.alwin.jpa.type.MessageType.SMS;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class MessageServiceImpl implements MessageService {

    private final ConfigurableMapper mapper;
    private final ActivityService activityService;
    private final SmsSenderService smsSenderService;
    private final MessageTemplateDao messageTemplateDao;

    @Inject
    public MessageServiceImpl(final AlwinMapper mapper, final ActivityService activityService,
                              final MessageTemplateDao messageTemplateDao, final SmsSenderService smsSenderService) {
        this.messageTemplateDao = messageTemplateDao;
        this.smsSenderService = smsSenderService;
        this.activityService = activityService;
        this.mapper = mapper;
    }

    @Override
    public List<SmsTemplateDto> findSmsMessagesTemplates() {
        return mapper.mapAsList(messageTemplateDao.findByType(SMS), SmsTemplateDto.class);
    }

    @Override
    public void sendSmsMessage(final SmsMessageDto smsMessage, final Long operatorId) throws SmsSendingException {
        final String message = replacePolishCharacters(smsMessage.getMessage());
        final Long activityId = activityService.createOutgoingSmsActivity(smsMessage.getIssueId(), operatorId, smsMessage.getPhoneNumber(), message);
        smsSenderService.send(smsMessage.getPhoneNumber(), message, activityId.intValue());
    }
}

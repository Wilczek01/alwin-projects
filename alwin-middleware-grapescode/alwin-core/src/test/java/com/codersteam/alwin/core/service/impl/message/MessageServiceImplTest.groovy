package com.codersteam.alwin.core.service.impl.message

import com.codersteam.alwin.core.api.model.message.SmsTemplateDto
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.message.SmsSenderService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.MessageTemplateDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.jpa.type.MessageType.SMS
import static com.codersteam.alwin.testdata.ActivityTestData.ACTIVITY_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_MESSAGE
import static com.codersteam.alwin.testdata.MessageTemplateTestData.SMS_PHONE_NUMBER
import static com.codersteam.alwin.testdata.MessageTemplateTestData.messageTemplate1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.messageTemplate2
import static com.codersteam.alwin.testdata.MessageTemplateTestData.smsMessageDto1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.smsTemplateDto1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.smsTemplateDto2
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1
import static org.assertj.core.api.Assertions.assertThat
import static org.junit.Assert.assertTrue

/**
 * @author Piotr Naroznik
 */
class MessageServiceImplTest extends Specification {

    def activityService
    def smsSenderService
    def messageTemplateDao

    @Subject
    MessageServiceImpl service

    def setup() {
        activityService = Mock(ActivityService)
        smsSenderService = Mock(SmsSenderService)
        messageTemplateDao = Mock(MessageTemplateDao)
        service = new MessageServiceImpl(new AlwinMapper(), activityService, messageTemplateDao, smsSenderService)
    }

    def "should find sms messages templates"() {
        given:
            messageTemplateDao.findByType(SMS) >> [messageTemplate1(), messageTemplate2()]
        when:
            final List<SmsTemplateDto> templates = service.findSmsMessagesTemplates()
        then:
            assertThat(templates).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(smsTemplateDto1(), smsTemplateDto2())
    }

    def "should not find sms messages templates in not exists"() {
        given:
            messageTemplateDao.findByType(SMS) >> []
        when:
            final List<SmsTemplateDto> templates = service.findSmsMessagesTemplates()
        then:
            assertTrue(templates.isEmpty())
    }

    def "should send sms messages"() {
        given:
            activityService.createOutgoingSmsActivity(ISSUE_ID_1, OPERATOR_ID_1, SMS_PHONE_NUMBER, SMS_MESSAGE) >> ACTIVITY_ID_1
        when:
            service.sendSmsMessage(smsMessageDto1(), OPERATOR_ID_1)
        then:
            1 * smsSenderService.send(SMS_PHONE_NUMBER, SMS_MESSAGE, ACTIVITY_ID_1)
    }
}

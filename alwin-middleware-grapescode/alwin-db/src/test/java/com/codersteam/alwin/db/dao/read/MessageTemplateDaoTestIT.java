package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.MessageTemplateDao;
import com.codersteam.alwin.jpa.MessageTemplate;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static com.codersteam.alwin.jpa.type.MessageType.E_MAIL;
import static com.codersteam.alwin.jpa.type.MessageType.SMS;
import static com.codersteam.alwin.testdata.MessageTemplateTestData.messageTemplate1;
import static com.codersteam.alwin.testdata.MessageTemplateTestData.messageTemplate2;
import static com.codersteam.alwin.testdata.MessageTemplateTestData.messageTemplate3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Piotr Naroznik
 */
public class MessageTemplateDaoTestIT extends ReadTestBase {

    @EJB
    private MessageTemplateDao dao;

    @Test
    public void shouldFindAllSmsTemplates() throws Exception {
        //when
        final List<MessageTemplate> templates = dao.findByType(SMS);

        //then
        assertThat(templates).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(messageTemplate1(), messageTemplate2());
    }

    @Test
        public void shouldFindAllEmailTemplates() throws Exception {
        //when
        final List<MessageTemplate> templates = dao.findByType(E_MAIL);

        //then
        assertThat(templates).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(messageTemplate3());
    }

    @Test
    public void getType() throws Exception {
        assertEquals(MessageTemplate.class, dao.getType());
    }

}

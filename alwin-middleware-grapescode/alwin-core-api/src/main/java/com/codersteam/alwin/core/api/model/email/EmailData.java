package com.codersteam.alwin.core.api.model.email;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Tomasz Sliwinski
 */
public abstract class EmailData implements Serializable {

    private final List<String> emailRecipients;

    EmailData(final List<String> emailRecipients) {
        this.emailRecipients = emailRecipients;
    }

    @SuppressWarnings("unused")
    public EmailData(final String emailRecipient) {
        this.emailRecipients = Collections.singletonList(emailRecipient);
    }

    public abstract EmailType getEmailType();

    public List<String> getEmailRecipients() {
        return emailRecipients;
    }
}

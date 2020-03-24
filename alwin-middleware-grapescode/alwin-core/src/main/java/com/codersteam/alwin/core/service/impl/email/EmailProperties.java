package com.codersteam.alwin.core.service.impl.email;

import com.codersteam.alwin.core.config.Property;

import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * @author Tomasz Sliwinski
 */
@Singleton
public class EmailProperties {

    static final String EMAIL_MIME_TYPE = "text/html;charset=UTF-8";

    @Inject
    @Property("email.from")
    private String emailFrom;

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(final String emailFrom) {
        this.emailFrom = emailFrom;
    }
}

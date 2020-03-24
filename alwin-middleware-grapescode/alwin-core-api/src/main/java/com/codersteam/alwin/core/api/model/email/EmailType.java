package com.codersteam.alwin.core.api.model.email;

/**
 * @author Tomasz Sliwinski
 */
public enum EmailType {

    MISSING_ISSUES("Zestawienie brakujących zleceń", "html/missing_issues.html"),
    UNRESOLVED_ISSUES("Zestawienie nierozwiązanych zleceń podczas windykacji telefonicznej", "html/unresolved_issues.html");

    private final String topic;
    private final String templateName;

    EmailType(final String topic, final String templateName) {
        this.topic = topic;
        this.templateName = templateName;
    }

    public String getTopic() {
        return topic;
    }

    public String getTemplateName() {
        return templateName;
    }
}

package com.codersteam.alwin.dms.model;

import java.util.List;
import java.util.Map;

/**
 * @author Piotr Naroznik
 */
public class GenerateDocumentDto {
    private Map<String, String> additionalInformations;
    private List<AttachmentDto> attachments;
    private Map<String, String> descriptions;
    private String templateId;

    public Map<String, String> getAdditionalInformations() {
        return additionalInformations;
    }

    public void setAdditionalInformations(final Map<String, String> additionalInformations) {
        this.additionalInformations = additionalInformations;
    }

    public List<AttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<AttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(final Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "GenerateDocumentDto{" +
                "additionalInformations=" + additionalInformations +
                ", attachments=" + attachments +
                ", descriptions=" + descriptions +
                ", templateId='" + templateId + '\'' +
                '}';
    }
}
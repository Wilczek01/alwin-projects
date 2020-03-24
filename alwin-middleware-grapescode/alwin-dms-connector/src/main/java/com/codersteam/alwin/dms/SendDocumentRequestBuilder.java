package com.codersteam.alwin.dms;

import com.codersteam.alwin.dms.model.SendDocumentDto;
import pl.aliorleasing.dms.SendDocumentRequest;

/**
 * @author Adam Stepnowski
 */
public class SendDocumentRequestBuilder {

    private SendDocumentDto attachment;
    private String system;
    private String author;

    private SendDocumentRequestBuilder() {
    }

    public static SendDocumentRequestBuilder sendDocumentRequest() {
        return new SendDocumentRequestBuilder();
    }

    public SendDocumentRequestBuilder withDocument(final SendDocumentDto attachment) {
        this.attachment = attachment;
        return this;
    }

    public SendDocumentRequestBuilder withSystem(final String system) {
        this.system = system;
        return this;
    }

    public SendDocumentRequestBuilder withAuthor(final String author) {
        this.author = author;
        return this;
    }

    public SendDocumentRequest build() {
        final SendDocumentRequest request = new SendDocumentRequest();
        if (attachment != null) {
            request.setContentBase64(this.attachment.getContentBase64());
            request.setMimeType(this.attachment.getMimeType());
            request.setFileName(this.attachment.getFileName());
        }
        request.setSystem(this.system);
        request.setAuthor(this.author);
        return request;
    }

}

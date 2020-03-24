package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentAttachment;

/**
 * @author Piotr Naroznik
 */
class AttachmentTestData extends AttachmentDtoTestData {

    public static DocumentAttachment attachment1() {
        return attachment(CONTENT_BASE64_1, MIME_TYPE_1, NAME_1);
    }

    public static DocumentAttachment attachment2() {
        return attachment(CONTENT_BASE64_2, MIME_TYPE_2, NAME_2);
    }

    public static DocumentAttachment attachment3() {
        return attachment(CONTENT_BASE64_3, MIME_TYPE_3, NAME_3);
    }

    public static DocumentAttachment attachment4() {
        return attachment(CONTENT_BASE64_4, MIME_TYPE_4, NAME_4);
    }

    private static DocumentAttachment attachment(final String contentBase64, final String mimeType, final String name) {
        final DocumentAttachment attachmentDto = new DocumentAttachment();
        attachmentDto.setContentBase64(contentBase64);
        attachmentDto.setMimeType(mimeType);
        attachmentDto.setName(name);
        return attachmentDto;
    }
}
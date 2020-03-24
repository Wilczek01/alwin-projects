package com.codersteam.alwin.dms.testdata;


import com.codersteam.alwin.dms.model.AttachmentDto;

/**
 * @author Piotr Naroznik
 */
public class AttachmentDtoTestData {
    protected static final String CONTENT_BASE64_1 = "CONTENT_BASE64_1";
    protected static final String MIME_TYPE_1 = "MIME_TYPE_1";
    protected static final String NAME_1 = "NAME_1";

    public static AttachmentDto attachmentDto1() {
        return attachmentDto(CONTENT_BASE64_1, MIME_TYPE_1, NAME_1);
    }

    protected static final String CONTENT_BASE64_2 = "CONTENT_BASE64_2";
    protected static final String MIME_TYPE_2 = "MIME_TYPE_2";
    protected static final String NAME_2 = "NAME_2";

    public static AttachmentDto attachmentDto2() {
        return attachmentDto(CONTENT_BASE64_2, MIME_TYPE_2, NAME_2);
    }

    protected static final String CONTENT_BASE64_3 = "CONTENT_BASE64_3";
    protected static final String MIME_TYPE_3 = "MIME_TYPE_3";
    protected static final String NAME_3 = "NAME_3";

    public static AttachmentDto attachmentDto3() {
        return attachmentDto(CONTENT_BASE64_3, MIME_TYPE_3, NAME_3);
    }

    protected static final String CONTENT_BASE64_4 = "CONTENT_BASE64_4";
    protected static final String MIME_TYPE_4 = "MIME_TYPE_4";
    protected static final String NAME_4 = "NAME_4";

    public static AttachmentDto attachmentDto4() {
        return attachmentDto(CONTENT_BASE64_4, MIME_TYPE_4, NAME_4);
    }

    private static AttachmentDto attachmentDto(final String contentBase64, final String mimeType, final String name) {
        final AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setContentBase64(contentBase64);
        attachmentDto.setMimeType(mimeType);
        attachmentDto.setName(name);
        return attachmentDto;
    }
}
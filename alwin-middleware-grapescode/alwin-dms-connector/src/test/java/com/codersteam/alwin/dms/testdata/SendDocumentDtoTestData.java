package com.codersteam.alwin.dms.testdata;

import com.codersteam.alwin.dms.model.MetadataDto;
import com.codersteam.alwin.dms.model.SendDocumentDto;

import java.util.List;

import static com.codersteam.alwin.dms.testdata.MetadataDtoTestData.*;
import static java.util.Arrays.asList;

public class SendDocumentDtoTestData {
    protected static final String AUTHOR_1 = "AUTHOR_1";
    protected static final String COMMENT_1 = "COMMENT_1";
    protected static final String CONTENT_BASE64_1 = "CONTENT_BASE64_1";
    protected static final List<MetadataDto> METADATA_LIST_1 = asList(metadataDto1(), metadataDto2(), metadataDto3(), metadataDto4());
    protected static final String DOCUMENT_NUMBER_1 = "DOCUMENT_NUMBER_1";
    protected static final String DOCUMENT_TYPE_1 = "DOCUMENT_TYPE_1";
    protected static final String FILE_NAME_1 = "FILE_NAME_1";
    protected static final String MIME_TYPE_1 = "MIME_TYPE_1";
    protected static final String SYSTEM_1 = "SYSTEM_1";
    protected static final String SYSTEM_USER_ID_1 = "SYSTEM_USER_ID_1";
    protected static final String AUTHOR_2 = "AUTHOR_2";
    protected static final String COMMENT_2 = "COMMENT_2";
    protected static final String CONTENT_BASE64_2 = "CONTENT_BASE64_2";
    protected static final List<MetadataDto> METADATA_LIST_2 = null;
    protected static final String DOCUMENT_NUMBER_2 = "DOCUMENT_NUMBER_2";
    protected static final String DOCUMENT_TYPE_2 = "DOCUMENT_TYPE_2";
    protected static final String FILE_NAME_2 = "FILE_NAME_2";
    protected static final String MIME_TYPE_2 = "MIME_TYPE_2";
    protected static final String SYSTEM_2 = "SYSTEM_2";
    protected static final String SYSTEM_USER_ID_2 = "SYSTEM_USER_ID_2";
    protected static final String AUTHOR_3 = "AUTHOR_3";
    protected static final String COMMENT_3 = "COMMENT_3";
    protected static final String CONTENT_BASE64_3 = "CONTENT_BASE64_3";
    protected static final List<MetadataDto> METADATA_LIST_3 = null;
    protected static final String DOCUMENT_NUMBER_3 = "DOCUMENT_NUMBER_3";
    protected static final String DOCUMENT_TYPE_3 = "DOCUMENT_TYPE_3";
    protected static final String FILE_NAME_3 = "FILE_NAME_3";
    protected static final String MIME_TYPE_3 = "MIME_TYPE_3";
    protected static final String SYSTEM_3 = "SYSTEM_3";
    protected static final String SYSTEM_USER_ID_3 = "SYSTEM_USER_ID_3";
    protected static final String AUTHOR_4 = "AUTHOR_4";
    protected static final String COMMENT_4 = "COMMENT_4";
    protected static final String CONTENT_BASE64_4 = "CONTENT_BASE64_4";
    protected static final List<MetadataDto> METADATA_LIST_4 = null;
    protected static final String DOCUMENT_NUMBER_4 = "DOCUMENT_NUMBER_4";
    protected static final String DOCUMENT_TYPE_4 = "DOCUMENT_TYPE_4";
    protected static final String FILE_NAME_4 = "FILE_NAME_4";
    protected static final String MIME_TYPE_4 = "MIME_TYPE_4";
    protected static final String SYSTEM_4 = "SYSTEM_4";
    protected static final String SYSTEM_USER_ID_4 = "SYSTEM_USER_ID_4";

    public static SendDocumentDto sendDocumentDto1() {
        return sendDocumentDto(AUTHOR_1, COMMENT_1, CONTENT_BASE64_1, METADATA_LIST_1, DOCUMENT_NUMBER_1,
                DOCUMENT_TYPE_1, FILE_NAME_1, MIME_TYPE_1, SYSTEM_1, SYSTEM_USER_ID_1);
    }

    public static SendDocumentDto sendDocumentDto2() {
        return sendDocumentDto(AUTHOR_2, COMMENT_2, CONTENT_BASE64_2, METADATA_LIST_2, DOCUMENT_NUMBER_2,
                DOCUMENT_TYPE_2, FILE_NAME_2, MIME_TYPE_2, SYSTEM_2, SYSTEM_USER_ID_2);
    }

    public static SendDocumentDto sendDocumentDto3() {
        return sendDocumentDto(AUTHOR_3, COMMENT_3, CONTENT_BASE64_3, METADATA_LIST_3, DOCUMENT_NUMBER_3,
                DOCUMENT_TYPE_3, FILE_NAME_3, MIME_TYPE_3, SYSTEM_3, SYSTEM_USER_ID_3);
    }

    public static SendDocumentDto sendDocumentDto4() {
        return sendDocumentDto(AUTHOR_4, COMMENT_4, CONTENT_BASE64_4, METADATA_LIST_4, DOCUMENT_NUMBER_4,
                DOCUMENT_TYPE_4, FILE_NAME_4, MIME_TYPE_4, SYSTEM_4, SYSTEM_USER_ID_4);
    }

    private static SendDocumentDto sendDocumentDto(final String author, final String comment, final String contentBase64, final List<MetadataDto> metadataList,
                                                   final String documentNumber, final String documentType, final String fileName, final String mimeType,
                                                   final String system, final String systemUserId) {
        final SendDocumentDto sendDocumentDto = new SendDocumentDto();
        sendDocumentDto.setAuthor(author);
        sendDocumentDto.setComment(comment);
        sendDocumentDto.setContentBase64(contentBase64);
        sendDocumentDto.setMetadataList(metadataList);
        sendDocumentDto.setDocumentNumber(documentNumber);
        sendDocumentDto.setDocumentType(documentType);
        sendDocumentDto.setFileName(fileName);
        sendDocumentDto.setMimeType(mimeType);
        sendDocumentDto.setSystem(system);
        sendDocumentDto.setSystemUserId(systemUserId);
        return sendDocumentDto;
    }
}
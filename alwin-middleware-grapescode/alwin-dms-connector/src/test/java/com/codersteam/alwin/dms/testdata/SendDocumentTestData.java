package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentMetadataDTO;
import pl.aliorleasing.dms.SendDocumentRequest;
import pl.aliorleasing.dms.SendDocumentRequest.DocumentMetadataList;

import java.util.Arrays;

import static com.codersteam.alwin.dms.testdata.MetadataTestData.*;

public class SendDocumentTestData extends SendDocumentDtoTestData {

    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_1 = documentMetadataList(metadata1(), metadata2(), metadata3(), metadata4());
    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_2 = null;
    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_3 = null;
    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_4 = null;

    public static SendDocumentRequest sendDocument1() {
        return sendDocument(AUTHOR_1, COMMENT_1, CONTENT_BASE64_1, DOCUMENT_METADATA_LIST_1,
                DOCUMENT_NUMBER_1, DOCUMENT_TYPE_1, FILE_NAME_1, MIME_TYPE_1, SYSTEM_1, SYSTEM_USER_ID_1);
    }

    public static SendDocumentRequest sendDocument2() {
        return sendDocument(AUTHOR_2, COMMENT_2, CONTENT_BASE64_2, DOCUMENT_METADATA_LIST_2,
                DOCUMENT_NUMBER_2, DOCUMENT_TYPE_2, FILE_NAME_2, MIME_TYPE_2, SYSTEM_2, SYSTEM_USER_ID_2);
    }

    public static SendDocumentRequest sendDocument3() {
        return sendDocument(AUTHOR_3, COMMENT_3, CONTENT_BASE64_3, DOCUMENT_METADATA_LIST_3,
                DOCUMENT_NUMBER_3, DOCUMENT_TYPE_3, FILE_NAME_3, MIME_TYPE_3, SYSTEM_3, SYSTEM_USER_ID_3);
    }

    public static SendDocumentRequest sendDocument4() {
        return sendDocument(AUTHOR_4, COMMENT_4, CONTENT_BASE64_4, DOCUMENT_METADATA_LIST_4,
                DOCUMENT_NUMBER_4, DOCUMENT_TYPE_4, FILE_NAME_4, MIME_TYPE_4, SYSTEM_4, SYSTEM_USER_ID_4);
    }

    private static SendDocumentRequest sendDocument(final String author, final String comment, final String contentBase64,
                                                    final DocumentMetadataList documentMetadataList, final String documentNumber, final String documentType,
                                                    final String fileName, final String mimeType, final String system, final String systemUserId) {
        final SendDocumentRequest sendDocumentDto = new SendDocumentRequest();
        sendDocumentDto.setAuthor(author);
        sendDocumentDto.setComment(comment);
        sendDocumentDto.setContentBase64(contentBase64);
        sendDocumentDto.setDocumentMetadataList(documentMetadataList);
        sendDocumentDto.setDocumentNumber(documentNumber);
        sendDocumentDto.setDocumentType(documentType);
        sendDocumentDto.setFileName(fileName);
        sendDocumentDto.setMimeType(mimeType);
        sendDocumentDto.setSystem(system);
        sendDocumentDto.setSystemUserId(systemUserId);
        return sendDocumentDto;
    }

    private static DocumentMetadataList documentMetadataList(final DocumentMetadataDTO... metadata) {
        final DocumentMetadataList documentMetadataList = new DocumentMetadataList();
        documentMetadataList.getDocumentMetadata().addAll(Arrays.asList(metadata));
        return documentMetadataList;
    }
}
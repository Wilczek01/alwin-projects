package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentDTO;
import pl.aliorleasing.dms.DocumentDTO.DocumentMetadataList;
import pl.aliorleasing.dms.DocumentMetadataDTO;
import pl.aliorleasing.dms.DocumentResponseStatus;
import pl.aliorleasing.dms.GeneratedDocument;

import java.util.Arrays;

import static com.codersteam.alwin.dms.testdata.MetadataTestData.*;

/**
 * @author Michal Horowic
 */
public class DocumentTestData extends DocumentDtoTestData {
    private static final DocumentResponseStatus RESPONSE_STATUS = DocumentResponseStatus.OK;

    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_1 = documentMetadataList(metadata1(), metadata2(), metadata3(), metadata4());
    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_2 = null;
    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_3 = null;
    private static final DocumentMetadataList DOCUMENT_METADATA_LIST_4 = null;

    public static DocumentDTO document1() {
        return document(AUTHOR_1, COMMENT_1, DOCUMENT_GENERATOR_REQUEST_ID_1, DOCUMENT_HASH_1, DOCUMENT_ID_1,
                DOCUMENT_METADATA_LIST_1, DOCUMENT_NUMBER_1, DOCUMENT_TEMPLATE_ID_1, DOCUMENT_TYPE_1, HTML_BASE64CONTENT_1, HTML_DOCUMENT_URL_1, MIME_TYPE_1,
                PDF_CONTENT_BASE64_1, PDF_DOCUMENT_URL_1, SYSTEM_1, SYSTEM_USER_ID_1);

    }

    public static DocumentDTO document2() {
        return document(AUTHOR_2, COMMENT_2, DOCUMENT_GENERATOR_REQUEST_ID_2, DOCUMENT_HASH_2, DOCUMENT_ID_2,
                DOCUMENT_METADATA_LIST_2, DOCUMENT_NUMBER_2, DOCUMENT_TEMPLATE_ID_2, DOCUMENT_TYPE_2, HTML_BASE64CONTENT_2, HTML_DOCUMENT_URL_2, MIME_TYPE_2,
                PDF_CONTENT_BASE64_2, PDF_DOCUMENT_URL_2, SYSTEM_2, SYSTEM_USER_ID_2);
    }

    public static DocumentDTO document3() {
        return document(AUTHOR_3, COMMENT_3, DOCUMENT_GENERATOR_REQUEST_ID_3, DOCUMENT_HASH_3, DOCUMENT_ID_3,
                DOCUMENT_METADATA_LIST_3, DOCUMENT_NUMBER_3, DOCUMENT_TEMPLATE_ID_3, DOCUMENT_TYPE_3, HTML_BASE64CONTENT_3, HTML_DOCUMENT_URL_3, MIME_TYPE_3,
                PDF_CONTENT_BASE64_3, PDF_DOCUMENT_URL_3, SYSTEM_3, SYSTEM_USER_ID_3);
    }

    public static DocumentDTO document4() {
        return document(AUTHOR_4, COMMENT_4, DOCUMENT_GENERATOR_REQUEST_ID_4, DOCUMENT_HASH_4, DOCUMENT_ID_4,
                DOCUMENT_METADATA_LIST_4, DOCUMENT_NUMBER_4, DOCUMENT_TEMPLATE_ID_4, DOCUMENT_TYPE_4, HTML_BASE64CONTENT_4, HTML_DOCUMENT_URL_4, MIME_TYPE_4,
                PDF_CONTENT_BASE64_4, PDF_DOCUMENT_URL_4, SYSTEM_4, SYSTEM_USER_ID_4);
    }

    private static DocumentDTO document(final String author, final String comment, final Long documentGeneratorRequestId, final String documentHash,
                                        final Long documentId, final DocumentMetadataList documentMetadataList, final String documentNumber,
                                        final String documentTemplateId, final String documentType, String htmlBase64Content, final String htmlDocumentUrl,
                                        final String mimeType, final String pdfContentBase64, final String pdfDocumentUrl, final String system,
                                        final String systemUserId) {
        final DocumentDTO documentDto = new DocumentDTO();
        documentDto.setAuthor(author);
        documentDto.setComment(comment);
        documentDto.setDocumentGeneratorRequestId(documentGeneratorRequestId);
        documentDto.setDocumentHash(documentHash);
        documentDto.setDocumentId(documentId);
        documentDto.setDocumentMetadataList(documentMetadataList);
        documentDto.setDocumentNumber(documentNumber);
        documentDto.setDocumentTemplateId(documentTemplateId);
        documentDto.setDocumentType(documentType);
        documentDto.setHtmlBase64Content(htmlBase64Content);
        documentDto.setHtmlDocumentUrl(htmlDocumentUrl);
        documentDto.setMimeType(mimeType);
        documentDto.setPdfContentBase64(pdfContentBase64);
        documentDto.setPdfDocumentUrl(pdfDocumentUrl);
        documentDto.setSystem(system);
        documentDto.setSystemUserId(systemUserId);
        return documentDto;
    }

    static DocumentMetadataList documentMetadataList(final DocumentMetadataDTO... metadata) {
        final DocumentMetadataList documentMetadataList = new DocumentMetadataList();
        documentMetadataList.getDocumentMetadata().addAll(Arrays.asList(metadata));
        return documentMetadataList;
    }

    public static GeneratedDocument generatedDocument(final boolean isIntegrationTest) {
        final GeneratedDocument response = new GeneratedDocument();
        response.setStatus(RESPONSE_STATUS);
        response.setDocument(documentDTO(isIntegrationTest));
        return response;
    }

    public static DocumentDTO documentDTO(final boolean isIntegrationTest) {
        final DocumentDTO document = new DocumentDTO();
        document.setMimeType(MIME_TYPE);
        document.setPdfContentBase64(getBase64Content(isIntegrationTest));
        return document;
    }
}

package com.codersteam.alwin.dms.testdata;

import com.codersteam.alwin.dms.model.DocumentDto;
import com.codersteam.alwin.dms.model.MetadataDto;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.codersteam.alwin.dms.testdata.MetadataDtoTestData.*;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * @author Piotr Naroznik
 */
public class DocumentDtoTestData {

    public static final String AUTHOR_1 = "AUTHOR_1";
    public static final String COMMENT_1 = "COMMENT_1";
    public static final Long DOCUMENT_GENERATOR_REQUEST_ID_1 = 6706152L;
    public static final String DOCUMENT_HASH_1 = "DOCUMENT_HASH_1";
    public static final Long DOCUMENT_ID_1 = 9038164L;
    public static final List<MetadataDto> METADATA_LIST_1 = Arrays.asList(metadataDto1(), metadataDto2(), metadataDto3(), metadataDto4());
    public static final String DOCUMENT_NUMBER_1 = "DOCUMENT_NUMBER_1";
    public static final String DOCUMENT_TEMPLATE_ID_1 = "DOCUMENT_TEMPLATE_ID_1";
    public static final String DOCUMENT_TYPE_1 = "DOCUMENT_TYPE_1";
    public static final String HTML_BASE64CONTENT_1 = "HTML_BASE64CONTENT_1";
    public static final String HTML_DOCUMENT_URL_1 = "HTML_DOCUMENT_URL_1";
    public static final String MIME_TYPE_1 = "MIME_TYPE_1";
    public static final String PDF_CONTENT_BASE64_1 = "PDF_CONTENT_BASE64_1";
    public static final String PDF_DOCUMENT_URL_1 = "PDF_DOCUMENT_URL_1";
    public static final String SYSTEM_1 = "SYSTEM_1";
    public static final String SYSTEM_USER_ID_1 = "SYSTEM_USER_ID_1";
    public static final String AUTHOR_2 = "AUTHOR_2";
    public static final String COMMENT_2 = "COMMENT_2";
    public static final Long DOCUMENT_GENERATOR_REQUEST_ID_2 = 7666019L;
    public static final String DOCUMENT_HASH_2 = "DOCUMENT_HASH_2";
    public static final Long DOCUMENT_ID_2 = 3701299L;
    public static final List<MetadataDto> METADATA_LIST_2 = null;
    public static final String DOCUMENT_NUMBER_2 = "DOCUMENT_NUMBER_2";
    public static final String DOCUMENT_TEMPLATE_ID_2 = "DOCUMENT_TEMPLATE_ID_2";
    public static final String DOCUMENT_TYPE_2 = "DOCUMENT_TYPE_2";
    public static final String HTML_BASE64CONTENT_2 = "HTML_BASE64CONTENT_2";
    public static final String HTML_DOCUMENT_URL_2 = "HTML_DOCUMENT_URL_2";
    public static final String MIME_TYPE_2 = "MIME_TYPE_2";
    public static final String PDF_CONTENT_BASE64_2 = "PDF_CONTENT_BASE64_2";
    public static final String PDF_DOCUMENT_URL_2 = "PDF_DOCUMENT_URL_2";
    public static final String SYSTEM_2 = "SYSTEM_2";
    public static final String SYSTEM_USER_ID_2 = "SYSTEM_USER_ID_2";
    public static final String AUTHOR_3 = "AUTHOR_3";
    public static final String COMMENT_3 = "COMMENT_3";
    public static final Long DOCUMENT_GENERATOR_REQUEST_ID_3 = 3108895L;
    public static final String DOCUMENT_HASH_3 = "DOCUMENT_HASH_3";
    public static final Long DOCUMENT_ID_3 = 1755590L;
    public static final List<MetadataDto> METADATA_LIST_3 = null;
    public static final String DOCUMENT_NUMBER_3 = "DOCUMENT_NUMBER_3";
    public static final String DOCUMENT_TEMPLATE_ID_3 = "DOCUMENT_TEMPLATE_ID_3";
    public static final String DOCUMENT_TYPE_3 = "DOCUMENT_TYPE_3";
    public static final String HTML_BASE64CONTENT_3 = "HTML_BASE64CONTENT_3";
    public static final String HTML_DOCUMENT_URL_3 = "HTML_DOCUMENT_URL_3";
    public static final String MIME_TYPE_3 = "MIME_TYPE_3";
    public static final String PDF_CONTENT_BASE64_3 = "PDF_CONTENT_BASE64_3";
    public static final String PDF_DOCUMENT_URL_3 = "PDF_DOCUMENT_URL_3";
    public static final String SYSTEM_3 = "SYSTEM_3";
    public static final String SYSTEM_USER_ID_3 = "SYSTEM_USER_ID_3";
    public static final String AUTHOR_4 = "AUTHOR_4";
    public static final String COMMENT_4 = "COMMENT_4";
    public static final Long DOCUMENT_GENERATOR_REQUEST_ID_4 = 665248L;
    public static final String DOCUMENT_HASH_4 = "DOCUMENT_HASH_4";
    public static final Long DOCUMENT_ID_4 = 989106L;
    public static final List<MetadataDto> METADATA_LIST_4 = null;
    public static final String DOCUMENT_NUMBER_4 = "DOCUMENT_NUMBER_4";
    public static final String DOCUMENT_TEMPLATE_ID_4 = "DOCUMENT_TEMPLATE_ID_4";
    public static final String DOCUMENT_TYPE_4 = "DOCUMENT_TYPE_4";
    public static final String HTML_BASE64CONTENT_4 = "HTML_BASE64CONTENT_4";
    public static final String HTML_DOCUMENT_URL_4 = "HTML_DOCUMENT_URL_4";
    public static final String MIME_TYPE_4 = "MIME_TYPE_4";
    public static final String PDF_CONTENT_BASE64_4 = "PDF_CONTENT_BASE64_4";
    public static final String PDF_DOCUMENT_URL_4 = "PDF_DOCUMENT_URL_4";
    public static final String SYSTEM_4 = "SYSTEM_4";
    public static final String SYSTEM_USER_ID_4 = "SYSTEM_USER_ID_4";
    protected static final String MIME_TYPE = "application/pdf";

    public static DocumentDto documentDto1() {
        return documentDto(AUTHOR_1, COMMENT_1, DOCUMENT_GENERATOR_REQUEST_ID_1, DOCUMENT_HASH_1, DOCUMENT_ID_1,
                METADATA_LIST_1, DOCUMENT_NUMBER_1, DOCUMENT_TEMPLATE_ID_1, DOCUMENT_TYPE_1, HTML_BASE64CONTENT_1, HTML_DOCUMENT_URL_1, MIME_TYPE_1,
                PDF_CONTENT_BASE64_1, PDF_DOCUMENT_URL_1, SYSTEM_1, SYSTEM_USER_ID_1);
    }

    public static DocumentDto documentDto2() {
        return documentDto(AUTHOR_2, COMMENT_2, DOCUMENT_GENERATOR_REQUEST_ID_2, DOCUMENT_HASH_2, DOCUMENT_ID_2,
                METADATA_LIST_2, DOCUMENT_NUMBER_2, DOCUMENT_TEMPLATE_ID_2, DOCUMENT_TYPE_2, HTML_BASE64CONTENT_2, HTML_DOCUMENT_URL_2, MIME_TYPE_2,
                PDF_CONTENT_BASE64_2, PDF_DOCUMENT_URL_2, SYSTEM_2, SYSTEM_USER_ID_2);
    }

    public static DocumentDto documentDto3() {
        return documentDto(AUTHOR_3, COMMENT_3, DOCUMENT_GENERATOR_REQUEST_ID_3, DOCUMENT_HASH_3, DOCUMENT_ID_3,
                METADATA_LIST_3, DOCUMENT_NUMBER_3, DOCUMENT_TEMPLATE_ID_3, DOCUMENT_TYPE_3, HTML_BASE64CONTENT_3, HTML_DOCUMENT_URL_3, MIME_TYPE_3,
                PDF_CONTENT_BASE64_3, PDF_DOCUMENT_URL_3, SYSTEM_3, SYSTEM_USER_ID_3);
    }

    public static DocumentDto documentDto4() {
        return documentDto(AUTHOR_4, COMMENT_4, DOCUMENT_GENERATOR_REQUEST_ID_4, DOCUMENT_HASH_4, DOCUMENT_ID_4, METADATA_LIST_4, DOCUMENT_NUMBER_4, DOCUMENT_TEMPLATE_ID_4, DOCUMENT_TYPE_4, HTML_BASE64CONTENT_4, HTML_DOCUMENT_URL_4, MIME_TYPE_4, PDF_CONTENT_BASE64_4, PDF_DOCUMENT_URL_4, SYSTEM_4, SYSTEM_USER_ID_4);
    }

    public static DocumentDto documentDto(final String author, String comment, Long documentGeneratorRequestId, String documentHash, Long documentId,
                                          List<MetadataDto> metadataList, String documentNumber, String documentTemplateId, String documentType,
                                          String htmlBase64Content, String htmlDocumentUrl, String mimeType, String pdfContentBase64, String pdfDocumentUrl,
                                          String system, String systemUserId) {
        final DocumentDto documentDto = new DocumentDto();
        documentDto.setAuthor(author);
        documentDto.setComment(comment);
        documentDto.setDocumentGeneratorRequestId(documentGeneratorRequestId);
        documentDto.setDocumentHash(documentHash);
        documentDto.setDocumentId(documentId);
        documentDto.setMetadataList(metadataList);
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


    public static DocumentDto documentDto(final boolean integrationTests) {
        final DocumentDto documentDto = new DocumentDto();
        documentDto.setMimeType(MIME_TYPE);
        documentDto.setPdfContentBase64(getBase64Content(integrationTests));
        return documentDto;
    }

    public static String getBase64Content(final boolean integrationTests) {
        if (integrationTests) {
            return getBase64ContentIntegrationTests();
        }
        return getBase64Content();
    }

    private static String getBase64ContentIntegrationTests() {
        try {
            final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            final InputStream responseInputStream = classloader.getResourceAsStream(getPath());
            final byte[] bytes = IOUtils.toByteArray(responseInputStream);
            return new String(Base64.getEncoder().encode(bytes));
        } catch (final IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static String getBase64Content() {
        try {
            final byte[] bytes = readAllBytes(get(DocumentDtoTestData.class.getResource(getPath()).toURI()));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (final IOException | URISyntaxException e) {
            throw new IllegalStateException("Can not read test pdf file");
        }
    }

    public static String getPath() {
        return "/pdf/test.pdf";
    }
}

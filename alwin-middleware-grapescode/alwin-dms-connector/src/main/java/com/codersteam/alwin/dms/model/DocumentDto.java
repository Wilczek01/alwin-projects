package com.codersteam.alwin.dms.model;

import java.util.List;

/**
 * @author Piotr Naroznik
 */
public class DocumentDto {
    private String author;
    private String comment;
    private Long documentGeneratorRequestId;
    private String documentHash;
    private Long documentId;
    private List<MetadataDto> metadataList;
    private String documentNumber;
    private String documentTemplateId;
    private String documentType;
    private String htmlBase64Content;
    private String htmlDocumentUrl;
    private String mimeType;
    private String pdfContentBase64;
    private String pdfDocumentUrl;
    private String system;
    private String systemUserId;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Long getDocumentGeneratorRequestId() {
        return documentGeneratorRequestId;
    }

    public void setDocumentGeneratorRequestId(final Long documentGeneratorRequestId) {
        this.documentGeneratorRequestId = documentGeneratorRequestId;
    }

    public String getDocumentHash() {
        return documentHash;
    }

    public void setDocumentHash(final String documentHash) {
        this.documentHash = documentHash;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(final Long documentId) {
        this.documentId = documentId;
    }

    public List<MetadataDto> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(final List<MetadataDto> metadataList) {
        this.metadataList = metadataList;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(final String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentTemplateId() {
        return documentTemplateId;
    }

    public void setDocumentTemplateId(final String documentTemplateId) {
        this.documentTemplateId = documentTemplateId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    public String getHtmlBase64Content() {
        return htmlBase64Content;
    }

    public void setHtmlBase64Content(final String htmlBase64Content) {
        this.htmlBase64Content = htmlBase64Content;
    }

    public String getHtmlDocumentUrl() {
        return htmlDocumentUrl;
    }

    public void setHtmlDocumentUrl(final String htmlDocumentUrl) {
        this.htmlDocumentUrl = htmlDocumentUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPdfContentBase64() {
        return pdfContentBase64;
    }

    public void setPdfContentBase64(final String pdfContentBase64) {
        this.pdfContentBase64 = pdfContentBase64;
    }

    public String getPdfDocumentUrl() {
        return pdfDocumentUrl;
    }

    public void setPdfDocumentUrl(final String pdfDocumentUrl) {
        this.pdfDocumentUrl = pdfDocumentUrl;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(final String system) {
        this.system = system;
    }

    public String getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(final String systemUserId) {
        this.systemUserId = systemUserId;
    }
}
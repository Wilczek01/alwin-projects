package com.codersteam.alwin.dms.model;

import java.util.List;

/**
 * @author Piotr Naroznik
 */
public class SendDocumentDto {
    private String author;
    private String comment;
    private String contentBase64;
    private List<MetadataDto> metadataList;
    private String documentNumber;
    private String documentType;
    private String fileName;
    private String mimeType;
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

    public String getContentBase64() {
        return contentBase64;
    }

    public void setContentBase64(final String contentBase64) {
        this.contentBase64 = contentBase64;
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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
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

    @Override
    public String toString() {
        return "SendDocumentDto{" +
                "author='" + author + '\'' +
                ", comment='" + comment + '\'' +
                ", contentBase64='" + contentBase64 + '\'' +
                ", metadataList=" + metadataList +
                ", documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", system='" + system + '\'' +
                ", systemUserId='" + systemUserId + '\'' +
                '}';
    }
}
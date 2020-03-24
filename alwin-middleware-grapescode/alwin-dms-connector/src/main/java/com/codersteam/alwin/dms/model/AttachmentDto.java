package com.codersteam.alwin.dms.model;

/**
 * @author Piotr Naroznik
 */
public class AttachmentDto {
    private String contentBase64;
    private String mimeType;
    private String name;

    public String getContentBase64() {
        return contentBase64;
    }

    public void setContentBase64(final String contentBase64) {
        this.contentBase64 = contentBase64;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "contentBase64='" + contentBase64 + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

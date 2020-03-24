package com.codersteam.alwin.core.api.model.activity;

/**
 * @author Adam Stepnowski
 */
public class ActivityAttachmentDto {

    private ActivityDto activity;
    private String filename;
    private String uploadedInputStream;

    public String getUploadedInputStream() {
        return uploadedInputStream;
    }

    public void setUploadedInputStream(final String uploadedInputStream) {
        this.uploadedInputStream = uploadedInputStream;
    }

    public ActivityDto getActivity() {
        return activity;
    }

    public void setActivity(final ActivityDto activity) {
        this.activity = activity;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }
}

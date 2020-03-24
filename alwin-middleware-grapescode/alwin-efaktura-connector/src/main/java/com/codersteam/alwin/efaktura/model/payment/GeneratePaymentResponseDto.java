package com.codersteam.alwin.efaktura.model.payment;

import java.util.Date;

/**
 * @author Dariusz Rackowski
 */
public class GeneratePaymentResponseDto {
    private Date creationDate;
    private String error;
    private Long id;
    private String requestNo;
    private Date sendDate;
    private String status;
    private Date updateDate;
    private String url;

    public GeneratePaymentResponseDto() {
    }

    public GeneratePaymentResponseDto(final String status, final String url) {
        this.status = status;
        this.url = url;
    }

    public GeneratePaymentResponseDto(final String status, final String url, final String error) {
        this.status = status;
        this.url = url;
        this.error = error;
    }


    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(final String requestNo) {
        this.requestNo = requestNo;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(final Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}

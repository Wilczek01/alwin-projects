package com.codersteam.alwin.core.api.model.customer;

/**
 * @author Piotr Narożnik
 */
public class ContactDetailDto {
    private Long id;
    private String remark;
    private String contact;
    private ContactTypeDto contactType;
    private ContactStateDto state;
    private Boolean sendAutomaticSms;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public ContactTypeDto getContactType() {
        return contactType;
    }

    public void setContactType(final ContactTypeDto contactType) {
        this.contactType = contactType;
    }

    public ContactStateDto getState() {
        return state;
    }

    public void setState(final ContactStateDto state) {
        this.state = state;
    }

    public Boolean getSendAutomaticSms() {
        return sendAutomaticSms;
    }

    public void setSendAutomaticSms(final Boolean sendAutomaticSms) {
        this.sendAutomaticSms = sendAutomaticSms;
    }

    @Override
    public String toString() {
        return "ContactDetailDto{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", contact='" + contact + '\'' +
                ", contactType=" + contactType +
                ", state=" + state +
                ", sendAutomaticSms=" + sendAutomaticSms +
                '}';
    }
}

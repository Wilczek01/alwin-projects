package com.codersteam.alwin.jpa.customer;

import com.codersteam.alwin.jpa.type.ContactImportedType;
import com.codersteam.alwin.jpa.type.ContactState;
import com.codersteam.alwin.jpa.type.ContactType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "contact_detail")
public class ContactDetail {

    @SequenceGenerator(name = "contactdetailSEQ", sequenceName = "contact_detail_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactdetailSEQ")
    private Long id;

    @Column(name = "contact", length = 100, nullable = false)
    private String contact;

    @Column(name = "contact_type", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactState state;

    @Column
    private String remark;

    @NotAudited
    @Column(name = "imported_from_aida", nullable = false)
    private boolean importedFromAida;

    @NotAudited
    @Column(name = "imported_type", length = 100)
    @Enumerated(EnumType.STRING)
    private ContactImportedType importedType;

    @Column(name = "send_automatic_sms")
    private Boolean sendAutomaticSms;

    public ContactDetail() {
    }

    public ContactDetail(final String contact, final ContactType contactType, final ContactState state, final String remark, final boolean importedFromAida,
                         final ContactImportedType importedType) {
        this.importedFromAida = importedFromAida;
        this.importedType = importedType;
        this.contactType = contactType;
        this.contact = contact;
        this.remark = remark;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(final ContactType contactType) {
        this.contactType = contactType;
    }

    public ContactState getState() {
        return state;
    }

    public void setState(final ContactState state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public boolean isImportedFromAida() {
        return importedFromAida;
    }

    public void setImportedFromAida(final boolean importedFromAida) {
        this.importedFromAida = importedFromAida;
    }

    public ContactImportedType getImportedType() {
        return importedType;
    }

    public void setImportedType(final ContactImportedType importedType) {
        this.importedType = importedType;
    }

    public Boolean getSendAutomaticSms() {
        return sendAutomaticSms;
    }

    public void setSendAutomaticSms(final Boolean sendAutomaticSms) {
        this.sendAutomaticSms = sendAutomaticSms;
    }

    @Override
    public String toString() {
        return "ContactDetail{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", contactType=" + contactType +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                ", importedFromAida=" + importedFromAida +
                ", importedType=" + importedType +
                ", sendAutomaticSms=" + sendAutomaticSms +
                '}';
    }
}

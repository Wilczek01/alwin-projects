package com.codersteam.alwin.jpa.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "company_person")
@IdClass(CompanyPersonId.class)
public class CompanyPerson {

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Company company;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Person person;

    @Column(name = "contactPerson", nullable = false)
    private boolean contactPerson;

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public boolean isContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final boolean contactPerson) {
        this.contactPerson = contactPerson;
    }

}
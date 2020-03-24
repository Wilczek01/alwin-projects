package com.codersteam.alwin.jpa.customer;

import java.io.Serializable;

/**
 * @author Piotr Naroznik
 */
public class CompanyPersonId implements Serializable {

    private Company company;
    private Person person;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CompanyPersonId that = (CompanyPersonId) o;

        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        return person != null ? person.equals(that.person) : that.person == null;
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }
}

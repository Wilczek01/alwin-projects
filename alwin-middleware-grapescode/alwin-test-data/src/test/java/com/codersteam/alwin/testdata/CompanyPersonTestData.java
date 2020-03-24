package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.Person;

import java.util.List;

import static com.codersteam.alwin.testdata.CompanyTestData.company1;
import static com.codersteam.alwin.testdata.CompanyTestData.company2;
import static com.codersteam.alwin.testdata.PersonTestData.createPerson;
import static com.codersteam.alwin.testdata.PersonTestData.person1;
import static com.codersteam.alwin.testdata.PersonTestData.person2;
import static java.util.Arrays.asList;

/**
 * @author Piotr Naroznik
 */
public class CompanyPersonTestData {

    public static CompanyPerson companyPerson1() {
        return companyPerson(person1(), false);
    }

    public static CompanyPerson companyPerson2() {
        return companyPerson(person2(), true);
    }

    public static List<CompanyPerson> company2Persons() {
        return asList(companyPerson1(), companyPerson2());
    }

    public static CompanyPerson expectedCompanyPerson() {
        final CompanyPerson companyPerson = companyPerson(person2(), true);
        fillCompany(company2(), companyPerson);
        return companyPerson;
    }

    public static CompanyPerson createCompanyPerson() {
        final CompanyPerson companyPerson = companyPerson(createPerson(), false);
        fillCompany(company1(), companyPerson);
        return companyPerson;
    }

    public static List<CompanyPerson> fillCompany(final Company company, final List<CompanyPerson> companyPersons) {
        if (companyPersons != null && !companyPersons.isEmpty()) {
            companyPersons.forEach(companyPerson -> fillCompany(company, companyPerson));
        }
        return companyPersons;
    }

    private static void fillCompany(final Company company, final CompanyPerson companyPerson) {
        companyPerson.setCompany(company);
    }


    public static CompanyPerson companyPerson(final Person person, final boolean contactPerson) {
        final CompanyPerson companyPerson = new CompanyPerson();
        companyPerson.setPerson(person);
        companyPerson.setContactPerson(contactPerson);
        return companyPerson;
    }

}

package com.codersteam.alwin.testdata;


import com.codersteam.alwin.core.api.model.customer.CompanyDto;
import com.codersteam.alwin.core.api.model.customer.CustomerDto;
import com.codersteam.alwin.core.api.model.customer.PersonDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.customer.Person;
import com.codersteam.alwin.jpa.operator.Operator;

import static com.codersteam.alwin.testdata.CompanyTestData.*;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator12;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorUserDto12;
import static com.codersteam.alwin.testdata.PersonTestData.person1;
import static com.codersteam.alwin.testdata.PersonTestData.personDto1;

@SuppressWarnings("WeakerAccess")
public class CustomerTestData {

    public static final long NON_EXISTING_ID = -1L;
    public static final long ID_1 = 1L;
    public static final long ID_2 = 2L;
    public static final long ID_3 = 3L;
    public static final long ID_4 = 4L;
    public static final long ID_5 = 5L;
    public static final long ID_7 = 7L;
    public static final long ID_8 = 8L;
    public static final long ID_9 = 9L;
    public static final long ID_10 = 10L;
    public static final long ID_11 = 11L;
    public static final long ID_12 = 12L;
    public static final long ID_13 = 13L;
    public static final long ID_14 = 14L;
    public static final long ID_15 = 15L;
    public static final long ID_16 = 16L;
    public static final long ID_17 = 17L;
    public static final long ID_18 = 18L;
    public static final long ID_19 = 19L;
    public static final long ID_20 = 20L;
    public static final long ID_23 = 23L;
    public static final long ID_24 = 24L;
    public static final long ID_26 = 26L;

    public static Customer testCustomer1() {
        return customer(ID_1, company1(), person1(), testOperator12());
    }

    public static Customer testCustomer3() {
        return customer(ID_3, company1(), person1(), null);
    }

    public static Customer testCustomer4() {
        return customer(ID_4, company2(), null, null);
    }

    public static Customer testCustomer5() {
        return customer(ID_5, company3(), null, null);
    }

    public static Customer testCustomer7() {
        return customer(ID_7, company5(), null, null);
    }

    public static Customer testCustomer8() {
        return customer(ID_8, company6(), null, null);
    }

    public static Customer testCustomer9() {
        return customer(ID_9, company7(), null, null);
    }

    public static Customer testCustomer10() {
        return customer(ID_10, company8(), null, null);
    }

    public static Customer testCustomer11() {
        return customer(ID_11, company9(), null, null);
    }

    public static Customer testCustomer12() {
        return customer(ID_12, company10(), null, null);
    }

    public static Customer testCustomer13() {
        return customer(ID_13, company11(), null, null);
    }

    public static Customer testCustomer14() {
        return customer(ID_14, company12(), null, null);
    }

    public static Customer testCustomer15() {
        return customer(ID_15, company13(), null, null);
    }

    public static Customer testCustomer16() {
        return customer(ID_16, company14(), null, null);
    }

    public static Customer testCustomer17() {
        return customer(ID_17, company15(), null, null);
    }

    public static Customer testCustomer18() {
        return customer(ID_18, company16(), null, null);
    }

    public static Customer testCustomer19() {
        return customer(ID_19, company17(), null, null);
    }

    public static Customer testCustomer20() {
        return customer(ID_20, company18(), null, null);
    }

    public static Customer testCustomer23() {
        return customer(ID_23, company21(), null, null);
    }

    public static Customer testCustomer24() {
        return customer(ID_24, company22(), null, null);
    }

    public static Customer testCustomer26() {
        return customer(ID_26, company24(), null, null);
    }

    public static CustomerDto testCustomerDto1() {
        return customerDto(ID_1, companyDto1(), personDto1(), testOperatorUserDto12());
    }

    public static CustomerDto testCustomerDto2() {
        return customerDto(ID_2, companyDto1(), personDto1(), null);
    }

    public static CustomerDto testCustomerDto3() {
        return customerDto(ID_3, companyDto1(), personDto1(), null);
    }

    public static CustomerDto testCustomerDto5() {
        return customerDto(ID_5, companyDto3(), null, null);
    }

    public static CustomerDto testCustomerDto7() {
        return customerDto(ID_7, companyDto5(), null, null);
    }

    public static CustomerDto testCustomerDto8() {
        return customerDto(ID_8, companyDto6(), null, null);
    }

    public static CustomerDto testCustomerDto9() {
        return customerDto(ID_9, companyDto7(), null, null);
    }

    public static CustomerDto testCustomerDto11() {
        return customerDto(ID_11, companyDto9(), null, null);
    }

    public static CustomerDto testCustomerDto12() {
        return customerDto(ID_12, companyDto10(), null, null);
    }

    public static CustomerDto testCustomerDto16() {
        return customerDto(ID_16, companyDto14(), null, null);
    }

    public static CustomerDto testCustomerDto20() {
        return customerDto(ID_20, companyDto18(), null, null);
    }

    public static CustomerDto testCustomerDto24() {
        return customerDto(ID_24, companyDto22(), null, null);
    }

    private static Customer customer(final Long id, final Company company, final Person person, final Operator accountManager) {
        final Customer customer = new Customer();
        customer.setId(id);
        customer.setCompany(company);
        customer.setPerson(person);
        customer.setAccountManager(accountManager);
        return customer;
    }

    private static CustomerDto customerDto(final Long id, final CompanyDto company, final PersonDto person, final OperatorUserDto accountManager) {
        final CustomerDto customer = new CustomerDto();
        customer.setId(id);
        customer.setCompany(company);
        customer.setPerson(person);
        customer.setAccountManager(accountManager);
        return customer;
    }
}

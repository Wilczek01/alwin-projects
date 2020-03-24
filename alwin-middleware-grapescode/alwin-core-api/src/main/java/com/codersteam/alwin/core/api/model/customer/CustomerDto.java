package com.codersteam.alwin.core.api.model.customer;

import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;

/**
 * @author Michal Horowic
 */
public class CustomerDto {

    private Long id;
    private CompanyDto company;
    private PersonDto person;
    private OperatorUserDto accountManager;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(final CompanyDto company) {
        this.company = company;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(final PersonDto person) {
        this.person = person;
    }

    public OperatorUserDto getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(final OperatorUserDto accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", company=" + company +
                ", person=" + person +
                ", accountManager=" + accountManager +
                '}';
    }
}

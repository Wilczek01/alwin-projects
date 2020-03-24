package com.codersteam.alwin.core.api.model.contract;

import com.codersteam.alwin.core.api.model.customer.CustomerDto;

/**
 * @author Michal Horowic
 */
public class ContractDto {

    private Long id;
    private String extContractId;
    private CustomerDto customer;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getExtContractId() {
        return extContractId;
    }

    public void setExtContractId(final String extContractId) {
        this.extContractId = extContractId;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(final CustomerDto customer) {
        this.customer = customer;
    }
}

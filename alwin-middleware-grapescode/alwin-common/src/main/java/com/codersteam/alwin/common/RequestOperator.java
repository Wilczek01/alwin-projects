package com.codersteam.alwin.common;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestOperator {

    private Long operatorId;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(final Long operatorId) {
        this.operatorId = operatorId;
    }
}

package com.codersteam.alwin.core.api.model.postal;

import com.codersteam.alwin.core.api.model.operator.OperatorDto;

/**
 * @author Michal Horowic
 */
public class PostalCodeInputDto {
    private String mask;
    private OperatorDto operator;

    public String getMask() {
        return mask;
    }

    public void setMask(final String mask) {
        this.mask = mask;
    }

    public OperatorDto getOperator() {
        return operator;
    }

    public void setOperator(final OperatorDto operator) {
        this.operator = operator;
    }
}

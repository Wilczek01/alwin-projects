package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;

import java.util.Set;

/**
 * @author Tomasz Sliwinski
 */
public class IssueTypeWithOperatorTypesDto extends IssueTypeDto {

    private Set<OperatorTypeDto> operatorTypes;

    public Set<OperatorTypeDto> getOperatorTypes() {
        return operatorTypes;
    }

    public void setOperatorTypes(final Set<OperatorTypeDto> operatorTypes) {
        this.operatorTypes = operatorTypes;
    }
}

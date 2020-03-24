package com.codersteam.alwin.core.api.model.user;

import com.codersteam.alwin.core.api.model.operator.EditableOperatorDto;

import java.util.List;

/**
 * @author Michal Horowic
 */
public class EditableUserDto extends UserDto {

    private List<EditableOperatorDto> operators;

    public List<EditableOperatorDto> getOperators() {
        return operators;
    }

    public void setOperators(List<EditableOperatorDto> operators) {
        this.operators = operators;
    }

}

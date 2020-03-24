package com.codersteam.alwin.core.api.model.user;

import com.codersteam.alwin.core.api.model.operator.SimpleOperatorDto;

import java.util.Date;
import java.util.List;

/**
 * @author Michal Horowic
 */
public class FullUserDto extends UserDto {

    private List<SimpleOperatorDto> operators;
    private Date updateDate;
    private Date creationDate;

    public List<SimpleOperatorDto> getOperators() {
        return operators;
    }

    public void setOperators(List<SimpleOperatorDto> operators) {
        this.operators = operators;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

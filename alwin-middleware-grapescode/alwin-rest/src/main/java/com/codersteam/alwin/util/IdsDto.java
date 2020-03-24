package com.codersteam.alwin.util;

import java.util.List;

/**
 * @author Michal Horowic
 */
public class IdsDto {

    public IdsDto() {
    }

    public IdsDto(List<Long> ids) {
        this.ids = ids;
    }

    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}

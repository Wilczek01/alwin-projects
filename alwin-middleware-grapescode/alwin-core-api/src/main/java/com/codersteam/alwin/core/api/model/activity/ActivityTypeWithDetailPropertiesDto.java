package com.codersteam.alwin.core.api.model.activity;

import java.util.List;

/**
 * @author Michal Horowic
 */
public class ActivityTypeWithDetailPropertiesDto extends ActivityTypeDto {

    private List<ActivityDetailPropertyDto> detailProperties;

    public List<ActivityDetailPropertyDto> getDetailProperties() {
        return detailProperties;
    }

    public void setDetailProperties(final List<ActivityDetailPropertyDto> detailProperties) {
        this.detailProperties = detailProperties;
    }
}

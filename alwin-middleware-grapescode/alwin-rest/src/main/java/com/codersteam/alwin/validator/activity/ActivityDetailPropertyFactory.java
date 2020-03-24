package com.codersteam.alwin.validator.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa tworząca obiekty o typie i wartości przekazanym w szczegółach czynności windykacyjnej
 *
 * @author Michal Horowic
 */
public class ActivityDetailPropertyFactory {

    private static final Map<Class, CreateActivityDetailProperty> CREATORS = new HashMap<>();

    static {
        CREATORS.put(Boolean.class, new CreateActivityBooleanDetailProperty());
        CREATORS.put(Integer.class, new CreateActivityIntegerDetailProperty());
        CREATORS.put(String.class, new CreateActivityStringDetailProperty());
        CREATORS.put(Date.class, new CreateActivityDateDetailProperty());
    }


    public void parseDetailProperty(final ActivityDetailDto activityDetail) throws ClassNotFoundException {
        if (activityDetail.getDetailProperty() == null || activityDetail.getValue() == null) {
            return;
        }

        final Class<?> propertyClass = Class.forName(activityDetail.getDetailProperty().getType());
        CREATORS.get(propertyClass).parseDetailProperty(activityDetail);
    }
}

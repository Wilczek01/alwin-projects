package com.codersteam.alwin.core.api.model.activity;

import java.util.List;
import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class ActivityDetailPropertyDto {

    private Long id;
    private String name;
    private String key;
    private String type;
    private List<ActivityTypeDetailPropertyDictValueDto> dictionaryValues;

    public ActivityDetailPropertyDto() {
    }

    public ActivityDetailPropertyDto(final Long id, final String name, final String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public List<ActivityTypeDetailPropertyDictValueDto> getDictionaryValues() {
        return dictionaryValues;
    }

    public void setDictionaryValues(final List<ActivityTypeDetailPropertyDictValueDto> dictionaryValues) {
        this.dictionaryValues = dictionaryValues;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityDetailPropertyDto that = (ActivityDetailPropertyDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(key, that.key) &&
                Objects.equals(type, that.type) &&
                Objects.equals(dictionaryValues, that.dictionaryValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, key, type, dictionaryValues);
    }

    @Override
    public String toString() {
        return "ActivityDetailPropertyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", dictionaryValues=" + dictionaryValues +
                '}';
    }
}

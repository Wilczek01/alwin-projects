package com.codersteam.alwin.core.api.model.tag;

import com.codersteam.alwin.core.api.model.customer.TagTypeDto;

/**
 * Etykieta dla zlecenia bez identyfikatora
 *
 * @author Michal Horowic
 */
public class TagInputDto {
    private String name;
    private String color;
    private boolean predefined;
    private TagTypeDto type;
    private String description;
    private TagIconDto tagIcon;

    public boolean isPredefined() {
        return predefined;
    }

    public void setPredefined(final boolean predefined) {
        this.predefined = predefined;
    }

    public TagTypeDto getType() {
        return type;
    }

    public void setType(final TagTypeDto type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public TagIconDto getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(final TagIconDto tagIcon) {
        this.tagIcon = tagIcon;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TagInputDto that = (TagInputDto) o;

        if (predefined != that.predefined) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (type != that.type) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return tagIcon != null ? tagIcon.equals(that.tagIcon) : that.tagIcon == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (predefined ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tagIcon != null ? tagIcon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagInputDto{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", predefined=" + predefined +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", tagIcon=" + tagIcon +
                '}';
    }
}

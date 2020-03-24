package com.codersteam.alwin.core.api.model.operator;

import java.util.Objects;

/**
 * @author Michal Horowic
 */
public class OperatorTypeDto {

    private Long id;
    private String typeName;
    private String typeLabel;

    public OperatorTypeDto() {
    }

    public OperatorTypeDto(final Long id, final String typeName, final String typeLabel) {
        this.id = id;
        this.typeName = typeName;
        this.typeLabel = typeLabel;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(final String typeLabel) {
        this.typeLabel = typeLabel;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OperatorTypeDto that = (OperatorTypeDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(typeLabel, that.typeLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName, typeLabel);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OperatorTypeDto{");
        sb.append("id=").append(id);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", typeLabel='").append(typeLabel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

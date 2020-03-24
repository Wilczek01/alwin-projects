package com.codersteam.alwin.jpa.operator;

import com.codersteam.alwin.jpa.type.OperatorNameType;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Michal Horowic
 */
@Audited
@Entity
@Table(name = "operator_type")
public class OperatorType {

    @SequenceGenerator(name = "operatortypeSEQ", sequenceName = "operator_type_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operatortypeSEQ")
    private Long id;

    @Column(name = "type_name", length = 100, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private OperatorNameType typeName;

    @Column(name = "type_label", length = 100, nullable = false, unique = false)
    private String typeLabel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_operator_type_id", referencedColumnName = "id")
    private OperatorType parentOperatorType;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public OperatorNameType getTypeName() {
        return typeName;
    }

    public void setTypeName(final OperatorNameType typeName) {
        this.typeName = typeName;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(final String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public OperatorType getParentOperatorType() {
        return parentOperatorType;
    }

    public void setParentOperatorType(final OperatorType parentOperatorType) {
        this.parentOperatorType = parentOperatorType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OperatorType that = (OperatorType) o;
        return Objects.equals(id, that.id) &&
                typeName == that.typeName &&
                Objects.equals(typeLabel, that.typeLabel) &&
                Objects.equals(parentOperatorType, that.parentOperatorType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName, typeLabel, parentOperatorType);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OperatorType{");
        sb.append("id=").append(id);
        sb.append(", typeName=").append(typeName);
        sb.append(", typeLabel='").append(typeLabel).append('\'');
        sb.append(", parentOperatorType=").append(parentOperatorType);
        sb.append('}');
        return sb.toString();
    }
}

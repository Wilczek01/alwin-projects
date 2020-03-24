package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.operator.OperatorType;
import com.codersteam.alwin.common.issue.IssueTypeName;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

/**
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "issue_type")
public class IssueType {

    @SequenceGenerator(name = "issueTypeSEQ", sequenceName = "issue_type_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueTypeSEQ")
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private IssueTypeName name;

    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "operator_type_issue_type",
            joinColumns = @JoinColumn(name = "issue_type_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_type_id"))
    private Set<OperatorType> operatorTypes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public IssueTypeName getName() {
        return name;
    }

    public void setName(final IssueTypeName name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public Set<OperatorType> getOperatorTypes() {
        return operatorTypes;
    }

    public void setOperatorTypes(final Set<OperatorType> operatorTypes) {
        this.operatorTypes = operatorTypes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IssueType issueType = (IssueType) o;
        return Objects.equals(id, issueType.id) &&
                name == issueType.name &&
                Objects.equals(label, issueType.label) &&
                Objects.equals(operatorTypes, issueType.operatorTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, label, operatorTypes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IssueType{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", label='").append(label).append('\'');
        sb.append(", operatorTypes=").append(operatorTypes);
        sb.append('}');
        return sb.toString();
    }
}

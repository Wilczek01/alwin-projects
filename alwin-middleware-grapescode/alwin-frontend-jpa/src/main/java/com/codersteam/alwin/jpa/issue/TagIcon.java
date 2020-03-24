package com.codersteam.alwin.jpa.issue;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Dariusz Rackowski
 */
@Audited
@Entity
@Table(name = "tag_icon")
public class TagIcon {

    @SequenceGenerator(name = "tagIconSEQ", sequenceName = "tag_icon_id_seq", initialValue = 1, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagIconSEQ")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

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

    @Override
    public String toString() {
        return "TagIcon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TagIcon tagIcon = (TagIcon) o;

        if (id != null ? !id.equals(tagIcon.id) : tagIcon.id != null) return false;
        return name != null ? name.equals(tagIcon.name) : tagIcon.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}

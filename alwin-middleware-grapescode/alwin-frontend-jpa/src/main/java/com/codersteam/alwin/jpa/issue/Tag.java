package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.type.TagType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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

/**
 * @author Michal Horowic
 */
@Audited
@Entity
@Table(name = "tag")
public class Tag {

    @SequenceGenerator(name = "tagSEQ", sequenceName = "tag_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSEQ")
    private Long id;


    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "color", length = 7, nullable = false)
    private String color;

    @Column(name = "predefined", nullable = false)
    private boolean predefined;

    @Column(name = "tag_type", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private TagType type = TagType.CUSTOM;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_icon_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TagIcon tagIcon;

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

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public boolean isPredefined() {
        return predefined;
    }

    public void setPredefined(final boolean predefined) {
        this.predefined = predefined;
    }

    public TagType getType() {
        return type;
    }

    public void setType(final TagType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public TagIcon getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(final TagIcon tagIcon) {
        this.tagIcon = tagIcon;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", predefined=" + predefined +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", tagIcon=" + tagIcon +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Tag tag = (Tag) o;

        if (predefined != tag.predefined) return false;
        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        if (color != null ? !color.equals(tag.color) : tag.color != null) return false;
        if (type != tag.type) return false;
        if (description != null ? !description.equals(tag.description) : tag.description != null) return false;
        return tagIcon != null ? tagIcon.equals(tag.tagIcon) : tag.tagIcon == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (predefined ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tagIcon != null ? tagIcon.hashCode() : 0);
        return result;
    }
}



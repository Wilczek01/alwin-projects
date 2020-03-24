package com.codersteam.alwin.jpa.activity;

import com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Encja przechowująca opis typu cechy dodatkowej dla czynności zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Audited
@Entity
@Table(name = "activity_detail_property")
public class ActivityDetailProperty {

    @Id
    @SequenceGenerator(name = "activityDetailPropertySEQ", sequenceName = "activity_detail_property_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityDetailPropertySEQ")
    private Long id;

    @Column(name = "property_key", length = 100, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ActivityDetailPropertyKey key;

    @Column(name = "property_name", length = 100, nullable = false)
    private String name;

    @Column(name = "property_type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "activityDetailProperty")
    private Set<ActivityTypeDetailProperty> activityTypeDetailProperty = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activityDetailProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ActivityTypeDetailPropertyDictValue> dictionaryValues;

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

    public ActivityDetailPropertyKey getKey() {
        return key;
    }

    public void setKey(final ActivityDetailPropertyKey key) {
        this.key = key;
    }

    public Set<ActivityTypeDetailProperty> getActivityTypeDetailProperty() {
        return activityTypeDetailProperty;
    }

    public void setActivityTypeDetailProperty(final Set<ActivityTypeDetailProperty> activityTypeDetailProperty) {
        this.activityTypeDetailProperty = activityTypeDetailProperty;
    }

    public Set<ActivityTypeDetailPropertyDictValue> getDictionaryValues() {
        return dictionaryValues;
    }

    public void setDictionaryValues(final Set<ActivityTypeDetailPropertyDictValue> dictionaryValues) {
        this.dictionaryValues = dictionaryValues;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityDetailProperty)) return false;
        final ActivityDetailProperty that = (ActivityDetailProperty) o;
        return Objects.equals(id, that.id) &&
                key == that.key &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, name, type);
    }
}

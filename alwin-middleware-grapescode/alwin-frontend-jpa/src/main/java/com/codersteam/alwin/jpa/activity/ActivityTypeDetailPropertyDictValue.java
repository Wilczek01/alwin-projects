package com.codersteam.alwin.jpa.activity;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Encja przechowująca wartości słownikowe dla cechy dodatkowej czynności zlecenia
 *
 * @author Tomasz Sliwinski
 */
@Entity
@Audited
@Table(name = "activity_type_detail_property_dict_value", uniqueConstraints = {@UniqueConstraint(columnNames = {"activity_detail_property_id", "value"})})
public class ActivityTypeDetailPropertyDictValue {

    @Id
    @SequenceGenerator(name = "activityTypeDetailPropertyDictValueSEQ", sequenceName = "activity_type_detail_property_dict_value_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityTypeDetailPropertyDictValueSEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_detail_property_id")
    private ActivityDetailProperty activityDetailProperty;

    @Column(name = "value", length = 1000, nullable = false)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public ActivityDetailProperty getActivityDetailProperty() {
        return activityDetailProperty;
    }

    public void setActivityDetailProperty(final ActivityDetailProperty activityDetailProperty) {
        this.activityDetailProperty = activityDetailProperty;
    }

    @Override
    public String toString() {
        return "ActivityTypeDetailPropertyDictValue{" +
                "id=" + id +
                ", activityDetailProperty=" + activityDetailProperty +
                ", value='" + value + '\'' +
                '}';
    }
}

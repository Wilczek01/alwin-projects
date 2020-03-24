package com.codersteam.alwin.jpa.activity;

import com.codersteam.alwin.common.activity.ActivityTypeDetailPropertyType;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/**
 * Encja konfiguracji cech dodatkowych dla czynności zlecenia
 *
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "activity_type_has_detail_property")
public class ActivityTypeDetailProperty {

    @Id
    @SequenceGenerator(name = "ActivityTypeDetailPropertySEQ", sequenceName = "activity_type_has_detail_property_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ActivityTypeDetailPropertySEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "activity_detail_property_id")
    private ActivityDetailProperty activityDetailProperty;

    @Column(name = "state", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityTypeDetailPropertyType state;

    @Column(name = "required", length = 50, nullable = false)
    private Boolean required;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(final ActivityType activityType) {
        this.activityType = activityType;
    }

    public ActivityDetailProperty getActivityDetailProperty() {
        return activityDetailProperty;
    }

    public void setActivityDetailProperty(final ActivityDetailProperty activityDetailProperty) {
        this.activityDetailProperty = activityDetailProperty;
    }

    public ActivityTypeDetailPropertyType getState() {
        return state;
    }

    public void setState(final ActivityTypeDetailPropertyType state) {
        this.state = state;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityTypeDetailProperty that = (ActivityTypeDetailProperty) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(activityType, that.activityType) &&
                Objects.equals(activityDetailProperty, that.activityDetailProperty) &&
                state == that.state &&
                Objects.equals(required, that.required);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, activityType, activityDetailProperty, state, required);
    }

    @Override
    public String toString() {
        return "ActivityTypeDetailProperty{" +
                "id=" + id +
                ", activityType=" + activityType +
                ", activityDetailProperty=" + activityDetailProperty +
                ", state=" + state +
                ", required=" + required +
                '}';
    }
}

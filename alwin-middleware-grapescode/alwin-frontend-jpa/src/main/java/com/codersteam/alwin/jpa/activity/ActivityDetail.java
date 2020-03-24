package com.codersteam.alwin.jpa.activity;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Encja przechowująca dane cechy dodatkowej czynności
 *
 * @author Tomasz Sliwinski
 */
@Audited
@Entity
@Table(name = "activity_detail")
public class ActivityDetail {

    @Id
    @SequenceGenerator(name = "activityDetailSEQ", sequenceName = "activity_detail_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityDetailSEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_detail_property_id", referencedColumnName = "id", nullable = false)
    @NotAudited
    private ActivityDetailProperty detailProperty;

    @Column(name = "property_value", length = 1000)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ActivityDetailProperty getDetailProperty() {
        return detailProperty;
    }

    public void setDetailProperty(final ActivityDetailProperty detailProperty) {
        this.detailProperty = detailProperty;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}

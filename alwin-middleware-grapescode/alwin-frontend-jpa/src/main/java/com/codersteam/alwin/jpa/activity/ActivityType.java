package com.codersteam.alwin.jpa.activity;

import com.codersteam.alwin.jpa.issue.IssueTypeActivityType;
import com.codersteam.alwin.jpa.type.ActivityTypeKey;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Encja przechowująca typ czynności zlecenia
 */
@Audited
@Entity
@Table(name = "activity_type")
public class ActivityType {

    @Id
    @SequenceGenerator(name = "activityTypeSEQ", sequenceName = "activity_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityTypeSEQ")
    private Long id;

    @Column(name = "key", length = 100, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ActivityTypeKey key;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "can_be_planned", nullable = false)
    private Boolean canBePlanned;

    @Column(name = "may_be_automated", nullable = false)
    private Boolean mayBeAutomated;

    @Column(name = "specific", nullable = false)
    private Boolean specific;

    @Column(name = "may_have_declaration", nullable = false)
    private Boolean mayHaveDeclaration;

    @Column(name = "charge_min", nullable = false)
    private BigDecimal chargeMin;

    @Column(name = "charge_max")
    private BigDecimal chargeMax;

    @Column(name = "customer_contact", nullable = false)
    private boolean customerContact;

    @OneToMany(mappedBy = "activityType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ActivityTypeDetailProperty> activityTypeDetailProperties = new HashSet<>();

    @OneToMany(mappedBy = "activityType")
    private Set<IssueTypeActivityType> issueTypeActivityTypes = new HashSet<>();

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

    public Boolean getCanBePlanned() {
        return canBePlanned;
    }

    public void setCanBePlanned(final Boolean canBePlanned) {
        this.canBePlanned = canBePlanned;
    }

    public Boolean getMayBeAutomated() {
        return mayBeAutomated;
    }

    public void setMayBeAutomated(final Boolean mayBeAutomated) {
        this.mayBeAutomated = mayBeAutomated;
    }

    public Boolean getMayHaveDeclaration() {
        return mayHaveDeclaration;
    }

    public void setMayHaveDeclaration(final Boolean mayHaveDeclaration) {
        this.mayHaveDeclaration = mayHaveDeclaration;
    }

    public BigDecimal getChargeMin() {
        return chargeMin;
    }

    public void setChargeMin(final BigDecimal chargeMin) {
        this.chargeMin = chargeMin;
    }

    public BigDecimal getChargeMax() {
        return chargeMax;
    }

    public void setChargeMax(final BigDecimal chargeMax) {
        this.chargeMax = chargeMax;
    }

    public Set<ActivityTypeDetailProperty> getActivityTypeDetailProperties() {
        return activityTypeDetailProperties;
    }

    public void setActivityTypeDetailProperties(final Set<ActivityTypeDetailProperty> activityTypeDetailProperties) {
        this.activityTypeDetailProperties = activityTypeDetailProperties;
    }

    public Boolean getSpecific() {
        return specific;
    }

    public void setSpecific(final Boolean specific) {
        this.specific = specific;
    }

    public boolean isCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(final boolean customerContact) {
        this.customerContact = customerContact;
    }

    public ActivityTypeKey getKey() {
        return key;
    }

    public void setKey(final ActivityTypeKey key) {
        this.key = key;
    }

    public Set<IssueTypeActivityType> getIssueTypeActivityTypes() {
        return issueTypeActivityTypes;
    }

    public void setIssueTypeActivityTypes(final Set<IssueTypeActivityType> issueTypeActivityTypes) {
        this.issueTypeActivityTypes = issueTypeActivityTypes;
    }
}

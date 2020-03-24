package com.codersteam.alwin.jpa;

import com.codersteam.alwin.jpa.operator.Operator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Maska kodu pocztowego dla operatora
 *
 * @author Michal Horowic
 */
@Audited
@Entity
@Table(name = "postal_code")
public class PostalCode {

    @SequenceGenerator(name = "postalcodeSEQ", sequenceName = "postal_code_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postalcodeSEQ")
    private Long id;

    @Column(name = "mask", length = 6, nullable = false, unique = true)
    private String mask;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_id", referencedColumnName = "id")
    private Operator operator;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(final String mask) {
        this.mask = mask;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(final Operator operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "PostalCode{" +
                "id=" + id +
                ", mask='" + mask + '\'' +
                ", operator=" + operator +
                '}';
    }
}

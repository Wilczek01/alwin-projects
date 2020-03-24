package com.codersteam.alwin.jpa.termination;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Dokument wypowiedzenia umowy
 *
 * @author Dariusz Rackowski
 */
@Audited
@Entity
@Table(name = "contract_termination_attachment")
public class ContractTerminationAttachment {

    /**
     * Identyfikator dokumentu wypowiedzenia umowy
     */
    @SequenceGenerator(name = "contractTerminationAttachmentSEQ", sequenceName = "contract_termination_attachment_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractTerminationAttachmentSEQ")
    private Long id;

    /**
     * Referencja do dokumentu
     */
    @Column(name = "reference", nullable = false)
    private String reference;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }
}

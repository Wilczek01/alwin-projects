package com.codersteam.alwin.efaktura.model.termination;

import java.util.Date;
import java.util.List;

/**
 * @author Dariusz Rackowski
 */
public class ContractTerminationResponseDto {
    /**
     * Data utworzenia dokumentu
     */
    private Date creationDate;

    /**
     * Data ostatniej aktualizacji wpisu
     */
    private Date updateDate;

    /**
     * Pole zwraca eFV - zawiera listę wygenerowanych dokumentów
     */
    private List<String> documentHashes;

    /**
     * Pole zwraca eFV w przypadku błędu wykonania
     */
    private String error;

    /**
     * Data ew. wysłania dokumentu/ów
     */
    private Date sendDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getDocumentHashes() {
        return documentHashes;
    }

    public void setDocumentHashes(final List<String> documentHashes) {
        this.documentHashes = documentHashes;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(final Date sendDate) {
        this.sendDate = sendDate;
    }
}

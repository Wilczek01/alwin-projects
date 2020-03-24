package com.codersteam.alwin.core.api.model.demand;

import java.util.List;

/**
 * Klasa agregująca sugestie wezwań do zapłaty do wysłania i do usunięcia
 *
 * @author Michal Horowic
 */
public class ProcessDemandsForPaymentDto {
    private List<DemandForPaymentDto> demandsToReject;
    private List<DemandForPaymentDto> demandsToSend;
    private String rejectReason;

    public List<DemandForPaymentDto> getDemandsToReject() {
        return demandsToReject;
    }

    public void setDemandsToReject(final List<DemandForPaymentDto> demandsToReject) {
        this.demandsToReject = demandsToReject;
    }

    public List<DemandForPaymentDto> getDemandsToSend() {
        return demandsToSend;
    }

    public void setDemandsToSend(final List<DemandForPaymentDto> demandsToSend) {
        this.demandsToSend = demandsToSend;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(final String rejectReason) {
        this.rejectReason = rejectReason;
    }
}

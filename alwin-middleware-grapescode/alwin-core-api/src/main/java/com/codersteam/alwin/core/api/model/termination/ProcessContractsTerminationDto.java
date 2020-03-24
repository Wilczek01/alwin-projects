package com.codersteam.alwin.core.api.model.termination;

import java.util.List;

/**
 * @author Dariusz Rackowski
 */
public class ProcessContractsTerminationDto {

    private List<TerminationDto> terminationsToSend;
    private List<TerminationDto> terminationsToReject;
    private List<TerminationDto> terminationsToPostpone;

    public List<TerminationDto> getTerminationsToSend() {
        return terminationsToSend;
    }

    public void setTerminationsToSend(final List<TerminationDto> terminationsToSend) {
        this.terminationsToSend = terminationsToSend;
    }

    public List<TerminationDto> getTerminationsToReject() {
        return terminationsToReject;
    }

    public void setTerminationsToReject(final List<TerminationDto> terminationsToReject) {
        this.terminationsToReject = terminationsToReject;
    }

    public List<TerminationDto> getTerminationsToPostpone() {
        return terminationsToPostpone;
    }

    public void setTerminationsToPostpone(final List<TerminationDto> terminationsToPostpone) {
        this.terminationsToPostpone = terminationsToPostpone;
    }
}

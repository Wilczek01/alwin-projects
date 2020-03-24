package com.codersteam.alwin.model.issue;

/**
 * @author Piotr Naroznik
 */
public class TerminateIssueRequest {

    private String terminationCause;
    private boolean excludedFromStats;
    private String exclusionFromStatsCause;

    public String getTerminationCause() {
        return terminationCause;
    }

    public void setTerminationCause(final String terminationCause) {
        this.terminationCause = terminationCause;
    }

    public boolean isExcludedFromStats() {
        return excludedFromStats;
    }

    public void setExcludedFromStats(final boolean excludedFromStats) {
        this.excludedFromStats = excludedFromStats;
    }

    public String getExclusionFromStatsCause() {
        return exclusionFromStatsCause;
    }

    public void setExclusionFromStatsCause(final String exclusionFromStatsCause) {
        this.exclusionFromStatsCause = exclusionFromStatsCause;
    }
}

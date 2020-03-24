package com.codersteam.alwin.core.api.model.issue;

/**
 * Portfel zleceń liczony po typie zlecenia oraz segmencie klienta.
 * Portfele są liczone tylko dla podanych w dao statusów zleceń.
 * Dlatego ten sam typ zlecenia i segment klienta może mieć więcej portfeli.
 *
 * @author Piotr Naroznik
 */
public class IssueWalletDto extends WalletDto {

    /**
     * Typ zlecenia
     */
    private IssueTypeDto issueType;

    /**
     * Segment klienta
     */
    private SegmentDto segment;

    /**
     * Czas trwania zleceń w portfelu
     */
    private String duration;

    public IssueTypeDto getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueTypeDto issueType) {
        this.issueType = issueType;
    }

    public SegmentDto getSegment() {
        return segment;
    }

    public void setSegment(final SegmentDto segment) {
        this.segment = segment;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final IssueWalletDto that = (IssueWalletDto) o;

        if (getIssueType() != null ? !getIssueType().equals(that.getIssueType()) : that.getIssueType() != null) return false;
        if (getSegment() != null ? !getSegment().equals(that.getSegment()) : that.getSegment() != null) return false;
        return getDuration() != null ? getDuration().equals(that.getDuration()) : that.getDuration() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getIssueType() != null ? getIssueType().hashCode() : 0);
        result = 31 * result + (getSegment() != null ? getSegment().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueWalletDto{" +
                "issueType=" + issueType +
                ", segment=" + segment +
                ", duration='" + duration + '\'' +
                "} " + super.toString();
    }
}

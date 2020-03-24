package com.codersteam.alwin.core.api.model.issue;

/**
 * Portfel zleceń liczony dla zleceń o podanym statusie zlecenia
 *
 * @author Piotr Naroznik
 */
public class IssueStateWalletDto extends WalletDto {

    /**
     * Status zlecenia
     */
    private IssueStateDto issueState;

    public IssueStateDto getIssueState() {
        return issueState;
    }

    public void setIssueState(final IssueStateDto issueState) {
        this.issueState = issueState;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final IssueStateWalletDto that = (IssueStateWalletDto) o;

        return getIssueState() != null ? getIssueState().equals(that.getIssueState()) : that.getIssueState() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getIssueState() != null ? getIssueState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueStateWalletDto{" +
                "issueState=" + issueState +
                "} " + super.toString();
    }
}
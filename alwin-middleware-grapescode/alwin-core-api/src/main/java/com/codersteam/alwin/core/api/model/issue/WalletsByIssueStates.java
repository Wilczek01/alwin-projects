package com.codersteam.alwin.core.api.model.issue;

import java.util.List;

/**
 * Portfele dla statusów zlecenia
 *
 * @author Piotr Naroznik
 */
public class WalletsByIssueStates {

    /**
     * Lista statusów dla których pobrane są portfele
     */
    private final List<IssueStateDto> issueStates;
    /**
     * Lista portfeli dla statusów
     */
    private final List<IssueWalletDto> wallets;

    public WalletsByIssueStates(final List<IssueStateDto> issueStates, final List<IssueWalletDto> wallets) {
        this.issueStates = issueStates;
        this.wallets = wallets;
    }

    public List<IssueStateDto> getIssueStates() {
        return issueStates;
    }

    public List<IssueWalletDto> getWallets() {
        return wallets;
    }
}
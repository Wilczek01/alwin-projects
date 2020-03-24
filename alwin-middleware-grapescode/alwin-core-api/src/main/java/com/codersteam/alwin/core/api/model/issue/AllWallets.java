package com.codersteam.alwin.core.api.model.issue;

import java.util.List;

/**
 * Wszystkie portfele prezentowane na stronie segmentów
 *
 * @author Piotr Naroznik
 */
public class AllWallets {

    /**
     * Portfele dla otwartych zleceń windykacyjnych
     */
    private final WalletsByIssueStates walletsByIssueStates;

    /**
     * Portfel dla zleceń czekających na zamknięcie
     */
    private final IssueStateWalletDto waitingForTerminationIssuesWallet;

    /**
     * Portfele dla etykiet
     */
    private final List<TagWalletDto> tagWallets;

    public AllWallets(final WalletsByIssueStates walletsByIssueStates, final IssueStateWalletDto waitingForTerminationIssuesWallet,
                      final List<TagWalletDto> tagWallets) {
        this.walletsByIssueStates = walletsByIssueStates;
        this.waitingForTerminationIssuesWallet = waitingForTerminationIssuesWallet;
        this.tagWallets = tagWallets;
    }

    public WalletsByIssueStates getWalletsByIssueStates() {
        return walletsByIssueStates;
    }

    public WalletDto getWaitingForTerminationIssuesWallet() {
        return waitingForTerminationIssuesWallet;
    }

    public List<TagWalletDto> getTagWallets() {
        return tagWallets;
    }

    @Override
    public String toString() {
        return "AllWallets{" +
                "walletsByIssueStates=" + walletsByIssueStates +
                ", waitingForTerminationIssuesWallet=" + waitingForTerminationIssuesWallet +
                ", tagWallets=" + tagWallets +
                '}';
    }
}
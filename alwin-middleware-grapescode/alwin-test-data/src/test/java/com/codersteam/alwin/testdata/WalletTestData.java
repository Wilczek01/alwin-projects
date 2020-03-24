package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.jpa.IssueWallet;
import com.codersteam.alwin.jpa.TagWallet;
import com.codersteam.alwin.jpa.Wallet;

import java.math.BigDecimal;
import java.util.List;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.IN_PROGRESS;
import static com.codersteam.alwin.core.api.model.issue.IssueStateDto.NEW;
import static com.codersteam.alwin.testdata.IssueTypeTestData.*;
import static com.codersteam.alwin.testdata.TagTestData.*;
import static java.util.Arrays.asList;

public class WalletTestData {

    public static final long CURRENT_ISSUE_QUEUE_COUNT_0 = 0L;
    public static final long CURRENT_ISSUE_QUEUE_COUNT_1 = 3L;
    public static final long CURRENT_ISSUE_QUEUE_COUNT_2 = 2L;
    private static final long COMPANY_COUNT_0 = 0L;
    private static final long COMPANY_COUNT_1 = 5L;
    private static final BigDecimal INVOLVEMENT_1 = new BigDecimal("12345.67");
    private static final BigDecimal INVOLVEMENT_2 = new BigDecimal("23456.78");
    private static final String DURATION_1 = DPD_START_1A + "-" + (DPD_START_1A + DURATION_1A);

    private static final BigDecimal BALANCE_START_PLN_1 = new BigDecimal("-444642.67");
    private static final BigDecimal BALANCE_START_PLN_2 = new BigDecimal("65432.44");

    private static final BigDecimal BALANCE_START_EUR_2 = new BigDecimal("432.23");

    private static final String DURATION_2 = DPD_START_1B + "-" + (DPD_START_1B + DURATION_1B);

    private static final String DURATION_3 = DPD_START_2A + "-" + (DPD_START_2A + DURATION_2A);
    private static final String DURATION_4 = DPD_START_2B + "-" + (DPD_START_2B + DURATION_2B);

    public static Wallet emptyWallet() {
        return new Wallet(0L, null, null, null);
    }

    public static Wallet wallet1() {
        final Wallet wallet = new Wallet(COMPANY_COUNT_1, INVOLVEMENT_1, BALANCE_START_PLN_1, ZERO);
        wallet.setCurrentIssueQueueCount(CURRENT_ISSUE_QUEUE_COUNT_1);
        return wallet;
    }

    public static Wallet walletForIssueStateNew() {
        return new Wallet(7L, new BigDecimal("12345.67"), new BigDecimal("664642.67"), ZERO);
    }

    public static IssueWallet issueWallet1() {
        return new IssueWallet(COMPANY_COUNT_1, INVOLVEMENT_1, BALANCE_START_PLN_1, ZERO);
    }

    public static IssueWallet issueWallet2() {
        return new IssueWallet(COMPANY_COUNT_1, INVOLVEMENT_2, BALANCE_START_PLN_2, BALANCE_START_EUR_2);
    }

    public static TagWallet tagWallet1() {
        return new TagWallet(2L, INVOLVEMENT_1, ZERO, ZERO, testTag1());
    }

    public static TagWallet tagWallet2() {
        return new TagWallet(1L, INVOLVEMENT_1, new BigDecimal("234642.67"), ZERO, testTag2());
    }

    public static IssueWallet issueWalletToMap() {
        final IssueWallet wallet = issueWallet1();
        wallet.setDuration(DURATION_1);
        wallet.setIssueType(issueType1());
        wallet.setSegment(Segment.A);
        return wallet;
    }

    private static IssueWalletDto issueWalletDto1() {
        return issueWalletDto(issueTypeDto1(), SegmentDto.A, COMPANY_COUNT_1, INVOLVEMENT_1, BALANCE_START_PLN_1, ZERO, CURRENT_ISSUE_QUEUE_COUNT_1);
    }

    public static TagWalletDto tagWalletDto1() {
        return tagWalletDto(2L, INVOLVEMENT_1, ZERO, ZERO, CURRENT_ISSUE_QUEUE_COUNT_1, testTagDto1());
    }


    public static IssueStateWalletDto issueStateWalletDto1() {
        return issueStateWalletDto(COMPANY_COUNT_1, INVOLVEMENT_1, BALANCE_START_PLN_1, ZERO, CURRENT_ISSUE_QUEUE_COUNT_1, null);
    }

    private static IssueWalletDto issueWalletDto2() {
        return issueWalletDto(issueTypeDto1(), SegmentDto.B, COMPANY_COUNT_1, INVOLVEMENT_2, BALANCE_START_PLN_2, BALANCE_START_EUR_2, CURRENT_ISSUE_QUEUE_COUNT_2);
    }

    private static IssueWalletDto issueWalletDto3() {
        return issueWalletDto(issueTypeDto2(), SegmentDto.A, COMPANY_COUNT_0, ZERO, ZERO, ZERO, CURRENT_ISSUE_QUEUE_COUNT_0);
    }

    private static IssueWalletDto issueWalletDto4() {
        return issueWalletDto(issueTypeDto2(), SegmentDto.B, COMPANY_COUNT_0, ZERO, ZERO, ZERO, CURRENT_ISSUE_QUEUE_COUNT_0);
    }

    public static WalletsByIssueStates walletsByIssueStates1() {
        return new WalletsByIssueStates(asList(NEW, IN_PROGRESS), walletWithDurationDtos());
    }

    public static List<IssueWalletDto> walletWithDurationDtos() {
        return asList(walletWithDurationDto1(), walletWithDurationDto3(), walletWithDurationDto2(), walletWithDurationDto4());
    }

    public static IssueWalletDto walletWithDurationDto1() {
        final IssueWalletDto walletDto = issueWalletDto1();
        walletDto.setDuration(DURATION_1);
        return walletDto;
    }

    public static IssueWalletDto walletWithDurationDto2() {
        final IssueWalletDto walletDto = issueWalletDto2();
        walletDto.setDuration(DURATION_2);
        return walletDto;
    }

    public static IssueWalletDto walletWithDurationDto3() {
        final IssueWalletDto walletDto = issueWalletDto3();
        walletDto.setDuration(DURATION_3);
        return walletDto;
    }

    public static IssueWalletDto walletWithDurationDto4() {
        final IssueWalletDto walletDto = issueWalletDto4();
        walletDto.setDuration(DURATION_4);
        return walletDto;
    }

    private static IssueWalletDto issueWalletDto(final IssueTypeDto issueType, final SegmentDto segment, final Long companyCount, final BigDecimal involvement,
                                                 final BigDecimal balanceStartPLN, final BigDecimal balanceStartEUR, final Long currentIssueQueueCount) {
        final IssueWalletDto walletDto = new IssueWalletDto();
        walletDto.setIssueType(issueType);
        walletDto.setSegment(segment);
        walletDto.setCompanyCount(companyCount);
        walletDto.setInvolvement(involvement);
        walletDto.setCurrentBalancePLN(balanceStartPLN);
        walletDto.setCurrentBalanceEUR(balanceStartEUR);
        walletDto.setCurrentIssueQueueCount(currentIssueQueueCount);
        return walletDto;
    }

    private static TagWalletDto tagWalletDto(final Long companyCount, final BigDecimal involvement, final BigDecimal balanceStartPLN,
                                             final BigDecimal balanceStartEUR, final Long currentIssueQueueCount, final TagDto tag) {
        final TagWalletDto walletDto = new TagWalletDto();
        walletDto.setCompanyCount(companyCount);
        walletDto.setInvolvement(involvement);
        walletDto.setCurrentBalancePLN(balanceStartPLN);
        walletDto.setCurrentBalanceEUR(balanceStartEUR);
        walletDto.setCurrentIssueQueueCount(currentIssueQueueCount);
        walletDto.setTag(tag);
        return walletDto;
    }

    private static IssueStateWalletDto issueStateWalletDto(final Long companyCount, final BigDecimal involvement, final BigDecimal balanceStartPLN,
                                                           final BigDecimal balanceStartEUR, final Long currentIssueQueueCount, final IssueStateDto issueState) {
        final IssueStateWalletDto walletDto = new IssueStateWalletDto();
        walletDto.setIssueState(issueState);
        walletDto.setCompanyCount(companyCount);
        walletDto.setInvolvement(involvement);
        walletDto.setCurrentBalancePLN(balanceStartPLN);
        walletDto.setCurrentBalanceEUR(balanceStartEUR);
        walletDto.setCurrentIssueQueueCount(currentIssueQueueCount);
        return walletDto;
    }
}
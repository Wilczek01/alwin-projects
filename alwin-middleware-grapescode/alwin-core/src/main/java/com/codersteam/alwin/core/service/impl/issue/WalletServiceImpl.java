package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.WalletService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao;
import com.codersteam.alwin.db.dao.IssueTypeDao;
import com.codersteam.alwin.jpa.IssueWallet;
import com.codersteam.alwin.jpa.TagWallet;
import com.codersteam.alwin.jpa.Wallet;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.type.IssueState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_1;
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_2;
import static com.codersteam.alwin.common.issue.Segment.A;
import static com.codersteam.alwin.common.issue.Segment.B;
import static java.lang.String.format;
import static java.util.Arrays.asList;

@Stateless
public class WalletServiceImpl implements WalletService {

    private static final long COMPANY_COUNT_0 = 0L;

    private IssueDao issueDao;
    private IssueTypeDao issueTypeDao;
    private IssueTypeConfigurationDao issueTypeConfigurationDao;
    private DateProvider dateProvider;
    private AlwinMapper alwinMapper;

    public WalletServiceImpl() {
    }

    @Inject
    public WalletServiceImpl(final IssueDao issueDao, final IssueTypeDao issueTypeDao, final IssueTypeConfigurationDao issueTypeConfigurationDao,
                             final DateProvider dateProvider, final AlwinMapper alwinMapper) {
        this.issueDao = issueDao;
        this.issueTypeDao = issueTypeDao;
        this.issueTypeConfigurationDao = issueTypeConfigurationDao;
        this.dateProvider = dateProvider;
        this.alwinMapper = alwinMapper;
    }

    public WalletsByIssueStates findIssueWallets() {
        final IssueType phoneDebtCollection1Type = issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_1);
        final IssueType phoneDebtCollection2Type = issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_2);

        final Integer phoneDebtCollection1DurationA = findDuration(PHONE_DEBT_COLLECTION_1, A);
        final Integer phoneDebtCollection1DurationB = findDuration(PHONE_DEBT_COLLECTION_1, B);
        final Integer phoneDebtCollection2DurationA = findDuration(PHONE_DEBT_COLLECTION_2, A);
        final Integer phoneDebtCollection2DurationB = findDuration(PHONE_DEBT_COLLECTION_2, B);

        final Integer phoneDebtCollection1StartA = findDpdStart(PHONE_DEBT_COLLECTION_1, A);
        final Integer phoneDebtCollection1StartB = findDpdStart(PHONE_DEBT_COLLECTION_1, B);
        final Integer phoneDebtCollection2StartA = findDpdStart(PHONE_DEBT_COLLECTION_2, A);
        final Integer phoneDebtCollection2StartB = findDpdStart(PHONE_DEBT_COLLECTION_2, B);

        return findIssueWallets(phoneDebtCollection1Type, phoneDebtCollection2Type,
                phoneDebtCollection1StartA, phoneDebtCollection1DurationA,
                phoneDebtCollection2StartA, phoneDebtCollection2DurationA,
                phoneDebtCollection1StartB, phoneDebtCollection1DurationB,
                phoneDebtCollection2StartB, phoneDebtCollection2DurationB
        );
    }

    private List<TagWalletDto> findTagWallets() {
        final List<TagWallet> tagWallets = issueDao.findTagWallets();
        tagWallets.forEach(this::setCurrentIssueQueueCount);
        return alwinMapper.mapAsList(tagWallets, TagWalletDto.class);
    }

    @Override
    public AllWallets findAllWallets() {
        final WalletsByIssueStates issueWallets = findIssueWallets();
        final List<TagWalletDto> tagWallets = findTagWallets();
        final IssueStateWalletDto waitingForTerminationIssuesWallet = findWalletByIssueState();
        return new AllWallets(issueWallets, waitingForTerminationIssuesWallet, tagWallets);
    }

    private IssueStateWalletDto findWalletByIssueState() {
        final Wallet walletByIssueState = issueDao.findWalletByIssueState(IssueState.WAITING_FOR_TERMINATION);
        setCurrentIssueStateQueueCount(walletByIssueState);
        final IssueStateWalletDto walletDto = alwinMapper.map(walletByIssueState, IssueStateWalletDto.class);
        walletDto.setIssueState(alwinMapper.map(IssueState.WAITING_FOR_TERMINATION, IssueStateDto.class));
        return walletDto;
    }

    private void setCurrentIssueStateQueueCount(final Wallet walletByIssueState) {
        Date currentDate = dateProvider.getCurrentDateStartOfDay();
        Long currentIssueQueueCount = issueDao.findCurrentIssueQueueCountByIssueState(IssueState.WAITING_FOR_TERMINATION, currentDate);
        walletByIssueState.setCurrentIssueQueueCount(currentIssueQueueCount);
    }

    private void setCurrentIssueQueueCount(final TagWallet tagWallet) {
        final Date currentDate = dateProvider.getCurrentDateStartOfDay();
        final Long currentIssueQueueCountByTag = issueDao.findCurrentIssueQueueCountByTag(tagWallet.getTag().getId(), currentDate);
        tagWallet.setCurrentIssueQueueCount(currentIssueQueueCountByTag);
    }

    private WalletsByIssueStates findIssueWallets(final IssueType phoneDebtCollection1Type, final IssueType phoneDebtCollection2Type,
                                                  final Integer phoneDebtCollection1StartA, final Integer phoneDebtCollection1DurationA,
                                                  final Integer phoneDebtCollection2StartA, final Integer phoneDebtCollection2DurationA,
                                                  final Integer phoneDebtCollection1StartB, final Integer phoneDebtCollection1DurationB,
                                                  final Integer phoneDebtCollection2StartB, final Integer phoneDebtCollection2DurationB) {
        final IssueWallet wallet1 = findIssueWallet(A, phoneDebtCollection1Type, phoneDebtCollection1StartA, phoneDebtCollection1DurationA);
        final IssueWallet wallet2 = findIssueWallet(A, phoneDebtCollection2Type, phoneDebtCollection2StartA, phoneDebtCollection2DurationA);
        final IssueWallet wallet3 = findIssueWallet(B, phoneDebtCollection1Type, phoneDebtCollection1StartB, phoneDebtCollection1DurationB);
        final IssueWallet wallet4 = findIssueWallet(B, phoneDebtCollection2Type, phoneDebtCollection2StartB, phoneDebtCollection2DurationB);
        final List<IssueWalletDto> wallets = alwinMapper.mapAsList(asList(wallet1, wallet2, wallet3, wallet4), IssueWalletDto.class);
        return new WalletsByIssueStates(alwinMapper.mapAsList(IssueState.NEW_AND_IN_PROGRESS_ISSUE_STATES, IssueStateDto.class), wallets);
    }

    private IssueWallet findIssueWallet(final Segment segment, final IssueType issueType, final int startDuration, final Integer duration) {
        final IssueTypeName issueTypeName = issueType.getName();
        final IssueWallet wallet = issueDao.findWalletsByIssueType(issueTypeName, segment, IssueState.NEW_AND_IN_PROGRESS_ISSUE_STATES).orElse(createEmptyIssueWallet());
        wallet.setIssueType(issueType);
        wallet.setSegment(segment);
        wallet.setDuration(formatDuration(startDuration, startDuration + duration));
        final Long currentIssueQueueCount = issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(issueTypeName, segment, dateProvider.getCurrentDateStartOfDay(), IssueState.NEW_AND_IN_PROGRESS_ISSUE_STATES);
        wallet.setCurrentIssueQueueCount(currentIssueQueueCount);
        return wallet;
    }

    private IssueWallet createEmptyIssueWallet() {
        return new IssueWallet(COMPANY_COUNT_0, ZERO, ZERO, ZERO);
    }

    private String formatDuration(final Integer startDuration, final Integer endDuration) {
        return format("%d-%d", (startDuration == 0 ? 1 : startDuration), endDuration);
    }

    private Integer findDuration(final IssueTypeName issueTypeName, final Segment segment) {
        return issueTypeConfigurationDao.findDurationByTypeAndSegment(issueTypeName, segment);
    }

    private Integer findDpdStart(final IssueTypeName issueTypeName, final Segment segment) {
        return issueTypeConfigurationDao.findDpdStartByTypeAndSegment(issueTypeName, segment);
    }
}
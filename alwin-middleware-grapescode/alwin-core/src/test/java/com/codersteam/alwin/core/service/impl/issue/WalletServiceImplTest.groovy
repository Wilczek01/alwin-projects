package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao
import com.codersteam.alwin.db.dao.IssueTypeDao
import com.codersteam.alwin.testdata.TestDateUtils
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_1
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION_2
import static com.codersteam.alwin.common.issue.Segment.A
import static com.codersteam.alwin.common.issue.Segment.B
import static com.codersteam.alwin.jpa.type.IssueState.NEW_AND_IN_PROGRESS_ISSUE_STATES
import static com.codersteam.alwin.testdata.IssueTypeTestData.*
import static com.codersteam.alwin.testdata.WalletTestData.*
import static org.assertj.core.api.Assertions.assertThat

class WalletServiceImplTest extends Specification {

    @Subject
    private WalletServiceImpl service

    private IssueDao issueDao = Mock(IssueDao)
    private IssueTypeDao issueTypeDao = Mock(IssueTypeDao)
    private IssueTypeConfigurationDao issueTypeConfigurationDao = Mock(IssueTypeConfigurationDao)
    private AlwinMapper alwinMapper = new AlwinMapper()
    private DateProvider dateProvider = Mock(DateProvider)

    def "setup"() {
        service = new WalletServiceImpl(issueDao, issueTypeDao, issueTypeConfigurationDao, dateProvider, alwinMapper)
    }

    def "should find wallets"() {
        given:
            issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_1) >> issueType1()
            issueTypeDao.findIssueTypeByName(PHONE_DEBT_COLLECTION_2) >> issueType2()
        and:
            issueDao.findWalletsByIssueType(PHONE_DEBT_COLLECTION_1, A, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> Optional.of(issueWallet1())
            issueDao.findWalletsByIssueType(PHONE_DEBT_COLLECTION_2, A, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> Optional.empty()
            issueDao.findWalletsByIssueType(PHONE_DEBT_COLLECTION_1, B, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> Optional.of(issueWallet2())
            issueDao.findWalletsByIssueType(PHONE_DEBT_COLLECTION_2, B, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> Optional.empty()
        and:
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, A) >> DURATION_1A
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_2, A) >> DURATION_2A
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_1, B) >> DURATION_1B
            issueTypeConfigurationDao.findDurationByTypeAndSegment(PHONE_DEBT_COLLECTION_2, B) >> DURATION_2B
        and:
            issueTypeConfigurationDao.findDpdStartByTypeAndSegment(PHONE_DEBT_COLLECTION_1, A) >> DPD_START_1A
            issueTypeConfigurationDao.findDpdStartByTypeAndSegment(PHONE_DEBT_COLLECTION_2, A) >> DPD_START_2A
            issueTypeConfigurationDao.findDpdStartByTypeAndSegment(PHONE_DEBT_COLLECTION_1, B) >> DPD_START_1B
            issueTypeConfigurationDao.findDpdStartByTypeAndSegment(PHONE_DEBT_COLLECTION_2, B) >> DPD_START_2B
        and:
            def currentDate = TestDateUtils.parse("2018-10-01")
            dateProvider.getCurrentDateStartOfDay() >> currentDate
        and:
            issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(PHONE_DEBT_COLLECTION_1, A, currentDate, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> CURRENT_ISSUE_QUEUE_COUNT_1
            issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(PHONE_DEBT_COLLECTION_2, A, currentDate, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> CURRENT_ISSUE_QUEUE_COUNT_0
            issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(PHONE_DEBT_COLLECTION_1, B, currentDate, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> CURRENT_ISSUE_QUEUE_COUNT_2
            issueDao.findCurrentIssueQueueCountByIssueTypeAndSegment(PHONE_DEBT_COLLECTION_2, B, currentDate, NEW_AND_IN_PROGRESS_ISSUE_STATES) >> CURRENT_ISSUE_QUEUE_COUNT_0
        when:
            def wallets = service.findIssueWallets()
        then:
            assertThat(wallets).isEqualToComparingFieldByFieldRecursively(walletsByIssueStates1())
    }
}
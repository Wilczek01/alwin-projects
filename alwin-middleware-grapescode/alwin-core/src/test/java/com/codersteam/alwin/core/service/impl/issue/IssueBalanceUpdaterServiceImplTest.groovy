package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.service.CurrencyExchangeRateService
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService
import com.codersteam.alwin.core.api.service.issue.InvoiceBalanceUpdaterService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.jpa.issue.Issue
import com.codersteam.alwin.jpa.type.IssueState
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.util.concurrent.ThreadLocalRandom

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.BalanceTestData.*
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.*
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID
import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class IssueBalanceUpdaterServiceImplTest extends Specification {

    @Subject
    private IssueBalanceUpdaterServiceImpl issueBalanceUpdater

    private IssueDao issueDao = Mock(IssueDao)
    private ActivityService activityService = Mock(ActivityService)
    private DateProvider dateProvider = Mock(DateProvider)
    private InvoiceBalanceUpdaterService invoiceBalanceUpdaterService = Mock(InvoiceBalanceUpdaterService)
    private AidaService aidaService = Mock(AidaService)
    private CurrencyExchangeRateService currencyExchangeRateService = Mock(CurrencyExchangeRateService)
    private BalanceCalculatorService balanceCalculatorService = Mock(BalanceCalculatorService)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper alwinMapper = new AlwinMapper(dateProvider, alwinProperties)

    def "setup"() {
        aidaService.getCurrencyExchangeRateService() >> currencyExchangeRateService
        issueBalanceUpdater = new IssueBalanceUpdaterServiceImpl(issueDao, activityService, dateProvider, invoiceBalanceUpdaterService, aidaService,
                balanceCalculatorService, alwinMapper)
    }

    def "should have default constructor for EJB"() {
        when:
            def service = new IssueBalanceUpdaterServiceImpl()
        then:
            service
    }

    def "should update issue balances without closing issue (negative balance)"() {
        given:
            def balanceUpdateDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> balanceUpdateDate
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(balanceUpdateDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.issueState == IssueState.NEW
                assert issue.balanceUpdateDate == balanceUpdateDate
            }
            0 * activityService.closeIssueActivities(issue2().getId())
    }

    def "should update issue balances without closing issue (negative eur balance)"() {
        given:
            def balanceUpdateDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> balanceUpdateDate
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(balanceUpdateDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> balanceWithNegativeBalanceEURThatIssueShouldBeProceed()
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.issueState == IssueState.NEW
                assert issue.balanceUpdateDate == balanceUpdateDate
            }
            0 * activityService.closeIssueActivities(issue2().getId())
    }

    def "should update issue balances with closing issue (positive balance)"() {
        given:
            def balanceUpdateDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> balanceUpdateDate
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(balanceUpdateDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> positiveBalance()
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.issueState == IssueState.DONE
                assert issue.balanceUpdateDate == balanceUpdateDate
            }
            1 * activityService.closeIssueActivities(issue2().getId())
    }

    def "should update issue balances without closing manually created issue (positive balance)"() {
        given:
            def balanceUpdateDate = parse("2019-07-10 00:16:00.000000")
            dateProvider.currentDate >> balanceUpdateDate
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and:
            def issueId = manuallyCreatedIssue().getId()
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(balanceUpdateDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(issueId) >> positiveBalance()
        and:
            issueDao.get(issueId) >> Optional.of(manuallyCreatedIssue())
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(issueId)
        then:
            1 * issueDao.update(_ as Issue) >> { List arguments ->
                Issue issue = arguments[0] as Issue
                assert issue.issueState == IssueState.IN_PROGRESS
                assert issue.balanceUpdateDate == balanceUpdateDate
            }
            0 * activityService.closeIssueActivities(issue2().getId())
    }

    def "should throw exception if issue not exists"() {
        given:
            issueDao.get(NOT_EXISTING_ISSUE_ID) >> Optional.empty()
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(NOT_EXISTING_ISSUE_ID)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NOT_EXISTING_ISSUE_ID
    }

    def "should update issue balance"() {
        given:
            def issue = issue1()
        and:
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue)
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_1) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            def currentDate = parse("2017-10-10")
            dateProvider.getCurrentDate() >> currentDate
            1 * currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_1)
        then:
            1 * issueDao.update(_ as Issue)
        and:
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_1)
    }

    def "should update issue balance if last update date is too old"() {
        given:
            def currentDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> currentDate
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and: 'issue balance update date more than 15 minutes old'
            def issueBalanceUpdateDate = parse("2018-07-10 00:00:00.000000")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            def issue = issue2()
            issue.balanceUpdateDate = issueBalanceUpdateDate
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue)
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_2)
            1 * issueDao.update(_ as Issue)
    }

    def "should update issue balance if last update date is null"() {
        given:
            def currentDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> currentDate
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-07-10")
        and: 'issue balance update date not set'
            def issueBalanceUpdateDate = null
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            def issue = issue2()
            issue.balanceUpdateDate = issueBalanceUpdateDate
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue)
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_2)
            1 * issueDao.update(_ as Issue)
    }

    def "should not update issue balance if last update date is within range of 15 minutes"() {
        given:
            def currentDate = parse("2018-07-10 00:15:00.000000")
            dateProvider.currentDate >> currentDate
            dateProvider.currentDateStartOfDay >> parse("2018-07-10")
        and: 'issue balance update date more than 15 minutes old'
            def issueBalanceUpdateDate = parse("2018-07-10 00:00:00.000000")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            def issue = issue2()
            issue.balanceUpdateDate = issueBalanceUpdateDate
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue)
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            0 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_2)
            0 * issueDao.update(_ as Issue)
    }

    @Ignore("TODO: Rozwiązać niedeterministyczne zachowanie")
    def "should update given issue balance only once for multiple parallel requests"() {
        given:
            def currentDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> currentDate
        and: 'issue balance update date more than 15 minutes old'
            def issueBalanceUpdateDate = parse("2018-07-10 00:00:00.000000")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            def issue = issue2()
            issue.balanceUpdateDate = issueBalanceUpdateDate
        and:
            issueDao.get(ISSUE_ID_2) >> {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10))
                Optional.of(issue)
            }
        when: 'multiple update issue balance requests for the same issue'
            IssueUpdateExecutor.runParallelBalanceUpdateForIssue(issueBalanceUpdater, ISSUE_ID_2, 10)
        then: 'issue balance updated only once'
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_2)
            1 * issueDao.update(_ as Issue)
    }

    def "should remove issue id from currently balance updating issues when error occurs"() {
        given:
            def currentDate = parse("2018-07-10 00:16:00.000000")
            dateProvider.currentDate >> currentDate
            dateProvider.currentDateStartOfDay >> parse("2018-07-10")
        and: 'issue balance update date more than 15 minutes old'
            def issueBalanceUpdateDate = parse("2018-07-10 00:00:00.000000")
        and:
            currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> {
                throw new RuntimeException("Not so fast young man!")
            }
        and:
            def issue = issue2()
            issue.balanceUpdateDate = issueBalanceUpdateDate
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue)
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(ISSUE_ID_2)
        then:
            thrown(RuntimeException)
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_2)
            0 * issueDao.update(_ as Issue)
            !IssueBalanceUpdaterServiceImpl.CURRENTLY_UPDATING_ISSUE_IDS.contains(ISSUE_ID_2)
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    @Unroll
    def "should not update balance for #issueState issue"() {
        given:
            dateProvider.currentDateStartOfDay >> parse("2018-07-10")
            dateProvider.currentDate >> parse("2018-07-10 12:00:00.000000")
        and: 'balance update date old enough to trigger balance update'
            def issueBalanceUpdateDate = parse("2018-07-10 00:00:00.000000")
        and:
            def issueInClosedState = issue
            issueInClosedState.balanceUpdateDate = issueBalanceUpdateDate
            issueDao.get(issueId) >> Optional.of(issueInClosedState)
        when:
            issueBalanceUpdater.getIssueWithUpdatedBalance(issueId)
        then:
            issueInClosedState.issueState == issueState
        and: 'no updates performed'
            0 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(_ as Long)
            0 * issueDao.update(_ as Issue)
        where:
            issueId     | issue     | issueState
            ISSUE_ID_19 | issue19() | IssueState.DONE
            ISSUE_ID_20 | issue20() | IssueState.CANCELED
    }
}

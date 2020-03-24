package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.service.CurrencyExchangeRateService
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService
import com.codersteam.alwin.core.api.service.issue.InvoiceBalanceUpdaterService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.jpa.issue.Issue
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.BalanceTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class ActiveIssuesUpdaterServiceImplTest extends Specification {

    @Subject
    private ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdater

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
        def issueBalanceUpdater = new IssueBalanceUpdaterServiceImpl(issueDao, activityService, dateProvider, invoiceBalanceUpdaterService, aidaService,
                balanceCalculatorService, alwinMapper)
        activeIssuesBalanceUpdater = new ActiveIssuesBalanceUpdaterService(issueDao, issueBalanceUpdater)
    }

    def "should have default constructor for EJB"() {
        when:
            def service = new IssueBalanceUpdaterServiceImpl()
        then:
            service
    }

    @Ignore
    def "should update all active issues balances"() {
        given:
            issueDao.findAllActiveIssues() >> [issue2(), issue3()]
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2019-10-10")
        and:
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_2) >> positiveBalance()
            balanceCalculatorService.recalculateBalanceForIssue(ISSUE_ID_3) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        and:
            def currentDate = parse("2019-10-10")
            dateProvider.getCurrentDate() >> currentDate
            2 * currencyExchangeRateService.findCurrencyExchangeRateByDate(currentDate) >> EUR_EXCHANGE_RATE
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
            issueDao.get(ISSUE_ID_3) >> Optional.of(issue3())
        and:
            invoiceBalanceUpdaterService.updateIssueInvoicesBalance(issue2().getId()) >> positiveBalance()
            invoiceBalanceUpdaterService.updateIssueInvoicesBalance(issue3().getId()) >> balanceWithNegativeBalanceThatIssueShouldBeProceed()
        when:
            activeIssuesBalanceUpdater.updateActiveIssuesBalance()
        then:
            2 * issueDao.update(_ as Issue)
        and:
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_2)
            1 * invoiceBalanceUpdaterService.updateIssueInvoicesBalance(ISSUE_ID_3)
    }

}

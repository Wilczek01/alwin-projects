package com.codersteam.alwin.rest

import com.codersteam.alwin.core.api.model.balance.BalanceStartAndAdditionalDto
import com.codersteam.alwin.core.api.model.currency.Currency
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService
import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2
import static com.codersteam.alwin.testdata.BalanceStartAndAdditionalTestData.balanceStartAndAdditionalEUR1
import static com.codersteam.alwin.testdata.BalanceStartAndAdditionalTestData.balanceStartAndAdditionalPLN1

/**
 * @author Tomasz Sliwinski
 */
class BalanceResourceImplTest extends Specification {

    @Subject
    private BalanceResource resource

    private BalanceCalculatorService balanceCalculatorService = Mock(BalanceCalculatorService)
    private IssueBalanceUpdaterService issueBalanceUpdaterService = Mock(IssueBalanceUpdaterService)

    def "setup"() {
        resource = new BalanceResource(balanceCalculatorService, issueBalanceUpdaterService)
    }

    def "should have default public constructor"() {
        when:
            def resource = new BalanceResource()
        then:
            resource
    }

    def "should update issue and invoices balance"() {
        when:
            resource.updateIssueBalances(ISSUE_ID_2)
        then:
            1 * issueBalanceUpdaterService.getIssueWithUpdatedBalance(ISSUE_ID_2)
    }

    def "should return balance start and additional"() {
        when:
            resource.getBalanceStartAndAdditional(ISSUE_ID_1)
        then:
            1 * balanceCalculatorService.calculateBalanceStartAndAdditional(ISSUE_ID_1) >> {
                Map<Currency, BalanceStartAndAdditionalDto> balanceStartAndAdditional = new HashMap<>()
                balanceStartAndAdditional.put(PLN, balanceStartAndAdditionalPLN1())
                balanceStartAndAdditional.put(EUR, balanceStartAndAdditionalEUR1())
                return balanceStartAndAdditional
            }
    }
}

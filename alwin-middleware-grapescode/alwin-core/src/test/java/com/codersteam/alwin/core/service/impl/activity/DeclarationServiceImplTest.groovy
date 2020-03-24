package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.aida.core.api.service.CompanyService
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.db.dao.ActivityDao
import com.codersteam.alwin.db.dao.DeclarationDao
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.jpa.activity.Declaration
import spock.lang.Specification
import spock.lang.Unroll

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.DeclarationTestData.declaration
import static com.codersteam.alwin.testdata.TestDateUtils.parse

class DeclarationServiceImplTest extends Specification {

    def static DATE_1 = '2017-09-10'
    def static DATE_2 = '2017-09-20'

    DeclarationServiceImpl service

    IssueDao issueDao = Mock(IssueDao)
    ActivityDao activityDao = Mock(ActivityDao)
    DeclarationDao declarationDao = Mock(DeclarationDao)
    AidaService aidaService = Mock(AidaService)
    CompanyService companyService = Mock(CompanyService)

    def "setup"() {
        aidaService.getCompanyService() >> companyService
        service = new DeclarationServiceImpl(issueDao, activityDao, declarationDao, aidaService)
    }

    @Unroll
    def "should check if amount #amount is grater that zero"() {
        expect:
            service.isGraterThanZero(amount) == result
        where:
            amount | result
            -10.0  | false
            0.0    | false
            10.0   | true
    }

    @Unroll
    def "should get declared payment amount for currency #currency"() {
        given:
            def declaration = new Declaration()
            declaration.declaredPaymentAmountPLN = 100.0
            declaration.declaredPaymentAmountEUR = 200.0
        expect:
            service.getDeclaredPaymentAmount(declaration, currency) == result
        where:
            currency | result
            PLN      | 100.0
            EUR      | 200.0
    }

    @Unroll
    def "should filter declaration by declared payment amount for test id #id"() {
        given:
            def declaration = new Declaration()
            declaration.declaredPaymentAmountPLN = amountPLN
            declaration.declaredPaymentAmountEUR = amountEUR
        expect:
            service.filterByDeclaredPaymentAmount(declaration, currency) == result
        where:
            id | amountPLN | amountEUR | currency | result
            1  | 0.0       | 100.0     | PLN      | false
            2  | 0.0       | 100.0     | EUR      | true
            3  | 100.0     | 0.0       | PLN      | true
            4  | 100.0     | 0.0       | EUR      | false
            5  | 100.0     | 100.0     | PLN      | true
            6  | 100.0     | 100.0     | EUR      | true
    }

    @Unroll
    def "should set cash paid for currency #currency"() {
        given:
            def declaration = new Declaration()
            declaration.declaredPaymentAmountPLN = 100.0
            declaration.declaredPaymentAmountEUR = 200.0
            declaration.cashPaidPLN = 10.0
            declaration.cashPaidEUR = 20.0
        when:
            service.setCashPaid(declaration, currency)
        then:
            declaration.cashPaidPLN == cashPaidPLN
            declaration.cashPaidEUR == cashPaidEUR
        where:
            currency | cashPaidPLN | cashPaidEUR
            PLN      | 100.0       | 20.0
            EUR      | 10.0        | 200.0
    }

    def "should get min declaration date"() {
        given:
            def declarations = [declaration(DATE_2), declaration(DATE_1)]
        expect:
            service.getMinDeclarationDate(declarations) == parse(DATE_1)
    }

    def "should get max declared payment date"() {
        given:
            def declarations = [declaration(DATE_1), declaration(DATE_2)]
        expect:
            service.getMaxDeclaredPaymentDate(declarations) == parse(DATE_2)
    }

    @Unroll
    def "should check if declaration is paid for test id #id"() {
        given:
            def declaration = new Declaration()
            declaration.cashPaidPLN = cashPaidPLN
            declaration.declaredPaymentAmountPLN = declaredPaymentAmountPLN
            declaration.cashPaidEUR = cashPaidEUR
            declaration.declaredPaymentAmountEUR = declaredPaymentAmountEUR
        expect:
            service.isDeclarationPaid(declaration) == paid
        where:
            id | cashPaidPLN | declaredPaymentAmountPLN | cashPaidEUR | declaredPaymentAmountEUR | paid
            1  | 0.0         | 0.0                      | 0.0         | 0.0                      | true
            2  | 99.9        | 100.0                    | 0.0         | 0.0                      | false
            3  | 100.0       | 100.0                    | 0.0         | 0.0                      | true
            4  | 100.0       | 100.0                    | 99.0        | 100.0                    | false
            5  | 98.0        | 100.0                    | 100.0       | 100.0                    | false
            6  | 100.0       | 100.0                    | 100.0       | 100.0                    | true
    }

    def "should not update declaration if not exists"() {
        given:
            activityDao.findIssueDeclarations(ISSUE_ID_1) >> []
        when:
            service.updateIssueDeclarations(ISSUE_ID_1)
        then:
            0 * declarationDao.update(_ as Declaration)
    }

    def "should throw exception if issue not exist"() {
        given:
            activityDao.findIssueDeclarations(ISSUE_ID_1) >> [declaration(DATE_1)]
            issueDao.get(ISSUE_ID_1) >> Optional.empty()
        when:
            service.updateIssueDeclarations(ISSUE_ID_1)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == ISSUE_ID_1
            0 * declarationDao.update(_ as Declaration)
    }

    def "should update declaration and set paid to true"() {
        given:
            def declaration = new Declaration()
            declaration.declarationDate = parse(DATE_1)
            declaration.declaredPaymentDate = parse(DATE_2)
            declaration.declaredPaymentAmountPLN = 100.0
            declaration.declaredPaymentAmountEUR = 0.0
            declaration.cashPaidPLN = 0.0
            declaration.cashPaidEUR = 0.0

            activityDao.findIssueDeclarations(ISSUE_ID_1) >> [declaration]
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())

            def paymentsByDate = [:]
            paymentsByDate[parse(DATE_1)] = 10.0
            paymentsByDate[parse(DATE_2)] = 90.0
            companyService.findCompanyPayments(EXT_COMPANY_ID_1, parse(DATE_1), parse(DATE_2), 'PLN') >> paymentsByDate

        when:
            service.updateIssueDeclarations(ISSUE_ID_1)

        then:
            1 * declarationDao.update(_ as Declaration) >> { List args ->
                Declaration updatedDeclaration = args[0]
                assert updatedDeclaration.cashPaidPLN == 100.0
                assert updatedDeclaration.cashPaidEUR == 0.0
                assert updatedDeclaration.paid
            }
    }

    def "should update declaration and set paid to false"() {
        given:
            def declaration = new Declaration()
            declaration.declarationDate = parse(DATE_1)
            declaration.declaredPaymentDate = parse(DATE_2)
            declaration.declaredPaymentAmountPLN = 100.0
            declaration.declaredPaymentAmountEUR = 200.0
            declaration.cashPaidPLN = 0.0
            declaration.cashPaidEUR = 0.0

            activityDao.findIssueDeclarations(ISSUE_ID_1) >> [declaration]
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())

            def paymentsByDatePLN = [:]
            paymentsByDatePLN[parse(DATE_1)] = 10.0
            paymentsByDatePLN[parse(DATE_2)] = 90.0
            companyService.findCompanyPayments(EXT_COMPANY_ID_1, parse(DATE_1), parse(DATE_2), 'PLN') >> paymentsByDatePLN

            def paymentsByDateEUR = [:]
            paymentsByDateEUR[parse(DATE_1)] = 20.0
            paymentsByDateEUR[parse(DATE_2)] = 30.0
            companyService.findCompanyPayments(EXT_COMPANY_ID_1, parse(DATE_1), parse(DATE_2), 'EUR') >> paymentsByDateEUR

        when:
            service.updateIssueDeclarations(ISSUE_ID_1)

        then:
            1 * declarationDao.update(_ as Declaration) >> { List args ->
                Declaration updatedDeclaration = args[0]
                assert updatedDeclaration.cashPaidPLN == 100.0
                assert updatedDeclaration.cashPaidEUR == 50.0
                assert !updatedDeclaration.paid
            }
    }

    def "should update two declaration and set paid to false"() {
        given:
            activityDao.findIssueDeclarations(ISSUE_ID_1) >> [declaration(1), declaration(2)]
            issueDao.get(ISSUE_ID_1) >> Optional.of(issue1())

            def paymentsByDatePLN = [:]
            paymentsByDatePLN[parse(DATE_1)] = 10.0
            paymentsByDatePLN[parse(DATE_2)] = 100.0
            companyService.findCompanyPayments(EXT_COMPANY_ID_1, parse(DATE_1), parse(DATE_2), 'PLN') >> paymentsByDatePLN

        when:
            service.updateIssueDeclarations(ISSUE_ID_1)

        then:
            2 * declarationDao.update(_ as Declaration) >> { List args ->
                Declaration updatedDeclaration = args[0]
                if (updatedDeclaration.id == 1) {
                    assert updatedDeclaration.cashPaidPLN == 100.0
                    assert updatedDeclaration.cashPaidEUR == 0.0
                    assert updatedDeclaration.paid
                }
                if (updatedDeclaration.id == 2) {
                    assert updatedDeclaration.cashPaidPLN == 10.0
                    assert updatedDeclaration.cashPaidEUR == 0.0
                    assert !updatedDeclaration.paid
                }
            }
    }
}

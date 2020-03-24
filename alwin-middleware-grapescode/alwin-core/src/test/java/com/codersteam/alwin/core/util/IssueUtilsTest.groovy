package com.codersteam.alwin.core.util

import com.codersteam.alwin.common.issue.Segment
import com.codersteam.alwin.jpa.issue.Issue
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue2
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto2
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue7
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issueDto7
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_10
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5

/**
 * @author Tomasz Sliwinski
 */
class IssueUtilsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = IssueUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should retrieve companyId from issue for contract"() {
        given: 'issue with contract customer'
            def issue = issueDto7()
        when:
            def companyId = IssueUtils.getExtCompanyId(issue)
        then:
            companyId == EXT_COMPANY_ID_10
    }

    def "should retrieve companyId from issue with customer"() {
        given: 'issue with customer'
            def issue = issueDto2()
        when:
            def companyId = IssueUtils.getExtCompanyId(issue)
        then:
            companyId == EXT_COMPANY_ID_5
    }

    def "should retrieve extCompanyId from issue for contract"() {
        given: 'issue with contract customer'
            def issue = issue7()
        when:
            def extCompanyId = IssueUtils.getExtCompanyId(issue)
        then:
            extCompanyId == EXT_COMPANY_ID_10
    }

    def "should retrieve extCompanyId from issue with customer"() {
        given: 'issue with customer'
            def issue = issue2()
        when:
            def extCompanyId = IssueUtils.getExtCompanyId(issue)
        then:
            extCompanyId == EXT_COMPANY_ID_5
    }

    def "should return null extCompanyId if company not set"() {
        given: 'issue with customer'
            def issue = issue2()
        and: 'no company defined'
            issue.customer.company = null
            issue.contract.customer.company = null
        when:
            def extCompanyId = IssueUtils.getExtCompanyId(issue)
        then:
            extCompanyId == null
    }

    def "should retrieve segment from issue with contract customer company"() {
        given: 'issue with contract customer'
            def issue = issue7()
        when:
            def segment = IssueUtils.getCompanySegmentFromIssue(issue)
        then:
            segment == Segment.A
    }

    def "should retrieve segment from issue with customer company"() {
        given: 'issue with customer'
            def issue = issue2()
        when:
            def segment = IssueUtils.getCompanySegmentFromIssue(issue)
        then:
            segment == Segment.A
    }

    def "should return null if company segment not set"() {
        given: 'issue with customer'
            def issue = issue2()
        and: 'no company segment defined'
            issue.getCustomer().getCompany().segment = null
        when:
            def segment = IssueUtils.getCompanySegmentFromIssue(issue)
        then:
            segment == null
    }

    def "should return null segment if company not set"() {
        given: 'issue with customer'
            def issue = issue2()
        and: 'no company defined'
            issue.customer.company = null
            issue.contract.customer.company = null
        when:
            def segment = IssueUtils.getCompanySegmentFromIssue(issue)
        then:
            segment == null
    }

    @Unroll
    def "should check if issue with id #id is paid"() {
        given:
            def issue = new Issue()
            issue.id = id
            issue.currentBalancePLN = currentBalancePLN
            issue.currentBalanceEUR = currentBalanceEUR
        when:
            def paid = IssueUtils.isPaid(issue)
        then:
            paid == expectedPaid
        where:
            id | currentBalancePLN | currentBalanceEUR | expectedPaid
            1  | -100.00           | -100.00           | false
            2  | 0.00              | -100.00           | false
            3  | -100.00           | 0.00              | false
            3  | 0.00              | 0.00              | true
            3  | 10.00             | 0.00              | true
            3  | 0.00              | 10.00             | true
            3  | 10.00             | 10.00             | true
    }
}

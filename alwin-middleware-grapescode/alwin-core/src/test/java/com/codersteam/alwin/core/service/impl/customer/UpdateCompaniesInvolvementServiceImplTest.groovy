package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.alwin.core.api.service.customer.InvolvementService
import com.codersteam.alwin.db.dao.IssueDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyTestData.*

class UpdateCompaniesInvolvementServiceImplTest extends Specification {

    @Subject
    private UpdateCompaniesInvolvementService service

    private IssueDao issueDao = Mock(IssueDao)
    private InvolvementService involvementService = Mock(InvolvementService)

    def "setup"() {
        service = new UpdateCompaniesInvolvementService(issueDao, involvementService)
    }

    def "should not update companies involvement if companies with active issue not exists"() {
        given:
            issueDao.findAllCompaniesWithActiveIssue() >> []
        when:
            service.updateCompaniesInvolvement()
        then:
            0 * involvementService.updateCompanyInvolvement(_ as Long)
    }

    def "should update companies involvement"() {
        given:
            issueDao.findAllCompaniesWithActiveIssue() >> [company1(), company2()]
        when:
            service.updateCompaniesInvolvement()
        then:
            1 * involvementService.updateCompanyInvolvement(COMPANY_ID_1)
            1 * involvementService.updateCompanyInvolvement(COMPANY_ID_2)
    }

    def "should continue updating companies involvement when error occred"() {
        given:
            issueDao.findAllCompaniesWithActiveIssue() >> [company1(), company2()]
        when:
            service.updateCompaniesInvolvement()
        then:
            1 * involvementService.updateCompanyInvolvement(COMPANY_ID_1) >> {throw new RuntimeException('runtime')}
            1 * involvementService.updateCompanyInvolvement(COMPANY_ID_2)
    }
}

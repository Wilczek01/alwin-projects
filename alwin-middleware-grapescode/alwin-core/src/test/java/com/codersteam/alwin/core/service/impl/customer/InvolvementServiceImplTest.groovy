package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.aida.core.api.service.InvolvementService
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.jpa.customer.Company
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.assertion.CompanyAssert.assertEquals

/**
 * @author Piotr Naroznik
 */
class InvolvementServiceImplTest extends Specification {

    @Subject
    private InvolvementServiceImpl service

    private CompanyDao companyDao = Mock(CompanyDao)
    private aidaService = Mock(AidaService)
    private InvolvementService aidaInvolvementService = Mock(InvolvementService)

    def "setup"() {
        aidaService.getInvolvementService() >> aidaInvolvementService
        service = new InvolvementServiceImpl(companyDao, aidaService)
    }

    def "should throw exception when company not exist in company involvement update"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.empty()
        when:
            service.updateCompanyInvolvement(COMPANY_ID_1)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMPANY_ID_1
            0 * companyDao.update(_ as Company)
    }

    def "should not update company involvement if involvement was not changed"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())
            aidaInvolvementService.calculateCompanyInvolvement(EXT_COMPANY_ID_1) >> INVOLVEMENT_1
        when:
            service.updateCompanyInvolvement(COMPANY_ID_1)
        then:
            0 * companyDao.update(_ as Company)
    }

    def "should update company involvement"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())
            aidaInvolvementService.calculateCompanyInvolvement(EXT_COMPANY_ID_1) >> INVOLVEMENT_2
        when:
            service.updateCompanyInvolvement(COMPANY_ID_1)
        then:
            1 * companyDao.update(_ as Company) >> { args ->
                def company = (Company) args[0]
                def expectedCompany = company1()
                expectedCompany.involvement = INVOLVEMENT_2
                assertEquals(company, expectedCompany)
                company
            }
    }

}
package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.aida.core.api.service.InvolvementService
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.customer.CustomerService
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.AlwinConstants.ZERO
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10
import static com.codersteam.alwin.testdata.aida.AidaCustomerInvolvementTestData.*

/**
 * @author Tomasz Sliwinski
 */
class CustomerRPBServiceImplTest extends Specification {

    @Subject
    private CustomerRPBServiceImpl service

    private InvolvementService involvementService = Mock(InvolvementService)
    private CustomerService customerService = Mock(CustomerService)

    def setup() {
        def aidaService = Mock(AidaService)
        aidaService.involvementService >> involvementService
        service = new CustomerRPBServiceImpl(aidaService, customerService)
    }

    def "should return ZERO RPB when no data found in AIDA"() {
        given:
            involvementService.getInvolvements(COMPANY_ID_10) >> []
        when:
            def rpb = service.calculateCompanyRPB(COMPANY_ID_10)
        then:
            rpb == ZERO
    }

    def "should calculate RPB only from non blocked contracts and not null values"() {
        given:
            involvementService.getInvolvements(COMPANY_ID_10) >> allCompany10CustomerInvolvements()
        and:
            customerService.findActiveContractOutOfServiceNumbers(COMPANY_ID_10) >> [CUSTOMER_INVOLVEMENT_CONTRACT_NO_3]
        when:
            def rpb = service.calculateCompanyRPB(COMPANY_ID_10)
        then:
            rpb == CUSTOMER_INVOLVEMENT_RPB_1 + CUSTOMER_INVOLVEMENT_RPB_2 + CUSTOMER_INVOLVEMENT_RPB_5
    }
}

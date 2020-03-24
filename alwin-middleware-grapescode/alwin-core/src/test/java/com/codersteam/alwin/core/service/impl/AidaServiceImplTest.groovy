package com.codersteam.alwin.core.service.impl

import spock.lang.Specification
import spock.lang.Subject

import javax.ejb.EJB

/**
 * @author Michal Horowic
 */
class AidaServiceImplTest extends Specification {

    @Subject
    AidaServiceImpl aidaService

    def "setup"() {
        aidaService = new AidaServiceImpl()
        aidaService.class.declaredFields
                .findAll { field -> field.getAnnotation(EJB) != null }
                .each { injectedField -> injectedField.setAccessible(true); injectedField.set(aidaService, Mock(injectedField.type)) }
    }

    def "should get InvoiceService"() {
        when:
            def service = aidaService.getInvoiceService()

        then:
            service != null
    }

    def "should get ContractService"() {
        when:
            def service = aidaService.getContractService()

        then:
            service != null
    }

    def "should get CompanyService"() {
        when:
            def service = aidaService.getCompanyService()

        then:
            service != null
    }

    def "should get PersonService"() {
        when:
            def service = aidaService.getPersonService()

        then:
            service != null
    }

    def "should get SettlementService"() {
        when:
            def service = aidaService.getSettlementService()

        then:
            service != null
    }

    def "should get InvolvementService"() {
        when:
            def service = aidaService.getInvolvementService()

        then:
            service != null
    }

    def "should get SegmentService"() {
        when:
            def service = aidaService.getSegmentService()

        then:
            service != null
    }

    def "should get ExcessPaymentService"() {
        when:
            def service = aidaService.getExcessPaymentService()

        then:
            service != null
    }

    def "should get CurrencyExchangeRateService"() {
        when:
            def service = aidaService.getCurrencyExchangeRateService()

        then:
            service != null
    }

    def "should get ContractSubjectService"() {
        when:
            def service = aidaService.getContractSubjectService()

        then:
            service != null
    }
}

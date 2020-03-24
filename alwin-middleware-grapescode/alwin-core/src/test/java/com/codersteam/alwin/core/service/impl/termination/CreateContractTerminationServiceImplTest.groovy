package com.codersteam.alwin.core.service.impl.termination

import com.codersteam.alwin.common.termination.ContractTerminationType
import com.codersteam.alwin.core.api.service.notice.FormalDebtCollectionService
import com.codersteam.alwin.core.api.service.termination.ContractTerminationCreatorService
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.ContractTerminationInitialDataTestData.contractTerminationInitialData1
import static com.codersteam.alwin.testdata.ContractTerminationInitialDataTestData.contractTerminationInitialData2

class CreateContractTerminationServiceImplTest extends Specification {

    @Subject
    private CreateContractTerminationService service

    private ContractTerminationCreatorService contractTerminationCreatorService = Mock(ContractTerminationCreatorService)
    private FormalDebtCollectionService formalDebtCollectionService = Mock(FormalDebtCollectionService)

    def "setup"() {
        service = Spy(CreateContractTerminationService, constructorArgs: [contractTerminationCreatorService,
                                                                          formalDebtCollectionService]) as CreateContractTerminationService
    }

    def "should prepare contract terminations"() {
        when:
            service.prepareContractTerminations()
        then:
            1 * service.prepareConditionalContractTerminations()
        then:
            1 * service.prepareImmediateContractTerminations()
    }

    def "should not prepare conditional contract terminations when no data found"() {
        given:
            formalDebtCollectionService.findConditionalContractTerminationsToCreate() >> []
        when:
            service.prepareConditionalContractTerminations()
        then:
            0 * contractTerminationCreatorService.prepareContractTermination(_ as ContractTerminationInitialData, ContractTerminationType.CONDITIONAL)
    }

    def "should prepare conditional contract terminations"() {
        given:
            formalDebtCollectionService.findConditionalContractTerminationsToCreate() >> [contractTerminationInitialData1(), contractTerminationInitialData2()]
        when:
            service.prepareConditionalContractTerminations()
        then:
            2 * contractTerminationCreatorService.prepareContractTermination(_ as ContractTerminationInitialData, ContractTerminationType.CONDITIONAL)
    }

    def "should continue prepare conditional contract terminations even when error occurred"() {
        given:
            formalDebtCollectionService.findConditionalContractTerminationsToCreate() >> [contractTerminationInitialData1(), contractTerminationInitialData2()]
        and:
            contractTerminationCreatorService.prepareContractTermination(_ as ContractTerminationInitialData, ContractTerminationType.CONDITIONAL) >> {
                throw new RuntimeException("ERROR")
            }
        when:
            service.prepareConditionalContractTerminations()
        then:
            noExceptionThrown()
    }

    def "should not prepare immediate contract terminations when no data found"() {
        given:
            formalDebtCollectionService.findImmediateContractTerminationsToCreate() >> []
        when:
            service.prepareImmediateContractTerminations()
        then:
            0 * contractTerminationCreatorService.prepareContractTermination(_ as ContractTerminationInitialData, ContractTerminationType.IMMEDIATE)
    }

    def "should prepare immediate contract terminations"() {
        given:
            formalDebtCollectionService.findImmediateContractTerminationsToCreate() >> [contractTerminationInitialData1(), contractTerminationInitialData2()]
        when:
            service.prepareImmediateContractTerminations()
        then:
            2 * contractTerminationCreatorService.prepareContractTermination(_ as ContractTerminationInitialData, ContractTerminationType.IMMEDIATE)
    }

    def "should continue prepare immediate contract terminations even when error occurred"() {
        given:
            formalDebtCollectionService.findImmediateContractTerminationsToCreate() >> [contractTerminationInitialData1(), contractTerminationInitialData2()]
        and:
            contractTerminationCreatorService.prepareContractTermination(_ as ContractTerminationInitialData, ContractTerminationType.IMMEDIATE) >> {
                throw new RuntimeException("ERROR")
            }
        when:
            service.prepareImmediateContractTerminations()
        then:
            noExceptionThrown()
    }


}

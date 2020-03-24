package com.codersteam.alwin.core.service.impl.termination

import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria
import com.codersteam.alwin.core.api.model.termination.ActivateContractTerminationDto
import com.codersteam.alwin.core.api.model.termination.TerminationDto
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.termination.ProcessContractTerminationService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.ContractTerminationDao
import com.codersteam.alwin.jpa.termination.ContractTermination
import com.google.common.collect.Sets
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.termination.ContractTerminationState.*
import static com.codersteam.alwin.testdata.ContractTerminationTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class ContractTerminationServiceImplTest extends Specification {

    @Subject
    private ContractTerminationServiceImpl service

    private ContractTerminationDao contractTerminationDao = Mock(ContractTerminationDao)
    private ProcessContractTerminationService processContractTerminations = Mock(ProcessContractTerminationService)
    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties properties = Mock(AlwinProperties)
    private AlwinMapper mapper = new AlwinMapper(dateProvider, properties)

    def "setup"() {
        service = Spy(ContractTerminationServiceImpl, constructorArgs: [mapper, contractTerminationDao, dateProvider,
                                                                        processContractTerminations]) as ContractTerminationServiceImpl
    }

    def "should execute update with correct status and remarks for reject contract terminations"() {
        given:
            contractTerminationDao.findByIdsIn(Sets.newHashSet(ID_104, ID_105)) >> [contractTermination104(), contractTermination105()]
            def terminationDto1 = new TerminationDto()
            def dto104 = terminationContractDto104()
            dto104.remark = "Remark for id 104"
            def dto105 = terminationContractDto105()
            dto105.remark = null
            terminationDto1.contracts = [dto104, dto105]
        when:
            service.rejectContractTerminations([terminationDto1])
        then:
            noExceptionThrown()
        and:
            2 * contractTerminationDao.update(_ as ContractTermination) >> { List arguments ->
                ContractTermination contractTermination = arguments[0] as ContractTermination
                assert contractTermination.state == REJECTED
                if (contractTermination.id == ID_104)
                    assert contractTermination.remark == "Remark for id 104"
                else if (contractTermination.id == ID_105)
                    assert contractTermination.remark == null
            }
    }

    def "should execute update with correct status and remarks for postpone contract terminations"() {
        given:
            contractTerminationDao.findByIdsIn(Sets.newHashSet(ID_104, ID_105)) >> [contractTermination104(), contractTermination105()]
            def terminationDto1 = new TerminationDto()
            def terminationContractDto104 = terminationContractDto104()
            terminationContractDto104.terminationDate = parse("2018-09-04")
            def terminationContractDto105 = terminationContractDto105()
            terminationContractDto105.terminationDate = parse("2018-09-05")
            terminationDto1.contracts = [terminationContractDto104, terminationContractDto105]
        when:
            service.postponeContractTerminations([terminationDto1])
        then:
            noExceptionThrown()
        and:
            2 * contractTerminationDao.update(_ as ContractTermination) >> { List arguments ->
                ContractTermination contractTermination = arguments[0] as ContractTermination
                assert contractTermination.state == POSTPONED
                if (contractTermination.id == ID_104)
                    assert contractTermination.terminationDate == parse("2018-09-04")
                else if (contractTermination.id == ID_105)
                    assert contractTermination.terminationDate == parse("2018-09-05")
            }
    }

    def "should find terminations by state"() {
        given:
            def firstResult = 0
            def maxResults = 10
            def totalValues = 3
            def searchCriteria = new ContractTerminationSearchCriteria()
            searchCriteria.setFirstResult(firstResult)
            searchCriteria.setMaxResults(maxResults)
            searchCriteria.setState(NEW)

        and:
            contractTerminationDao.findByStatePaginatedByExtCompanyIdAndTerminationDateAndType(searchCriteria, new LinkedHashMap<>()) >> [
                    contractTermination102(), contractTermination105(), contractTermination104()
            ]
        and:
            contractTerminationDao.countInStatePaginatedByExtCompanyIdAndTerminationDate(searchCriteria) >> totalValues
        when:
            def terminationsHistoryPage = service.findTerminations(searchCriteria, new LinkedHashMap<>())
        then:
            terminationsHistoryPage.values.size() == 3
            terminationsHistoryPage.totalValues == totalValues
    }

    def "should find terminations to process"() {
        given:
            def terminationDateLte = parse("2018-10-10")
            dateProvider.currentDateEndOfDay >> terminationDateLte
            contractTerminationDao.findByStatesAndTerminationDateLte(OPEN_CONTRACT_TERMINATION_STATES, terminationDateLte) >> [
                    contractTermination101(), contractTermination102(), contractTermination105(), contractTermination104()
            ]
        when:
            def terminationsToProcess = service.findTerminationsToProcess()
        then:
            terminationsToProcess.size() == 4
    }

    def "should update contract termination when activation is called"() {
        given:
            contractTerminationDao.get(1L) >> Optional.of(contractTermination101())
            def activationDate = new Date()
            def resumptionCost = new BigDecimal("1320.50")
            def remark = "Some remark"
        and:
            def termination = new ActivateContractTerminationDto()
            termination.activationDate = activationDate
            termination.resumptionCost = resumptionCost
            termination.remark = remark
        when:
            service.activateContractTermination(1L, termination)
        then:
            1 * contractTerminationDao.update(_ as ContractTermination) >> { List arguments ->
                ContractTermination contractTermination = arguments[0] as ContractTermination
                assert contractTermination.id == ID_101
                assert contractTermination.state == CONTRACT_ACTIVATED
                assert contractTermination.activationDate == activationDate
                assert contractTermination.resumptionCost == resumptionCost
                assert contractTermination.remark == remark
            }
    }
}

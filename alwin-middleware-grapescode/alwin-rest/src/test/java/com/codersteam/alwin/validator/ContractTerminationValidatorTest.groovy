package com.codersteam.alwin.validator

import com.codersteam.alwin.common.termination.ContractTerminationState
import com.codersteam.alwin.common.termination.ContractTerminationType
import com.codersteam.alwin.core.api.model.termination.*
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.termination.ContractTerminationService
import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.testdata.TestDateUtils
import org.apache.commons.lang.time.DateUtils
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.termination.ContractTerminationState.ISSUED
import static com.codersteam.alwin.common.termination.ContractTerminationState.POSTPONED
import static com.codersteam.alwin.common.termination.ContractTerminationType.CONDITIONAL
import static com.codersteam.alwin.common.termination.ContractTerminationType.IMMEDIATE

/**
 * @author Dariusz Rackowski
 */
class ContractTerminationValidatorTest extends Specification {

    @Subject
    ContractTerminationValidator validator

    private ContractTerminationService contractTerminationService = Mock(ContractTerminationService)
    private DateProvider dateProvider = Mock(DateProvider)

    def "setup"() {
        validator = new ContractTerminationValidator(contractTerminationService, dateProvider)
        contractTerminationService.findContractIdToCompanyIds(_ as Set) >> [103L: 10L, 104L: 10L, 203L: 20L, 301L: 30L]
        contractTerminationService.findContractIdToContractType(_ as Set) >> [103L: CONDITIONAL, 104L: IMMEDIATE, 203L: CONDITIONAL, 301L: 30L]
    }

    def "should extract termination ids"() {
        given:
            List<TerminationDto> terminationList = [termination1(), termination2()]
        when:
            def ids = validator.extractContractTerminationIds(terminationList)
        then:
            ids == new HashSet<>([103L, 104L, 205L, 206L])
    }

    def "should validate company id for contract id"() {
        given:
            List<TerminationDto> terminationList = [termination1(), termination2()]
            Map<Long, Long> contractIdToCompanyId = [103L: 10L, 104L: 10L, 205L: 20L, 206L: 20L]
        when:
            validator.validateCompanyIdForContractId(terminationList, contractIdToCompanyId)
        then:
            noExceptionThrown()
    }

    def "should validate company id for contract id throw exception"() {
        given:
            List<TerminationDto> terminationList = [termination1(), termination2()]
            Map<Long, Long> contractIdToCompanyId = [103L: 11L, 104L: 10L, 205L: 20L, 206L: 20L]
        when:
            validator.validateCompanyIdForContractId(terminationList, contractIdToCompanyId)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == 'Nr klienta nie jest prawidłowy dla wypowiedzenia umowy o id 103'
    }

    def "should validate termination type - should be possible change CONDITIONAL to IMMEDIATE"() {
        given:
            List<TerminationDto> terminationList = [termination1(), termination2()]
            Map<Long, ContractTerminationType> currentContractIdToTerminationType = [103L: CONDITIONAL, 104L: CONDITIONAL, 205L: CONDITIONAL, 206L: IMMEDIATE]
        when:
            validator.validateTerminationType(terminationList, currentContractIdToTerminationType)
        then:
            noExceptionThrown()
    }

    def "should validate termination type - shouldn't be possible to change IMMEDIATE to CONDITIONAL"() {
        given:
            List<TerminationDto> terminationList = [termination1(), termination2()]
            Map<Long, ContractTerminationType> currentContractIdToTerminationType = [103L: IMMEDIATE, 104L: CONDITIONAL, 205L: IMMEDIATE, 206L: IMMEDIATE]
        when:
            validator.validateTerminationType(terminationList, currentContractIdToTerminationType)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == 'Natychmiastowe wypowiedzenie umowy nie może zostać zmienione na warunkowe (umowa o id 103)'
    }

    def "should validate termination type - shouldn't be possible to send different types in one group"() {
        given:
            def dto = termination1()
            dto.type = IMMEDIATE
            dto.contracts[0].type = IMMEDIATE
        and:
            List<TerminationDto> terminationList = [dto, termination2()]
            Map<Long, ContractTerminationType> currentContractIdToTerminationType = [103L: CONDITIONAL, 104L: CONDITIONAL, 205L: CONDITIONAL, 206L: IMMEDIATE]
        when:
            validator.validateTerminationType(terminationList, currentContractIdToTerminationType)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == 'Wybrano więcej niż jeden typ wypowiedzenia dla klienta o id 10'
    }

    def "should validate termination type - shouldn't be possible to send group with type different than any contract type"() {
        given:
            def dto = termination1()
            dto.type = CONDITIONAL
            dto.contracts[0].type = IMMEDIATE
            dto.contracts[1].type = IMMEDIATE
        and:
            List<TerminationDto> terminationList = [dto, termination2()]
            Map<Long, ContractTerminationType> currentContractIdToTerminationType = [103L: CONDITIONAL, 104L: CONDITIONAL, 205L: CONDITIONAL, 206L: IMMEDIATE]
        when:
            validator.validateTerminationType(terminationList, currentContractIdToTerminationType)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == 'Typ wypowiedzenia w grupie musi się zgadzać z typem w każdym kontrakcie dla klienta o id 10'
    }

    def "should validate new termination date is greater than current date"() {
        given:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
        and:
            List<TerminationDto> terminationList = [termination1(), termination2()]
        and:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
        when:
            validator.validateNewTerminationDateIsGreaterThanCurrentDate(terminationList)
        then:
            noExceptionThrown()
    }

    def "should validate new termination date is greater than current date throw exception"() {
        given:
            List<TerminationDto> terminationList = [termination1(), termination2()]
        and:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-02-02 23:59:59.999999")
        when:
            validator.validateNewTerminationDateIsGreaterThanCurrentDate(terminationList)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == 'Wybrano nieprawidłowe daty odroczenia wypowiedzenia umów dla wypowiedzeń [103, 104]'
    }

    def "should validate with success"() {
        given:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
        and:
            def terminations = new ProcessContractsTerminationDto()
            terminations.terminationsToSend = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])]),
                    termination(20L, IMMEDIATE, [contract(203L, "2018-01-03", IMMEDIATE, ISSUED, null, [
                            subject(2032L, 1090L, null),
                    ])])
            ]
            terminations.terminationsToReject = [
                    termination(10L, IMMEDIATE, [contract(104L, "2018-01-03", IMMEDIATE, ISSUED, null, [
                            subject(1043L, 1090L, "200.00")
                    ])])
            ]
            terminations.terminationsToPostpone = [
                    termination(30L, IMMEDIATE, [contract(301L, "2018-03-20", IMMEDIATE, ISSUED, null, [
                            subject(3011L, 1090L, "123.00")
                    ])])
            ]
        when:
            validator.validate(terminations)
        then:
            noExceptionThrown()
    }

    def "should validate only one operation for each contract id 1"() {
        given:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
            contractTerminationService.findNotExistingContractTerminationIds(_ as Set) >> []
            contractTerminationService.findNotReadyToProcessContractTerminations(_ as Set) >> []
        and:
            def terminations = new ProcessContractsTerminationDto()
            terminations.terminationsToSend = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])]),
                    termination(20L, IMMEDIATE, [contract(203L, "2018-01-03", IMMEDIATE, ISSUED, null, [
                            subject(2032L, 1090L, null),
                    ])])
            ]
            terminations.terminationsToReject = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])])
            ]
            terminations.terminationsToPostpone = []
        when:
            validator.validate(terminations)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wypowiedzenie umowy musi występować co najwyżej w jednym operacji"
    }

    def "should validate only one operation for each contract id 2"() {
        given:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
            contractTerminationService.findNotExistingContractTerminationIds(_ as Set) >> []
            contractTerminationService.findNotReadyToProcessContractTerminations(_ as Set) >> []
        and:
            def terminations = new ProcessContractsTerminationDto()
            terminations.terminationsToSend = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])]),
                    termination(20L, IMMEDIATE, [contract(203L, "2018-01-03", IMMEDIATE, ISSUED, null, [
                            subject(2032L, 1090L, null),
                    ])])
            ]
            terminations.terminationsToReject = []
            terminations.terminationsToPostpone = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])])
            ]
        when:
            validator.validate(terminations)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wypowiedzenie umowy musi występować co najwyżej w jednym operacji"
    }


    def "should validate existing of contract terminations"() {
        given:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
            contractTerminationService.findNotExistingContractTerminationIds(_ as Set) >> [203L]
            contractTerminationService.findNotReadyToProcessContractTerminations(_ as Set) >> []
        and:
            def terminations = new ProcessContractsTerminationDto()
            terminations.terminationsToSend = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])]),
                    termination(20L, IMMEDIATE, [contract(203L, "2018-01-03", IMMEDIATE, ISSUED, null, [
                            subject(2032L, 1090L, null),
                    ])])
            ]
            terminations.terminationsToReject = []
            terminations.terminationsToPostpone = []
        when:
            validator.validate(terminations)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wypowiedzenia umów o podanych identyfikatorach [203] nie istnieją"
    }

    def "should validate ready to process states of contract terminations"() {
        given:
            dateProvider.getCurrentDateEndOfDay() >> TestDateUtils.parse("2018-01-02 23:59:59.999999")
            contractTerminationService.findNotReadyToProcessContractTerminations(_ as Set) >> [103L]
            contractTerminationService.findNotExistingContractTerminationIds(_ as Set) >> []
        and:
            def terminations = new ProcessContractsTerminationDto()
            terminations.terminationsToSend = [
                    termination(10L, CONDITIONAL, [contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                            subject(1031L, 1090L, "123.00"),
                            subject(1032L, 1090L, null),
                    ])]),
                    termination(20L, IMMEDIATE, [contract(203L, "2018-01-03", IMMEDIATE, ISSUED, null, [
                            subject(2032L, 1090L, null),
                    ])])
            ]
            terminations.terminationsToReject = []
            terminations.terminationsToPostpone = []
        when:
            validator.validate(terminations)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wypowiedzenia umów o podanych identyfikatorach [103] zostały już obsłużone"
    }

    def "should validate activation contract termination successfully"() {
        given:
            contractTerminationService.findTermination(1L) >> Optional.of(terminationContract103())
        and:
            def activateContractTerminationDto = new ActivateContractTerminationDto()
            activateContractTerminationDto.activationDate = new Date()
            activateContractTerminationDto.resumptionCost = new BigDecimal("123.00")
            activateContractTerminationDto.remark = ""
        when:
            validator.validate(1L, activateContractTerminationDto)
        then:
            noExceptionThrown()
    }

    def "should activation contract termination validation check if termination exists"() {
        given:
            contractTerminationService.findTermination(1L) >> Optional.empty()
        and:
            def activateContractTerminationDto = new ActivateContractTerminationDto()
        when:
            validator.validate(1L, activateContractTerminationDto)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wypowiedzenie umowy o podanym id (1) nie istnieje"
    }

    def "should activation contract termination validation check state"() {
        given:
            def contract103 = terminationContract103()
            contract103.state = POSTPONED
            contractTerminationService.findTermination(1L) >> Optional.of(contract103)
        and:
            def activateContractTerminationDto = new ActivateContractTerminationDto()
        when:
            validator.validate(1L, activateContractTerminationDto)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Wypowiedzenie umowy o id 1 nie jest wysłane"
    }

    def "should activation contract termination validation check if activation date is present"() {
        given:
            contractTerminationService.findTermination(1L) >> Optional.of(terminationContract103())
        and:
            def activateContractTerminationDto = new ActivateContractTerminationDto()
            activateContractTerminationDto.resumptionCost = new BigDecimal("123.00")
            activateContractTerminationDto.activationDate = null
        when:
            validator.validate(1L, activateContractTerminationDto)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Nie wybrano daty aktywacji dla wypowiedzenia o id 1"
    }

    def "should activation contract termination validation check if activation date is not before termination date present"() {
        given:
            def termination103 = terminationContract103()
            contractTerminationService.findTermination(1L) >> Optional.of(termination103)
        and:
            def activateContractTerminationDto = new ActivateContractTerminationDto()
            activateContractTerminationDto.resumptionCost = new BigDecimal("123.00")
            activateContractTerminationDto.activationDate = DateUtils.addDays(termination103.getTerminationDate(), -2)
        when:
            validator.validate(1L, activateContractTerminationDto)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Daty aktywacji dla wypowiedzenia o id 1 nie może być starsza niż data wypowiedzenia"
    }

    def "should activation contract termination validation check if resumption cost is present"() {
        given:
            def termination103 = terminationContract103()
            contractTerminationService.findTermination(1L) >> Optional.of(termination103)
        and:
            def activateContractTerminationDto = new ActivateContractTerminationDto()
            activateContractTerminationDto.resumptionCost = null
            activateContractTerminationDto.activationDate = DateUtils.addDays(termination103.getTerminationDate(), 2)
        when:
            validator.validate(1L, activateContractTerminationDto)
        then:
            def exception = thrown(AlwinValidationException)
        and:
            exception.message == "Nie podano wartości opłaty za wznowienie dla wypowiedzenia o id 1"
    }

    TerminationContractDto terminationContract103() {
        return contract(103L, "2018-01-03", CONDITIONAL, ISSUED, null, [
                subject(1031L, 1090L, "123.00"),
                subject(1032L, 1090L, null)
        ])
    }

    TerminationDto termination1() {
        def terminationContract1 = terminationContract103()
        def terminationContract2 = contract(104L, "2018-01-04", CONDITIONAL, ISSUED, null, [
                subject(1043L, 1090L, "123.00")
        ])

        termination(10L, CONDITIONAL, [terminationContract1, terminationContract2])
    }

    TerminationDto termination2() {
        def terminationContract1 = contract(205L, "2018-02-05", IMMEDIATE, ISSUED, null, [
                subject(2051L, 1090L, "123.00"),
                subject(2052L, 1090L, null)
        ])
        def terminationContract2 = contract(206L, "2018-02-06", IMMEDIATE, ISSUED, null, [
                subject(2063L, 1090L, "123.00")
        ])

        termination(20L, IMMEDIATE, [terminationContract1, terminationContract2])
    }

    private static TerminationDto termination(Long extCompanyId, ContractTerminationType type, List contracts) {
        def termination = new TerminationDto()
        termination.extCompanyId = extCompanyId
        termination.contracts = contracts
        termination.type = type
        termination
    }

    private static TerminationContractDto contract(Long id, String terminationDate, ContractTerminationType type,
                                                   ContractTerminationState state, String remark, List subjects) {
        def terminationContract1 = new TerminationContractDto()
        terminationContract1.contractTerminationId = id
        terminationContract1.terminationDate = terminationDate != null ? TestDateUtils.parse(terminationDate) : null
        terminationContract1.type = type
        terminationContract1.state = state
        terminationContract1.remark = remark
        terminationContract1.subjects = subjects
        terminationContract1
    }

    private static void subject(Long id, Long subjectId, String installationCharge) {
        def subject = new TerminationContractSubjectDto()
        subject.id = id
        subject.subjectId = subjectId
        subject.gpsToInstall = installationCharge != null
        subject.gpsInstallationCharge = installationCharge != null ? new BigDecimal(installationCharge) : null
    }
}

package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto
import com.codersteam.alwin.core.api.model.activity.ActivityDto
import com.codersteam.alwin.core.api.model.activity.ActivityStateDto
import com.codersteam.alwin.core.api.model.declaration.DeclarationDto
import com.codersteam.alwin.core.api.model.issue.IssueDto
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityDetailPropertyService
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.exception.AlwinValidationException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.ZoneOffset

import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.PLANNED
import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.POSTPONED
import static com.codersteam.alwin.testdata.ActivityTestData.*
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_DTO_1
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_DTO_2
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeDto1
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static java.util.Collections.singletonList

/**
 * @author Michal Horowic
 */
class ActivityValidatorTest extends Specification {

    @Subject
    private ActivityValidator activityValidator

    private ActivityService activityService = Mock(ActivityService)
    private IssueService issueService = Mock(IssueService)
    private DateProvider dateProvider = Mock(DateProvider)
    private ActivityTypeService activityTypeService = Mock(ActivityTypeService)
    private ActivityDetailPropertyService activityDetailPropertyService = Mock(ActivityDetailPropertyService)

    def "setup"() {
        activityValidator = new ActivityValidator(issueService, activityService, dateProvider, activityTypeService, activityDetailPropertyService)
    }

    def "should throw an exception if property does not have a value"(def type) {
        given:
            def detail = new ActivityDetailDto()
            detail.value = null
            detail.required = true
            detail.detailProperty = new ActivityDetailPropertyDto()
            detail.detailProperty.type = type
            detail.detailProperty.name = "Testowy label"

        and:
            def activity = new ActivityDto()
            activity.activityDetails = singletonList(detail)

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Wartość dla 'Testowy label' jest wymagana"

        where:
            type << [Date.canonicalName, Boolean.canonicalName, Integer.canonicalName, String.canonicalName]

    }

    def "should throw an exception if property does not have a value and required is null"(def type) {
        given:
            def detail = new ActivityDetailDto()
            detail.value = null
            detail.required = null
            detail.detailProperty = new ActivityDetailPropertyDto()
            detail.detailProperty.type = type
            detail.detailProperty.name = "Testowy label"

        and:
            def issue = new IssueDto()
            issue.issueType = issueTypeDto1()

        and:
            def activity = new ActivityDto()
            activity.activityDetails = singletonList(detail)
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            issueService.findIssue(activity.issueId) >> issue

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Nieprawidłowy typ czynności dla typu zlecenia"

        where:
            type << [Date.canonicalName, Boolean.canonicalName, Integer.canonicalName, String.canonicalName]

    }

    def "should throw an exception if property does not have a value and required is false"(def type) {
        given:
            def detail = new ActivityDetailDto()
            detail.value = null
            detail.required = null
            detail.detailProperty = new ActivityDetailPropertyDto()
            detail.detailProperty.type = type
            detail.detailProperty.name = "Testowy label"

        and:
            def issue = new IssueDto()
            issue.issueType = issueTypeDto1()

        and:
            def activity = new ActivityDto()
            activity.activityDetails = singletonList(detail)
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            issueService.findIssue(activity.issueId) >> issue

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Nieprawidłowy typ czynności dla typu zlecenia"

        where:
            type << [Date.canonicalName, Boolean.canonicalName, Integer.canonicalName, String.canonicalName]

    }

    def "should throw an exception if property has unknown type"() {
        given:
            def detail = new ActivityDetailDto()
            detail.value = "testowa wartosc"
            detail.required = true
            detail.detailProperty = new ActivityDetailPropertyDto()
            detail.detailProperty.type = "nieznany.typ.Klasy"
            detail.detailProperty.name = "Testowy label"

        and:
            def activity = new ActivityDto()
            activity.activityDetails = singletonList(detail)

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawny typ dla wartości 'Testowy label'"
    }

    def "should throw an exception if value has wrong type"() {
        given:
            def detail = new ActivityDetailDto()
            detail.value = "testowa wartosc"
            detail.required = true
            detail.detailProperty = new ActivityDetailPropertyDto()
            detail.detailProperty.type = type
            detail.detailProperty.name = "Testowy label"

        and:
            def activity = new ActivityDto()
            activity.activityDetails = singletonList(detail)

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawna wartość dla 'Testowy label'"

        where:
            type << [Date.canonicalName, Boolean.canonicalName, Integer.canonicalName]
    }

    def "should throw an exception if activity planned date is after issue expiration date"() {
        given:
            def issue = new IssueDto()
            LocalDateTime dateBefore30Days = LocalDateTime.now().minusDays(30)
            issue.expirationDate = Date.from(dateBefore30Days.toInstant(ZoneOffset.UTC))

        and:
            def activity = new ActivityDto()
            activity.plannedDate = new Date()
            activity.issueId = 100L

        and:
            issueService.findIssue(activity.issueId) >> issue

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Data zaplanowania nie może być późniejsza niż data zakończenia zlecenia"
    }

    def "should throw an exception if activity planned date is after max activity planned date"() {
        given:
            def issue = new IssueDto()
            issue.expirationDate = parse("2017-10-20")

        and:
            def activity = new ActivityDto()
            activity.plannedDate = parse("2017-10-15")
            activity.issueId = 100L
            activity.state = PLANNED

        and:
            dateProvider.getDateStartOfDayPlusDays(7) >> parse("2017-10-13")

        and:
            issueService.findIssue(activity.issueId) >> issue

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Czynność może być zaplanowana maksymalnie 7 dni w przód"
    }

    def "should pass validation if activity planned date is before max activity planned date"() {
        given:
            def issue = new IssueDto()
            issue.expirationDate = parse("2017-10-20")
            issue.id = 100L
            issue.issueType = issueTypeDto1()

        and:
            def activity = new ActivityDto()
            activity.plannedDate = parse("2017-10-15")
            activity.issueId = issue.id
            activity.state = PLANNED
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            dateProvider.getDateStartOfDayPlusDays(7) >> parse("2017-10-16")

        and:
            issueService.findIssue(activity.issueId) >> issue

        and:
            activityTypeService.checkIfActivityTypeIsAllowedForIssueType(issue.issueType.id, activity.activityType.id) >> true

        when:
            activityValidator.validate(activity)

        then:
            true
    }

    def "should throw an exception if activity to update does not exist"() {
        given:
            def activity = activityDto(ACTIVITY_ID_100, null, null)

        and:
            activityService.findActivityById(ACTIVITY_ID_100) >> Optional.empty()

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawna czynność windykacyjna do aktualizacji"
    }

    def "should throw an exception if activity to update is not open"() {
        given:
            def activity = activityDto(ACTIVITY_ID_100, state, null)

        and:
            activityService.findActivityById(ACTIVITY_ID_100) >> Optional.of(activity)

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawny status czynności windykacyjnej"

        where:
            state << [ActivityStateDto.CANCELED, ActivityStateDto.EXECUTED]
    }

    def "should throw an exception if activity type was changed"() {
        given:
            def activityToUpdate = activityDto(ACTIVITY_ID_100, state, TEST_ACTIVITY_TYPE_DTO_2)
        and:
            def existingActivity = activityDto(ACTIVITY_ID_100, state, TEST_ACTIVITY_TYPE_DTO_1)

        and:
            activityService.findActivityById(ACTIVITY_ID_100) >> Optional.of(existingActivity)

        when:
            activityValidator.validate(activityToUpdate)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawny typ czynności windykacyjnej"

        where:
            state << [PLANNED, POSTPONED]
    }

    def "should pass validation when activity to update is correct"() {
        given:
            def declaration = new DeclarationDto()
            declaration.declarationDate = parse("2017-08-01")
            declaration.declaredPaymentDate = parse("2017-08-09")
            def activity = activityDto(ACTIVITY_ID_100, state, TEST_ACTIVITY_TYPE_DTO_1)
            activity.issueId = ISSUE_ID_1
            activity.declarations = [declaration]
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            activityService.findActivityById(ACTIVITY_ID_100) >> Optional.of(activity)

        and:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        and:
            activityService.findActiveIssueActivities(ISSUE_ID_1, EXPIRATION_DATE_1) >> []

        and:
            dateProvider.getStartOfPreviousWorkingDay() >> parse("2017-07-31")
            dateProvider.getDateStartOfDayPlusDays(7) >> parse("2017-08-10")

        and:
            activityTypeService.checkIfActivityTypeIsAllowedForIssueType(issueDto1().issueType.id, activity.activityType.id) >> true

        when:
            activityValidator.validate(activity)

        then:
            true

        where:
            state << [PLANNED, POSTPONED]
    }

    def "should throw an exception if activity type is illegal for issue type"() {
        given:
            def declaration = new DeclarationDto()
            declaration.declarationDate = parse("2017-08-01")
            declaration.declaredPaymentDate = parse("2017-08-09")
            def activity = activityDto(ACTIVITY_ID_100, PLANNED, TEST_ACTIVITY_TYPE_DTO_1)
            activity.issueId = ISSUE_ID_1
            activity.declarations = [declaration]
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            activityService.findActivityById(ACTIVITY_ID_100) >> Optional.of(activity)

        and:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        and:
            activityService.findActiveIssueActivities(ISSUE_ID_1, EXPIRATION_DATE_1) >> []

        and:
            dateProvider.getStartOfPreviousWorkingDay() >> parse("2017-07-31")
            dateProvider.getDateStartOfDayPlusDays(7) >> parse("2017-08-10")

        and:
            activityTypeService.checkIfActivityTypeIsAllowedForIssueType(issueDto1().issueType.id, activity.activityType.id) >> false

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Nieprawidłowy typ czynności dla typu zlecenia"
    }

    def "should throw an exception if declaration date is after issue expiration date"() {
        given:
            def declaration = new DeclarationDto()
            declaration.declaredPaymentDate = parse("2017-08-11")
        and:
            def activity = new ActivityDto()
            activity.issueId = ISSUE_ID_1
            activity.declarations = [declaration]

        and:
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Data zapłaty deklaracji nie może być późniejsza niż data zakończenia zlecenia"
    }

    @Unroll
    def "should throw an exception for too long declaration payment date [#declaredPaymentDate]"() {
        given:
            def declaration = new DeclarationDto()
            declaration.declaredPaymentDate = declaredPaymentDate

        and:
            def activity = new ActivityDto()
            activity.id = ACTIVITY_ID_1
            activity.issueId = ISSUE_ID_3
            activity.declarations = [declaration]
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            issueService.findIssue(ISSUE_ID_3) >> issueDto3()
            activityService.findActiveIssueActivities(ISSUE_ID_3, EXPIRATION_DATE_3) >> activities
            activityService.findActivityById(ACTIVITY_ID_1) >> Optional.of(activityDto1())

        and:
            dateProvider.getDateStartOfDayPlusDays(7) >> parse("2017-08-08")

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Maksymalna data zapłaty deklaracji to ${expectedDate}"

        where:
            activities                                 | declaredPaymentDate | expectedDate
            []                                         | parse("2017-08-09") | "08.08.2017"
            [activityDto1()]                           | parse("2017-08-10") | "08.08.2017"
            [activityDto(ACTIVITY_ID_2, "2017-08-10")] | parse("2017-08-11") | "08.08.2017"
            [activityDto(ACTIVITY_ID_1, "2017-08-06")] | parse("2017-08-12") | "08.08.2017"
            [activityDto(ACTIVITY_ID_2, "2017-08-06")] | parse("2017-08-13") | "08.08.2017"
            [activityDto(ACTIVITY_ID_2, "2017-08-02")] | parse("2017-08-14") | "08.08.2017"
    }

    def "should pass validation when activity is null"() {
        when:
            activityValidator.validate(null)

        then:
            true
    }

    def "should throw an exception for declaration date before previous working day"() {
        given:
            def declaration = new DeclarationDto()
            declaration.declarationDate = parse("2017-07-30")
            declaration.declaredPaymentDate = parse("2017-08-06")

        and:
            def activity = new ActivityDto()
            activity.id = ACTIVITY_ID_1
            activity.issueId = ISSUE_ID_3
            activity.declarations = [declaration]
            activity.activityType = TEST_ACTIVITY_TYPE_DTO_1

        and:
            issueService.findIssue(ISSUE_ID_3) >> issueDto3()
            activityService.findActiveIssueActivities(ISSUE_ID_3, EXPIRATION_DATE_3) >> []
            activityService.findActivityById(ACTIVITY_ID_1) >> Optional.of(activityDto1())

        and:
            dateProvider.getStartOfPreviousWorkingDay() >> parse("2017-07-31")
            dateProvider.getDateStartOfDayPlusDays(7) >> parse("2017-08-08")

        when:
            activityValidator.validate(activity)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Data stworzenia deklaracji nie może być wcześniejsza niż poprzedni dzień roboczy"
    }
}

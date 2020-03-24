package com.codersteam.alwin.core.service.impl.scheduler

import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.SchedulerExecutionInfoDao
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo
import com.codersteam.alwin.testdata.SchedulerExecutionTestData
import org.javers.common.collections.Lists
import spock.lang.Specification
import spock.lang.Subject

import static SchedulerExecutionInfoServiceImpl.CLEANED_UP_BEFORE_SUCCESSFUL_FINISH
import static com.codersteam.alwin.core.api.model.common.Page.emptyPage
import static com.codersteam.alwin.testdata.SchedulerExecutionTestData.schedulerExecutionsFirstPage
import static com.codersteam.alwin.testdata.SchedulerExecutionTestData.schedulerExecutionsFirstPageDto
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Dariusz Rackowski
 */
class SchedulerExecutionInfoServiceImplTest extends Specification {

    private SchedulerExecutionInfoDao schedulerExecutionDao = Mock(SchedulerExecutionInfoDao)
    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper mapper = new AlwinMapper(dateProvider, alwinProperties)

    @Subject
    SchedulerExecutionInfoServiceImpl service

    def setup() {
        service = new SchedulerExecutionInfoServiceImpl(mapper, dateProvider, schedulerExecutionDao)
    }

    def "should create schedule execution"() {
        given:
            dateProvider.getCurrentDate() >> SchedulerExecutionTestData.CREATE_TEST_START_DATE_5
        when:
            def started = service.schedulerExecutionStarted(SchedulerExecutionTestData.CREATE_TEST_TYPE_5, SchedulerExecutionTestData.CREATE_TEST_MANUAL_5)
        then:
            assertThat(started).isEqualToComparingFieldByFieldRecursively(SchedulerExecutionTestData.expectedCreatedSchedulerExecutionDto1())
        and:
            1 * schedulerExecutionDao.create(_ as SchedulerExecutionInfo) >> {
                List args ->
                    def schedulerExecution = (SchedulerExecutionInfo) args[0]
                    schedulerExecution.id = SchedulerExecutionTestData.CREATE_TEST_ID_5
                    schedulerExecution
            }
    }

    def "should update end date on scheduler finished"() {
        given:
            def currentDate = parse("2018-05-05 01:02:03.456789")
            dateProvider.getCurrentDate() >> currentDate
        when:
            service.schedulerExecutionFinished(SchedulerExecutionTestData.ID_4)
        then:
            1 * schedulerExecutionDao.updateEndDate(SchedulerExecutionTestData.ID_4, currentDate) >> 1
    }

    def "should update end date and state on scheduler failed"() {
        given:
            def currentDate = parse("2018-05-05 01:02:03.456789")
            dateProvider.getCurrentDate() >> currentDate
        and:
            def errorMessage = "some error occurred"
        when:
            service.schedulerExecutionFailed(SchedulerExecutionTestData.ID_4, errorMessage)
        then:
            1 * schedulerExecutionDao.updateEndDateAndState(SchedulerExecutionTestData.ID_4, currentDate, errorMessage) >> 1
    }

    def "should return page of schedulers executions"() {
        given:
            schedulerExecutionDao.findSchedulersExecutions(0, 2) >> schedulerExecutionsFirstPage()

        and:
            schedulerExecutionDao.findSchedulersExecutionsCount() >> 4

        when:
            def result = service.findSchedulersExecutions(0, 2)

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(schedulerExecutionsFirstPageDto())

    }

    def "should return empty page of schedulers executions"() {
        given:
            schedulerExecutionDao.findSchedulersExecutions(0, 2) >> []

        and:
            schedulerExecutionDao.findSchedulersExecutionsCount() >> 0

        when:
            def result = service.findSchedulersExecutions(0, 2)

        then:
            assertThat(result).isEqualToComparingFieldByFieldRecursively(emptyPage())

    }

    def "should cleanup all not finished scheduler executions"() {
        given:
            def currentDate = parse("2018-05-05 03:04:05.123456")
            dateProvider.getCurrentDate() >> currentDate
        when:
            service.cleanupRunningSchedulerExecutions()
        then:
            1 * schedulerExecutionDao.findByEndDateIsNull() >> Lists.asList(
                    SchedulerExecutionTestData.schedulerExecution4(),
                    SchedulerExecutionTestData.schedulerExecution6()
            )
        then:
            1 * schedulerExecutionDao.updateEndDateAndState(SchedulerExecutionTestData.ID_4, currentDate, CLEANED_UP_BEFORE_SUCCESSFUL_FINISH) >> 1
        then:
            1 * schedulerExecutionDao.updateEndDateAndState(SchedulerExecutionTestData.ID_6, currentDate, CLEANED_UP_BEFORE_SUCCESSFUL_FINISH) >> 1
    }

}

package com.codersteam.alwin.core.service.impl.scheduler

import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.SchedulerConfigurationDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.SchedulerConfigurationTestData.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Dariusz Rackowski
 */
class SchedulerConfigurationServiceImplTest extends Specification {

    private SchedulerConfigurationDao schedulerConfigurationDao = Mock(SchedulerConfigurationDao)
    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper mapper = new AlwinMapper(dateProvider, alwinProperties)

    @Subject
    SchedulerConfigurationServiceImpl service

    def setup() {
        service = new SchedulerConfigurationServiceImpl(mapper, dateProvider, schedulerConfigurationDao)
    }

    def "should return all scheduler configurations"() {
        when:
            def allConfigurations = service.findAll()
        then:
            assertThat(allConfigurations).isEqualToComparingFieldByFieldRecursively(schedulerConfigurationDtoList())
        and:
            1 * schedulerConfigurationDao.allOrderedById() >> {
                schedulerConfigurationList()
            }
    }

    def "should call update task execution hour and updated date"() {
        given:
            dateProvider.getCurrentDate() >> CHANGED_UPDATE_DATE_1
        when:
            service.changeTimeOfExecution(PROCESS_1, CHANGED_HOUR_1)
        then:
            1 * schedulerConfigurationDao.updateHourAndUpdateDate(PROCESS_1, CHANGED_UPDATE_DATE_1, CHANGED_HOUR_1) >> { 1 }
    }

}

package com.codersteam.alwin.integration;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;
import com.codersteam.alwin.core.service.impl.scheduler.BatchProcessScheduler;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.testdata.SchedulerConfigurationTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.ejb.Timer;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dariusz Rackowski
 */
@UsingDataSet({"test-scheduler-configuration.json"})
public class SchedulerBatchProcessSchedulerTestIT extends CoreTestBase {

    private static final long ONE_DAY_IN_MILLISECONDS = TimeUnit.HOURS.toMillis(24);

    @EJB
    private BatchProcessScheduler batchProcessScheduler;

    @Test
    public void shouldCreateCorrectTimerForSchedulerExecution() {
        // given
        final SchedulerConfigurationDto configuration = SchedulerConfigurationTestData.schedulerConfigurationDto1();

        // when
        final Timer createdTimer = batchProcessScheduler.createTimer(configuration);

        // then
        assertThat(createdTimer.getInfo()).isEqualTo(SchedulerBatchProcessType.BASE_SCHEDULE_TASKS.name());
        // and
        assertThat(createdTimer.isPersistent()).isFalse();
        // and
        assertThat(createdTimer.getNextTimeout()).hasHourOfDay(SchedulerConfigurationTestData.HOUR_1);
        assertThat(createdTimer.getNextTimeout()).hasMinute(0);
        assertThat(createdTimer.getNextTimeout()).hasSecond(0);
        // and
        assertThat(createdTimer.getTimeRemaining()).isNotNegative();
        assertThat(createdTimer.getTimeRemaining()).isLessThan(ONE_DAY_IN_MILLISECONDS);
    }

}


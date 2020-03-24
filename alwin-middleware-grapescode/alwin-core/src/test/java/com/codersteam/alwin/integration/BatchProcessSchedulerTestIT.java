package com.codersteam.alwin.integration;

import com.codersteam.alwin.core.service.impl.scheduler.BatchProcessScheduler;
import com.codersteam.alwin.db.dao.SchedulerExecutionInfoDao;
import com.codersteam.alwin.integration.common.CoreTestBase;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dariusz Rackowski
 */
@UsingDataSet({"test-scheduler-execution.json"})
public class BatchProcessSchedulerTestIT extends CoreTestBase {

    @EJB
    private BatchProcessScheduler batchProcessScheduler;
    @Inject
    private SchedulerExecutionInfoDao schedulerExecutionDao;

    @Test
    public void shouldCleanupOldSchedulerExecutions() throws Exception {
        // when
        batchProcessScheduler.setup();
        commitTrx();

        // then
        assertThatAllSchedulerExecutionsEnded();
    }

    private void assertThatAllSchedulerExecutionsEnded() {
        final List<SchedulerExecutionInfo> notFinishedSchedulerExecutions = schedulerExecutionDao.all().stream()
                .filter(schedulerExecution -> schedulerExecution.getEndDate() == null)
                .collect(Collectors.toList());
        assertThat(notFinishedSchedulerExecutions).isEmpty();
    }

}

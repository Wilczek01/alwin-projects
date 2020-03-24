package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.SchedulerExecutionInfoDao;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;
import com.codersteam.alwin.testdata.SchedulerExecutionTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dariusz Rackowski
 */
@UsingDataSet({"test-scheduler-execution.json"})
public class SchedulerExecutionDaoTestIT extends WriteTestBase {

    @EJB
    private SchedulerExecutionInfoDao schedulerExecutionDao;

    @Test
    public void shouldUpdateEndDate() throws Exception {
        // given
        final SchedulerExecutionInfo schedulerExecution = schedulerExecutionDao.get(SchedulerExecutionTestData.ID_4).get();
        final Date originalEndDate = schedulerExecution.getEndDate();

        // when
        final int updatedCount = schedulerExecutionDao.updateEndDate(SchedulerExecutionTestData.ID_4, parse("2018-05-09 12:00:00.000000"));
        commitTrx();

        // then
        assertThat(updatedCount).isEqualTo(1);
        final SchedulerExecutionInfo updatedSchedulerExecution = schedulerExecutionDao.get(SchedulerExecutionTestData.ID_4).get();
        assertThat(updatedSchedulerExecution.getEndDate()).isNotNull();
        assertThat(updatedSchedulerExecution.getEndDate()).isNotEqualTo(originalEndDate);
    }

    @Test
    public void shouldUpdateEndDateAndState() throws Exception {
        // given
        final SchedulerExecutionInfo schedulerExecution = schedulerExecutionDao.get(SchedulerExecutionTestData.ID_4).get();
        final Date originalEndDate = schedulerExecution.getEndDate();

        // when
        final String errorState = "unknown error occurred";
        final int updatedCount = schedulerExecutionDao.updateEndDateAndState(SchedulerExecutionTestData.ID_4, parse("2018-05-09 12:00:00.000000"), errorState);
        commitTrx();

        // then
        assertThat(updatedCount).isEqualTo(1);
        final SchedulerExecutionInfo updatedSchedulerExecution = schedulerExecutionDao.get(SchedulerExecutionTestData.ID_4).get();
        assertThat(updatedSchedulerExecution.getEndDate()).isNotNull();
        assertThat(updatedSchedulerExecution.getEndDate()).isNotEqualTo(originalEndDate);
        assertThat(updatedSchedulerExecution.getState()).isEqualToIgnoringCase(errorState);
    }

}

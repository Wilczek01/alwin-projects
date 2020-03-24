package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.db.dao.SchedulerExecutionInfoDao;
import com.codersteam.alwin.jpa.scheduler.SchedulerExecutionInfo;
import com.codersteam.alwin.testdata.SchedulerExecutionTestData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


/**
 * @author Dariusz Rackowski
 */
public class SchedulerExecutionInfoDaoTestIT extends ReadTestBase {

    @EJB
    private SchedulerExecutionInfoDao schedulerExecutionDao;

    @Test
    public void shouldFindAll() {
        // when
        final List<SchedulerExecutionInfo> allSchedulerExecutions = schedulerExecutionDao.all();

        // then
        assertThat(allSchedulerExecutions).hasSize(5);
    }

    @Test
    public void shouldFindOne() {
        // when
        final Optional<SchedulerExecutionInfo> schedulerExecution = schedulerExecutionDao.get(SchedulerExecutionTestData.ID_1);

        // then
        assertThat(schedulerExecution).isPresent();
        assertThat(schedulerExecution.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate")
                .isEqualToComparingFieldByField(SchedulerExecutionTestData.schedulerExecution1());
    }


    @Test
    public void shouldFindPageOfSchedulersExecutions() {
        // when
        final List<SchedulerExecutionInfo> schedulerExecution = schedulerExecutionDao.findSchedulersExecutions(0, 2);

        // then
        AssertionsForClassTypes.assertThat(schedulerExecution)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate")
                .isEqualToComparingFieldByFieldRecursively(SchedulerExecutionTestData.schedulerExecutionsFirstPage());

        // and
        final List<SchedulerExecutionInfo> schedulerExecutionSecondPage = schedulerExecutionDao.findSchedulersExecutions(2, 2);

        // and
        AssertionsForClassTypes.assertThat(schedulerExecutionSecondPage)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate")
                .isEqualToComparingFieldByFieldRecursively(SchedulerExecutionTestData.schedulerExecutionsSecondPage());
    }

    @Test
    public void shouldFindCountOfSchedulersExecutions() {
        // when
        final long schedulerExecution = schedulerExecutionDao.findSchedulersExecutionsCount();

        // then
        assertEquals(5L, schedulerExecution);
    }

    @Test
    public void shouldFindCountOfRunningSchedulerExecutions() {
        // when
        final long withoutEndDateCount = schedulerExecutionDao.findWithoutEndDateCount(SchedulerTaskType.UPDATE_COMPANIES_INVOLVEMENT);

        // then
        assertEquals(1L, withoutEndDateCount);
    }

}

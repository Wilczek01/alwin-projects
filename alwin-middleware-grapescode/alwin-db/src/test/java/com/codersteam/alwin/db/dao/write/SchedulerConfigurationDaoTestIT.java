package com.codersteam.alwin.db.dao.write;

import com.codersteam.alwin.db.dao.SchedulerConfigurationDao;
import com.codersteam.alwin.jpa.scheduler.SchedulerConfiguration;
import com.codersteam.alwin.testdata.SchedulerConfigurationTestData;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dariusz Rackowski
 */
@UsingDataSet({"test-scheduler-configuration.json"})
public class SchedulerConfigurationDaoTestIT extends WriteTestBase {

    @EJB
    private SchedulerConfigurationDao schedulerConfigurationDao;

    @Test
    public void shouldUpdateHourAndUpdateDateOfSchedulerConfiguration() throws Exception {
        // given
        final SchedulerConfiguration schedulerConfiguration = schedulerConfigurationDao.get(SchedulerConfigurationTestData.ID_1).get();
        final Date originalUpdateDate = schedulerConfiguration.getUpdateDate();
        // and
        final int NEW_HOUR = SchedulerConfigurationTestData.CHANGED_HOUR_1;
        final Date NEW_UPDATE_DATE = SchedulerConfigurationTestData.CHANGED_UPDATE_DATE_1;

        // when
        final int updatedCount = schedulerConfigurationDao.updateHourAndUpdateDate(SchedulerConfigurationTestData.PROCESS_1, NEW_UPDATE_DATE, NEW_HOUR);
        commitTrx();

        // then
        assertThat(updatedCount).isEqualTo(1);
        final SchedulerConfiguration updatedSchedulerConfiguration = schedulerConfigurationDao.get(SchedulerConfigurationTestData.ID_1).get();
        assertThat(updatedSchedulerConfiguration.getHour()).isEqualTo(NEW_HOUR);
        assertThat(updatedSchedulerConfiguration.getUpdateDate().getTime()).isEqualTo(NEW_UPDATE_DATE.getTime());
        assertThat(updatedSchedulerConfiguration.getUpdateDate()).isNotEqualTo(originalUpdateDate);
    }

}

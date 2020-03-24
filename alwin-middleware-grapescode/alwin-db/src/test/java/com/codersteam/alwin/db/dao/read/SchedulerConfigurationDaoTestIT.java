package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.SchedulerConfigurationDao;
import com.codersteam.alwin.jpa.scheduler.SchedulerConfiguration;
import com.codersteam.alwin.testdata.SchedulerConfigurationTestData;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * @author Dariusz Rackowski
 */
public class SchedulerConfigurationDaoTestIT extends ReadTestBase {

    @EJB
    private SchedulerConfigurationDao schedulerConfigurationDao;

    @Test
    public void shouldFindAllSchedulerConfigurations() {
        // when
        final List<SchedulerConfiguration> allSchedulerConfiguration = schedulerConfigurationDao.allOrderedById();

        // then
        assertThat(allSchedulerConfiguration)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "updateDate")
                .isEqualToComparingFieldByFieldRecursively(SchedulerConfigurationTestData.schedulerConfigurationList());
    }

    @Test
    public void shouldFindOneSchedulerConfigurationById() {
        // when
        final Optional<SchedulerConfiguration> schedulerConfiguration = schedulerConfigurationDao.get(SchedulerConfigurationTestData.ID_1);

        // then
        assertThat(schedulerConfiguration).isPresent();
        assertThat(schedulerConfiguration.get())
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "updateDate")
                .isEqualToComparingFieldByField(SchedulerConfigurationTestData.schedulerConfiguration1());
    }

}

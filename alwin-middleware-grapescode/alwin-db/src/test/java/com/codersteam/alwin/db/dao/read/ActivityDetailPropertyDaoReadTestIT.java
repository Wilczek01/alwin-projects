package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.ActivityDetailPropertyDao;
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.FAILED_PHONE_CALL_REASON;
import static com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey.REMARK;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ALL_ACTIVITY_DETAIL_PROPERTIES;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty10;
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.activityDetailProperty11;
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValuesForActivityDetailProperty11;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Stepnowski
 */
public class ActivityDetailPropertyDaoReadTestIT extends ReadTestBase {

    @EJB
    private ActivityDetailPropertyDao activityDetailPropertyDao;

    @Test
    public void shouldFindRemarkActivityDetailPropertyByKey() {
        // when
        final Optional<ActivityDetailProperty> remarkActivityDetailProperty = activityDetailPropertyDao.findActivityDetailProperty(REMARK);

        // then
        assertTrue(remarkActivityDetailProperty.isPresent());
        assertThat(remarkActivityDetailProperty.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "dictionaryValues", "activityTypeDetailProperty")
                .isEqualToComparingFieldByFieldRecursively(activityDetailProperty10());
    }

    @Test
    public void shouldFindAllActivityDetailProperties() {
        // when
        final List<ActivityDetailProperty> allActivityDetailProperties = activityDetailPropertyDao.findAllActivityDetailProperties();

        // then
        assertEquals(ALL_ACTIVITY_DETAIL_PROPERTIES.size(), allActivityDetailProperties.size());
        assertThat(allActivityDetailProperties)
                .usingComparatorForFields(SKIP_COMPARATOR, "dictionaryValues", "activityTypeDetailProperty")
                .isEqualToComparingFieldByFieldRecursively(ALL_ACTIVITY_DETAIL_PROPERTIES);
    }

    @Test
    public void shouldFindFailedPhoneCallReasonActivityDetailPropertyWithDictionaryValues() {
        // when
        final Optional<ActivityDetailProperty> failedPhoneCallactivityDetailProperty =
                activityDetailPropertyDao.findActivityDetailProperty(FAILED_PHONE_CALL_REASON);

        // then
        assertTrue(failedPhoneCallactivityDetailProperty.isPresent());
        assertThat(failedPhoneCallactivityDetailProperty.get())
                .usingComparatorForFields(SKIP_COMPARATOR, "dictionaryValues", "activityTypeDetailProperty")
                .isEqualToComparingFieldByFieldRecursively(activityDetailProperty11());
        assertThat(failedPhoneCallactivityDetailProperty.get().getDictionaryValues())
                .usingComparatorForFields(SKIP_COMPARATOR, "activityDetailProperty")
                .isEqualToComparingFieldByFieldRecursively(activityTypeDetailPropertyDictValuesForActivityDetailProperty11());
    }

    @Test
    public void shouldReturnDaoType() {
        // when
        final Class<ActivityDetailProperty> property = activityDetailPropertyDao.getType();

        // then
        assertEquals(ActivityDetailProperty.class, property);
    }
}

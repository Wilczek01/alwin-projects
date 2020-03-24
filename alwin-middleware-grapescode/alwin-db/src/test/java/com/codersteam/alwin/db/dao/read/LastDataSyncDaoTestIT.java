package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.LastDataSyncDao;
import com.codersteam.alwin.jpa.LastDataSync;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.Optional;

import static com.codersteam.alwin.jpa.type.LastDataSyncType.FIND_UNRESOLVED_ISSUES;
import static com.codersteam.alwin.jpa.type.LastDataSyncType.UPDATE_COMPANY_DATA;
import static com.codersteam.alwin.testdata.LastDataSyncTestData.lastDataSync1;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LastDataSyncDaoTestIT extends ReadTestBase {

    @EJB
    private LastDataSyncDao dao;

    @Test
    public void shouldFindLastDataSyncByType() throws Exception {
        // when
        final Optional<LastDataSync> optional = dao.findByType(UPDATE_COMPANY_DATA);
        // then
        assertTrue(optional.isPresent());
        assertThat(optional.get()).usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "toDate", "fromDate")
                .isEqualToComparingFieldByField(lastDataSync1());
    }

    @Test
    public void shouldNotFindLastDataSyncByType() throws Exception {
        // when
        final Optional<LastDataSync> optional = dao.findByType(FIND_UNRESOLVED_ISSUES);
        // then
        assertFalse(optional.isPresent());
    }

    @Test
    public void shouldGetType() throws Exception {
        assertEquals(LastDataSync.class, dao.getType());
    }


}

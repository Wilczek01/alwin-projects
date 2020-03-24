package com.codersteam.alwin.testdata;

import com.codersteam.alwin.jpa.LastDataSync;
import com.codersteam.alwin.jpa.type.LastDataSyncType;

import java.util.Date;

import static com.codersteam.alwin.jpa.type.LastDataSyncType.FIND_UNRESOLVED_ISSUES;
import static com.codersteam.alwin.jpa.type.LastDataSyncType.UPDATE_COMPANY_DATA;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

public class LastDataSyncTestData {

    private final static Long LAST_DATA_SYNC_ID_1 = 1L;
    private final static LastDataSyncType LAST_DATA_SYNC_TYPE_1 = UPDATE_COMPANY_DATA;
    public final static Date LAST_DATA_SYNC_TO_DATE_1 = parse("2016-10-10 12:23:34.000000");
    private final static Date LAST_DATA_SYNC_FROM_DATE_1 = parse("2016-10-09 23:34:43.000000");

    private final static Long LAST_DATA_SYNC_ID_2 = 2L;
    private final static LastDataSyncType LAST_DATA_SYNC_TYPE_2 = FIND_UNRESOLVED_ISSUES;
    public final static Date LAST_DATA_SYNC_TO_DATE_2 = parse("2016-10-10 12:23:34.000000");
    private final static Date LAST_DATA_SYNC_FROM_DATE_2 = parse("2016-10-09 23:34:43.000000");

    public static LastDataSync lastDataSync1() {
        return lastDataSync(LAST_DATA_SYNC_ID_1, LAST_DATA_SYNC_TYPE_1, LAST_DATA_SYNC_TO_DATE_1, LAST_DATA_SYNC_FROM_DATE_1);
    }

    public static LastDataSync lastDataSync2() {
        return lastDataSync(LAST_DATA_SYNC_ID_2, LAST_DATA_SYNC_TYPE_2, LAST_DATA_SYNC_TO_DATE_2, LAST_DATA_SYNC_FROM_DATE_2);
    }

    private static LastDataSync lastDataSync(final Long id, final LastDataSyncType type, final Date toDate, final Date fromDate) {
        final LastDataSync lastDataSync = new LastDataSync();
        lastDataSync.setId(id);
        lastDataSync.setType(type);
        lastDataSync.setToDate(toDate);
        lastDataSync.setFromDate(fromDate);
        return lastDataSync;
    }
}

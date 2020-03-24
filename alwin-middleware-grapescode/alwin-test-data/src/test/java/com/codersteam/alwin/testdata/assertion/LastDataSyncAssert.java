package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.LastDataSync;
import com.codersteam.alwin.jpa.type.LastDataSyncType;

import java.util.Date;

public class LastDataSyncAssert {

    public static void assertLastDataSync(final LastDataSync lastDataSync, final Date fromDate, final Date toDate, final LastDataSyncType type) {
        assert lastDataSync.getFromDate() == fromDate;
        assert lastDataSync.getToDate() == toDate;
        assert lastDataSync.getType() == type;
    }
}

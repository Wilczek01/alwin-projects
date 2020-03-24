package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.service.customer.UpdateLastDataSyncService;
import com.codersteam.alwin.db.dao.LastDataSyncDao;
import com.codersteam.alwin.jpa.LastDataSync;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

import static com.codersteam.alwin.jpa.type.LastDataSyncType.UPDATE_COMPANY_DATA;

/**
 * @author Piotr Naroznik
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class UpdateLastDataSyncServiceImpl implements UpdateLastDataSyncService {

    private final LastDataSyncDao lastDataSyncDao;

    @Inject
    public UpdateLastDataSyncServiceImpl(final LastDataSyncDao lastDataSyncDao) {
        this.lastDataSyncDao = lastDataSyncDao;
    }

    @Override
    public void updateLastDataSync(final Date fromDate, final Date toDate) {
        final LastDataSync lastDataSync = lastDataSyncDao.findByType(UPDATE_COMPANY_DATA).orElse(createLastDataSync());
        lastDataSync.setFromDate(fromDate);
        lastDataSync.setToDate(toDate);
        lastDataSyncDao.update(lastDataSync);
    }

    private LastDataSync createLastDataSync() {
        final LastDataSync lastDataSync = new LastDataSync();
        lastDataSync.setType(UPDATE_COMPANY_DATA);
        return lastDataSync;
    }
}

package com.codersteam.alwin.core.api.service.customer;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface UpdateLastDataSyncService {

    void updateLastDataSync(Date fromDate, Date toDate);
}

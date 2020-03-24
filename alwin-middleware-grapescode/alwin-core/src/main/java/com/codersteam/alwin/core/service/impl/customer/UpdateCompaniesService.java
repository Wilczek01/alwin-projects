package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.model.AidaPersonDto;
import com.codersteam.aida.core.api.service.CompanyService;
import com.codersteam.aida.core.api.service.PersonService;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.SyncDataService;
import com.codersteam.alwin.core.api.service.customer.UpdateLastDataSyncService;
import com.codersteam.alwin.db.dao.LastDataSyncDao;
import com.codersteam.alwin.jpa.LastDataSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.service.AlwinParameters.SYNC_DATA_DAY_START;
import static com.codersteam.alwin.jpa.type.LastDataSyncType.UPDATE_COMPANY_DATA;

public class UpdateCompaniesService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateCompaniesService.class.getName());

    private final DateProvider dateProvider;
    private final PersonService personService;
    private final CompanyService companyService;
    private final LastDataSyncDao lastDataSyncDao;
    private final SyncDataService syncDataService;
    private final UpdateLastDataSyncService updateLastDataSyncService;

    @Inject
    public UpdateCompaniesService(LastDataSyncDao lastDataSyncDao,
                                  AidaService aidaService,
                                  DateProvider dateProvider,
                                  SyncDataService syncDataService,
                                  UpdateLastDataSyncService updateLastDataSyncService) {
        this.dateProvider = dateProvider;
        this.lastDataSyncDao = lastDataSyncDao;
        this.personService = aidaService.getPersonService();
        this.companyService = aidaService.getCompanyService();
        this.syncDataService = syncDataService;
        this.updateLastDataSyncService = updateLastDataSyncService;
    }

    public void syncCompanyData() {
        Date fromDate = findSyncFromDate();
        Date toDate = dateProvider.getCurrentDate();
        LOG.info("sync company data started for fromDate = {}, toDate = {}", fromDate, toDate);

        Map<Long, AidaCompanyDto> modifiedCompanies = findModifiedCompanies(fromDate, toDate);
        LOG.info("found {} companies to update", modifiedCompanies.size());

        Map<Long, List<AidaPersonDto>> modifiedPersons = findModifiedPersons(fromDate, toDate);

        prepareModifiedCompaniesIds(modifiedCompanies, modifiedPersons)
                .forEach(extCompanyId -> {
                    try {
                        syncDataService.updateCompanyIfExist(modifiedCompanies.get(extCompanyId), modifiedPersons.get(extCompanyId), extCompanyId);
                    } catch (final Exception e) {
                        LOG.error("Cannot sync company data for extCompanyId {}", extCompanyId, e);
                    }
                });

        LOG.info("sync company data ended");
        updateLastDataSyncService.updateLastDataSync(fromDate, toDate);
    }

    private Date findSyncFromDate() {
        return lastDataSyncDao.findByType(UPDATE_COMPANY_DATA).map(LastDataSync::getToDate)
                .orElse(dateProvider.getDateStartOfDayMinusDays(SYNC_DATA_DAY_START));
    }

    private Map<Long, AidaCompanyDto> findModifiedCompanies(Date fromDate, Date toDate) {
        List<AidaCompanyDto> modifiedCompanies = companyService.findModifiedCompaniesExclusiveFromDate(fromDate, toDate);
        LOG.info("Received {} modified companies from AIDA", modifiedCompanies.size());

        return modifiedCompanies.stream()
                .collect(Collectors.toMap(AidaCompanyDto::getId, b -> b, (p, q) -> p));
    }

    private Map<Long, List<AidaPersonDto>> findModifiedPersons(Date fromDate, Date toDate) {
        return personService.findModifiedPersonsExclusiveFromDate(fromDate, toDate)
                .stream()
                .collect(Collectors.groupingBy(AidaPersonDto::getCompanyId));
    }

    private Set<Long> prepareModifiedCompaniesIds(Map<Long, AidaCompanyDto> modifiedCompanies, Map<Long, List<AidaPersonDto>> modifiedPersons) {
        Set<Long> extCompanyIds = new HashSet<>(modifiedPersons.keySet());
        extCompanyIds.addAll(modifiedCompanies.keySet());
        return extCompanyIds;
    }
}

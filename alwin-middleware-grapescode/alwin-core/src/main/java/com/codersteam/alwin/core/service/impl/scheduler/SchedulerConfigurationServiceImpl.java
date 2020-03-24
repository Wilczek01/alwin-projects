package com.codersteam.alwin.core.service.impl.scheduler;

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerConfigurationService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.SchedulerConfigurationDao;
import com.codersteam.alwin.jpa.scheduler.SchedulerConfiguration;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author Dariusz Rackowski
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class SchedulerConfigurationServiceImpl implements SchedulerConfigurationService {

    private final AlwinMapper mapper;
    private final DateProvider dateProvider;
    private final SchedulerConfigurationDao schedulerConfigurationDao;

    @Inject
    public SchedulerConfigurationServiceImpl(final AlwinMapper mapper,
                                             final DateProvider dateProvider,
                                             final SchedulerConfigurationDao schedulerConfigurationDao) {
        this.mapper = mapper;
        this.dateProvider = dateProvider;
        this.schedulerConfigurationDao = schedulerConfigurationDao;
    }

    @Override
    public void changeTimeOfExecution(final SchedulerBatchProcessType schedulerProcessBatchType, final int hour) {
        final int updated = schedulerConfigurationDao.updateHourAndUpdateDate(schedulerProcessBatchType, dateProvider.getCurrentDate(), hour);
        if (updated == 0) {
            throw new IllegalArgumentException(String.format("Unable to find scheduler configuration for process batch type %s", schedulerProcessBatchType));
        }
    }

    @Override
    public List<SchedulerConfigurationDto> findAll() {
        final List<SchedulerConfiguration> all = schedulerConfigurationDao.allOrderedById();
        return mapper.mapAsList(all, SchedulerConfigurationDto.class);
    }

    @Override
    public Optional<SchedulerConfigurationDto> findByBatchProcessType(final SchedulerBatchProcessType process) {
        return schedulerConfigurationDao.findByBatchProcess(process)
                .map(schedulerConfiguration -> mapper.map(schedulerConfiguration, SchedulerConfigurationDto.class));
    }
}
